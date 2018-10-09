package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import java.util.*

abstract class BaseOperandWriter(c: BluetoothGattCharacteristic? = null): Writable{

    open val tag = BaseOperandWriter::class.java.canonicalName as String


    private var offset: Int = 0
    private lateinit var helperCharacteristic : BluetoothGattCharacteristic
    lateinit var rawData: ByteArray

    init {
        System.out.printf("init block: loading up raw data\n")
        if(c == null){
           helperCharacteristic = BluetoothGattCharacteristic( UUID.randomUUID(), BluetoothGattCharacteristic.PROPERTY_READ, BluetoothGattCharacteristic.PERMISSION_READ)
        }else{
            helperCharacteristic = c
        }
        rawData = helperCharacteristic.value
    }

    /**
     * Serialize the operand into [ByteArray] and store it to [rawData]
     * @return false if composing is not successful; true otherwise.
     */
    abstract fun compose(): Boolean


    override fun setIntValue(value: Int, formatType: Int): Int {
        var sizeOfBytesWritten = 0
       if(helperCharacteristic.setValue(value, formatType, offset)){
           rawData = helperCharacteristic.value
           sizeOfBytesWritten = getTypeLen(formatType)
           this.offset += sizeOfBytesWritten
       }
        return sizeOfBytesWritten
    }

    override fun setFloatValue(mantissa: Int, exponent: Int, formatType: Int): Int {
         var sizeOfBytesWritten = 0
       if(helperCharacteristic.setValue(mantissa, exponent, formatType, offset)){
           rawData = helperCharacteristic.value
           sizeOfBytesWritten = getTypeLen(formatType)
           this.offset += sizeOfBytesWritten
       }
        return sizeOfBytesWritten
    }

    override fun setStringValue(value: String): Int {
          var sizeOfBytesWritten = 0
       if(helperCharacteristic.setValue(value)){
           rawData = helperCharacteristic.value
           sizeOfBytesWritten = rawData.size
           this.offset += sizeOfBytesWritten
       }
        return sizeOfBytesWritten
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