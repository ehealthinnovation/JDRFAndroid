package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class BaseOperandTest: BaseTest() {

    class TestBaseOperand(data: ByteArray, c: BluetoothGattCharacteristic) : BaseOperand(data, c){
        override fun parse(): Boolean {
            //This is a dummy method for implementing the base Operand
            return true
        }
    }

    lateinit var operandUnderTest: TestBaseOperand
    val testPacket = byteArrayOf(0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x00)

    @Before
    fun setup(){
        System.out.printf("setting up the test\n")
        operandUnderTest = TestBaseOperand(testPacket, mockBTCharacteristic(testPacket))
    }

    @Test
    fun getNextInt() {
        //get next int8
        Assert.assertEquals(0x01, operandUnderTest.getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        //get next int16
        Assert.assertEquals(0x0302, operandUnderTest.getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16))
    }

    @Test
    fun getNextFloat() {
        //get next sfloat
        Assert.assertEquals(513.toFloat(), operandUnderTest.getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT))
        //get next sfloat
        Assert.assertEquals(1027.toFloat(), operandUnderTest.getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT))
    }
}