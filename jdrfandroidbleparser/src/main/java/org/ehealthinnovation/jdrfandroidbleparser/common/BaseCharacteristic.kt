package org.ehealthinnovation.jdrfandroidbleparser.common

import android.bluetooth.BluetoothGattCharacteristic
import android.util.Log
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.FormatType
import org.ehealthinnovation.jdrfandroidbleparser.utility.CrcHelper
import java.util.*
import kotlin.jvm.Throws
import kotlin.jvm.java

/**
 * Base characteristic which all defined characteristics must extend.
 *
 * Created by miantorno on 2018-06-19.
 */
abstract class BaseCharacteristic(val uuid: Int) {

    /**
     * If the characteristic is null, which is possible, we just return false so the requesting
     * process can handle the failed parse. This is done in the super class, so we don't have to
     * deal with it in every other sub class characteristic.
     *
     * The default value for CRC check is false. There are some characteristics that requires a
     * first pass of parsing to know if CRC is present, and a second pass to parse the content with
     * the [hasCrc] flag set correctly.
     *
     *
     */
    constructor(characteristic: BluetoothGattCharacteristic?, uuid: Int, hasCrc: Boolean = false, isComposing: Boolean = false, hasE2eCounter: Boolean = false): this(uuid) {
        if(!isComposing) {
            this.characteristic = characteristic
            characteristic?.let {
                rawData = it.value ?: ByteArray(0)
                this.successfulParsing = tryParse(it, hasCrc, hasE2eCounter)
            }
        }else{
            characteristic?.let {
                this.composingcharacteristic = it }
            if(characteristic == null){
                this.composingcharacteristic = BluetoothGattCharacteristic(UUID.randomUUID(), BluetoothGattCharacteristic.PROPERTY_WRITE, BluetoothGattCharacteristic.PERMISSION_WRITE)
            }
        }
    }

    open val tag = BaseCharacteristic::class.java.canonicalName as String
    val nullValueException = "Null characteristic interpretation, aborting parsing."
    var rawData: ByteArray = ByteArray(0)
    var characteristic: BluetoothGattCharacteristic? = null
    var successfulParsing: Boolean = false
    var offset = 0
    /**A dummy characteristic for composing a new characteristic*/
    private var composingcharacteristic: BluetoothGattCharacteristic? = null

    /**
     * Swallowing the exception is one of two viable options, some users might want any error to
     * bubble up all the way immediately.
     *
     * https://github.com/markiantorno/JDRFAndroidBLEParser/issues/1
     * @param c  The [BluetoothGattCharacteristic] to parse
     * @param checkCrc if CRC check is needed. Default if false
     * @return true if parsing is successful
     *
     */
    fun tryParse(c: BluetoothGattCharacteristic, checkCrc : Boolean = false, hasE2eCounter: Boolean = false): Boolean {
        var errorFreeParse = false
        try {
            if(checkCrc){
                if(!testCrc(rawData)){
                    throw Exception("CRC16 check failed");
                }
            }
            errorFreeParse = parse(c, hasE2eCounter)
        } catch (e: NullPointerException) {
            Log.e(tag, nullValueException)
        } catch (e: Exception) {
            Log.e(tag, e.message)
        }
        return errorFreeParse
    }


    /**
     * Each characteristic has it's own set of values which could be of differing types, so we leave
     * implementation of the parsing to the individual characteristic implementation.
     *
     * Order matters for parsing as values are stored in the array, and are pulling out using a
     * store offset variable, see [getNextOffset].
     *
     * @param c The [BluetoothGattCharacteristic] to parse.
     */
    protected abstract fun parse(c: BluetoothGattCharacteristic, hasE2eCounter : Boolean = false): Boolean

    /**
     * This function tests if the CRC attached at the packet is correct. This function
     * assumes the CRC is attached at the end of the packet. If it is not the case, subclass
     * needs to implements this method.
     * @param data The raw data of the message byte sequence
     * @return true if the CRC is passed.
     */
    protected fun testCrc(data : ByteArray) : Boolean {
        return CrcHelper.testCcittCrc16(data, data.size)
    }

    /**
     * This function append the CRC to the end of the packet.
     * @param data The raw data of the message byte sequence
     * @return the resulting [ByteArray] with the CRC attached.
     */
    protected fun attachCrc(data: ByteArray? = null) : ByteArray {
        data?.let {
            return CrcHelper.attachCcittCrc16ToPacket(data, data.size)
        }
        val cRc = CrcHelper.calculateCcittCrc16(rawData, rawData.size).toInt()
        if(putIntValue(cRc, BluetoothGattCharacteristic.FORMAT_UINT16)) {
            return rawData
        }else{
            throw IllegalStateException("Unable to append CRC")
        }
    }

    /**
     * Returns the stored [String] value of this characteristic.
     *
     * <p>The formatType parameter determines how the characteristic value is to be interpreted.
     * For example, setting formatType to [FORMAT_SINT16] specifies that the first two bytes of the
     * characteristic value at the given offset are interpreted to generate the return value. This
     * will use the current stored [offset] as the index at which the value can be found.
     *
     * Increments [offset] by the size (in bytes) of value returned.
     *
     * @param formatType The format type used to interpret the characteristic
     *                   value.
     * @throws NullPointerException If no such value exists, or offset exceeds size.
     * @return Next [String] cached value of the characteristic.
     */
    @Throws(NullPointerException::class)
    protected fun getNextStringValue(c: BluetoothGattCharacteristic): String =
            c.getStringValue(offset)?.let {
                offset += it.toByteArray().size
                return it
            } ?: throw NullPointerException("Offset \"$offset\" does not relate to valid String value...")

