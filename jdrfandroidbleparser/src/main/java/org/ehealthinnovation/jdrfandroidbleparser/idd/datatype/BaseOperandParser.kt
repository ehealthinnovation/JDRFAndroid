package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype

import android.bluetooth.BluetoothGattCharacteristic
import java.lang.NullPointerException
import java.util.*


/**
 * This class implements the interface [Parsable].
 *
 * It is the base class for operand in the control point. A set of basic operations are provided
 * for parsing.
 *
 * Subclass must implement the [parse] method, the details of this method varies with each operand.
 *
 * This class preserve the state of parsing, so [parse] should not be called twice after an object
 * is created.
 *
 * @author Harry Qiu
 * @since 0.0.1
 */
abstract class BaseOperandParser(val rawData: ByteArray, c: BluetoothGattCharacteristic? = null) : Parsable {

    open val tag = BaseOperandParser::class.java.canonicalName as String

    private var offset: Int = 0
    private lateinit var helperCharacteristic : BluetoothGattCharacteristic

    init {
        System.out.printf("init block: loading up raw data\n")
        if(c == null){
           helperCharacteristic = BluetoothGattCharacteristic( UUID.randomUUID(), BluetoothGattCharacteristic.PROPERTY_READ, BluetoothGattCharacteristic.PERMISSION_READ)
        }else{
            helperCharacteristic = c
        }
        helperCharacteristic.value = rawData
    }

    /**
     * Deserialize the [rawData] into specific operand objects.
     * @return false if parsing is not successful; true otherwise.
     */
    abstract fun parse(): Boolean


    override fun getNextInt(formatType: Int): Int {
        var toReturn = helperCharacteristic.getIntValue(formatType, offset)
        toReturn?.let {
            offset += getTypeLen(formatType)
            return toReturn
        } ?: throw NullPointerException("reading next integer results in null.")
    }

    override fun getNextFloat(formatType: Int): Float {
        var toReturn = helperCharacteristic.getFloatValue(formatType, offset)
        toReturn?.let {
            offset += getTypeLen(formatType)
            return toReturn
        } ?: throw NullPointerException("reading next float results in null.")
    }

    override fun getNextString(length: Int): String {
        var toReturn = helperCharacteristic.getStringValue(offset)
        toReturn?.let {
            offset += toReturn.length
            return toReturn
        } ?: throw NullPointerException("reading next string results in null.")
    }

    fun getByteArray(length: Int): ByteArray {
        var buffer = helperCharacteristic.value
        var toReturn: ByteArray
        buffer?.let {
            if(it.size < (length + offset)) {
                throw ArrayIndexOutOfBoundsException("reading byte array beyond buffer length")
            }
            toReturn = it.sliceArray(IntRange(offset, offset+length))
            offset += length
            return  toReturn
        } ?: throw NullPointerException("reading byte array returns null")
    }

    /**
     * Returns the size of a give value type.
     * @param formatType the format whose size to be querried
     * @return The size of the format type
     */
    fun getTypeLen(formatType: Int):Int {
        return formatType and 0x0F
    }
}