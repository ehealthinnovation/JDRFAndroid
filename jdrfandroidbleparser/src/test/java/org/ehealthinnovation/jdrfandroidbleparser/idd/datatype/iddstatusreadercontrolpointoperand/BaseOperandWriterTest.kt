package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BaseOperandWriterTest: BaseTest() {
    class TestBaseOperand(c: BluetoothGattCharacteristic) : BaseOperandWriter(c){
        override fun hasValidArguments(): Boolean {
            return true
        }

        override fun compose(): Boolean {
            //This is a dummy class created for test purpose
            return true
        }
    }

    lateinit var operandUnderTest: TestBaseOperand

    @Before
    fun setup(){
        System.out.printf("setting up the test\n")
        operandUnderTest = TestBaseOperand(mockBTCharacteristic(ByteArray(0)))
    }

    @After
    fun tearDown(){

    }

    @Test
    fun setIntValue() {
        //test writing a uint 8
        val expectedOffset = 1
        val expectedResultByteArray = byteArrayOf(0x13)
        val integertoWrite = 0x13
        Assert.assertEquals(expectedOffset, operandUnderTest.setIntValue(integertoWrite, BluetoothGattCharacteristic.FORMAT_UINT8))
        Assert.assertEquals(true, operandUnderTest.rawData.contentEquals(expectedResultByteArray))
    }

    @Test
    fun  setIntValue16(){
        //test writing a uint 16
        val expectedOffset = 2
        val expectedResultByteArray = byteArrayOf(0x12, 0x34)
        val integertoWrite = 0x3412
        Assert.assertEquals(expectedOffset, operandUnderTest.setIntValue(integertoWrite, BluetoothGattCharacteristic.FORMAT_UINT16))
        System.out.printf("composed byte array ${operandUnderTest.rawData.contentToString()}\n")
        Assert.assertEquals(true, operandUnderTest.rawData.contentEquals(expectedResultByteArray))
    }


    @Test
    fun setFloatValue() {
        //test writing a short float
        val expectedOffset = 2
        val expectedResultByteArray = byteArrayOf(0x20, 0xF3.toByte())
        val mantissaWrite = 800
        val exponentWrite = -1
        Assert.assertEquals(expectedOffset, operandUnderTest.setFloatValue(mantissaWrite, exponentWrite, BluetoothGattCharacteristic.FORMAT_SFLOAT))
        System.out.printf("composed byte array ${operandUnderTest.rawData.contentToString()}\n")
        Assert.assertEquals(true, operandUnderTest.rawData.contentEquals(expectedResultByteArray))
    }

    @Test
    fun setStringValue() {
        //todo  this test is not implemented as our application does not requires string writing yet.
    }


}