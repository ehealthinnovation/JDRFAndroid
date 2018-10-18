package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype

import android.bluetooth.BluetoothGattCharacteristic
import java.util.*

/**
 * This class implements the interface [Writable].
 *
 * It is the base class for operand in the control point. A set of basic operations are provided
 * for writing an serialized object.
 *
 * Subclass must implement the [compose] method, the details of this method varies with each operand.
 *
 * This class preserve the state of composing, so [compose] should not be called twice after an object
 * is created.
 *
 * @author Harry Qiu
 * @since 0.0.1
 */
abstract class BaseOperandWriter(c: BluetoothGattCharacteristic? = null) : Writable {

    open val tag = BaseOperandWriter::class.java.canonicalName as String


    private var offset: Int = 0
    private lateinit var helperCharacteristic: BluetoothGattCharacteristic
    lateinit var rawData: ByteArray

    init {
        System.out.printf("init block: loading up raw data\n")
        if (c == null) {
            helperCharacteristic = BluetoothGattCharacteristic(UUID.randomUUID(), BluetoothGattCharacteristic.PROPERTY_READ, BluetoothGattCharacteristic.PERMISSION_READ)
        } else {
            helperCharacteristic = c
        }
        rawData = helperCharacteristic.value
    }

    /**
     * Serialize the operand into [ByteArray] and store it to [rawData]
     * @return false if composing is not successful; true otherwise.
     */
    abstract fun compose(): Boolean

    /**
     * Return true if all arguments required to compose a operand is valid
     */
    abstract fun hasValidArguments(): Boolean

    override fun setIntValue(value: Int, formatType: Int): Int {
        var sizeOfBytesWritten = 0
        if (helperCharacteristic.setValue(value, formatType, offset)) {
            rawData = helperCharacteristic.value
            sizeOfBytesWritten = getTypeLen(formatType)
            this.offset += sizeOfBytesWritten
        }
        return sizeOfBytesWritten
    }

    override fun setFloatValue(mantissa: Int, exponent: Int, formatType: Int): Int {
        var sizeOfBytesWritten = 0
        if (helperCharacteristic.setValue(mantissa, exponent, formatType, offset)) {
            rawData = helperCharacteristic.value
            sizeOfBytesWritten = getTypeLen(formatType)
            this.offset += sizeOfBytesWritten
        }
        return sizeOfBytesWritten
    }

    override fun setStringValue(value: String): Int {
        var sizeOfBytesWritten = 0
        if (helperCharacteristic.setValue(value)) {
            rawData = helperCharacteristic.value
            sizeOfBytesWritten = rawData.size
            this.offset += sizeOfBytesWritten
        }
        return sizeOfBytesWritten
    }

    protected fun setByteArray(data: ByteArray): Int {
        var sizeOfBytesWritten = 0
        if (helperCharacteristic.setValue(data)) {
            rawData = helperCharacteristic.value
            sizeOfBytesWritten = data.size
            this.offset += sizeOfBytesWritten
        }
        return sizeOfBytesWritten
    }

    /**
     * Returns the size of a give value type.
     * @param formatType the format whose size to be querried
     * @return The size of the format type
     */
    fun getTypeLen(formatType: Int): Int {
        return formatType and 0x0F
    }
}