    /**
     * Returns the stored [Int] value of this characteristic.
     *
     * <p>The formatType parameter determines how the characteristic value is to be interpreted.
     * For example, setting formatType to [FORMAT_SINT16] specifies that the first two bytes of the
     * characteristic value at the given offset are interpreted to generate the return value. This
     * will use the current stored [offset] as the index at which the value can be found.
     *
     * Increments [offset] by the size (in bytes) of value returned.
     *
     * @param formatType The format type used to interpret the characteristic
     *                   value.
     * @throws NullPointerException If no such value exists, or offset exceeds size.
     * @return Next [Int] cached value of the characteristic.
     */
    @Throws(NullPointerException::class)
    protected fun getNextIntValue(c: BluetoothGattCharacteristic, formatType: Int): Int =
            c.getIntValue(formatType, offset)?.let {
                offset = getNextOffset(formatType, offset)
                return it
            } ?: throw NullPointerException("Format \"$formatType\" at offset \"$offset\" does " +
                    "not relate to valid Int value...")

    /**
     * Returns the stored [Float] value of this characteristic.
     *
     * <p>The formatType parameter determines how the characteristic value is to be interpreted.
     * For example, setting formatType to [FORMAT_SFLOAT] specifies that the first two bytes of the
     * characteristic value at the given offset are interpreted to generate the return value. This
     * will use the current stored [offset] as the index at which the value can be found.
     *
     * Increments [offset] by the size (in bytes) of value returned.
     *
     * @param formatType The format type used to interpret the characteristic
     *                   value.
     * @throws NullPointerException If no such value exists, or offset exceeds size.
     * @return Next [Float] cached value of the characteristic.
     */
    @Throws(NullPointerException::class)
    protected fun getNextFloatValue(c: BluetoothGattCharacteristic, formatType: Int): Float =
            c.getFloatValue(formatType, offset)?.let {
                offset = getNextOffset(formatType, offset)
                return it
            } ?: throw NullPointerException("Format \"$formatType\" at offset \"$offset\" does " +
                    "not relate to valid Float value...")

    protected fun getRawValueAfterOffset(c:BluetoothGattCharacteristic): ByteArray =
            c.value.let {
                val toReturn:ByteArray = it.copyOfRange(offset, it.size-1)
                offset = it.size - 1
                toReturn
            }

    @Throws(NullPointerException::class)

    /**
     * Put the input [value] of Bluetooth type [formatType] into a the internal [composingcharacteristic]
     * object. The [offset] property will be incremented by the size of  [formatType]
     *
     * @param value The integer value to be put into the serial buffer
     * @param formatType Int indicating the format type of the input. An example is
     * [BluetoothGattCharacteristic.FORMAT_UINT8]
     * @return true if operation is successful; false otherwise
     * @throws NullPointerException If the internal serialization buffer [composingcharacteristic]
     * is null, this happens when [BaseCharacteristic]
     */
    protected fun putIntValue(value :Int, formatType: Int) : Boolean{
       composingcharacteristic?.run {
           val results = this.setValue(value, formatType, offset)
           if(results) {
               offset = getNextOffset(formatType, offset)
               rawData = this.value ?: ByteArray(0)
           }
           return results
       } ?: throw NullPointerException("composingcharacteristic is null, make sure the " +
               "characteristic is initiated with the composing constructor")
    }

    protected fun putFloatValue(mantissa: Int, exponent: Int, formatType: Int ) : Boolean{
        composingcharacteristic?.run {
            val result = this.setValue(mantissa, exponent, formatType, offset)
            if (result){
                offset = getNextOffset(formatType, offset)
                rawData = this.value ?: ByteArray(0)
            }
            return result
        } ?: throw NullPointerException("composingcharacteristic is null, make sure the " +
            "characteristic is initiated with the composing constructor")
    }

    protected fun putByteArray(data: ByteArray) : Boolean{
        composingcharacteristic?.run {
            val result = this.setValue(data)
            if(result){
                offset += data.size
                rawData = this.value ?: ByteArray(0)
            }
            return result
        }?: throw NullPointerException("composingcharacteristic is null, make sure the " +
            "characteristic is initiated with the composing constructor")

    }

    /**
     * Increments the current read index by the appropriate amount for the format type passed in.
     *
     * @param formatType Please [BluetoothGattCharacteristic] for acceptable format types.
     * @param currentIndex Current byte offset for characteristic reading.
     * @return Adjusted index offset
     */
    private fun getNextOffset(formatType: Int, currentIndex: Int): Int {
        var newIndex = currentIndex
        newIndex += FormatType.fromType(formatType)?.let {
            it.length()
        } ?: Log.e(tag, "Bad format type, \"$formatType\", passed into get value...")
        return newIndex
    }




}
