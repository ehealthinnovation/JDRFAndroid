package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.common

import android.bluetooth.BluetoothGattCharacteristic
import android.util.Log
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.FormatType
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
     */
    constructor(characteristic: BluetoothGattCharacteristic?, uuid: Int): this(uuid) {
        this.characteristic = characteristic
        characteristic?.let {
            rawData = it.value ?: ByteArray(0)
            this.successfulParsing = tryParse(it)
        }
    }

    open val tag = BaseCharacteristic::class.java.canonicalName as String
    val nullValueException = "Null characteristic interpretation, aborting parsing."
    var rawData: ByteArray = ByteArray(0)
    var characteristic: BluetoothGattCharacteristic? = null
    var successfulParsing: Boolean = false
    var offset = 0

    /**
     * Swallowing the exception is one of two viable options, some users might want any error to
     * bubble up all the way immediately.
     *
     * https://github.com/markiantorno/JDRFAndroidBLEParser/issues/1
     */
    fun tryParse(c: BluetoothGattCharacteristic): Boolean {
        var errorFreeParse = false
        try {
            errorFreeParse = parse(c)
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
    protected abstract fun parse(c: BluetoothGattCharacteristic): Boolean

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
