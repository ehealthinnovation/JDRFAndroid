package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class BaseOperandTest {

    class TestBaseOperand(data: ByteArray) : BaseOperand(data){
        override fun parse(): Boolean {
            //This is a dummy method for implementing the base Operand
            return true
        }
    }

    lateinit var operandUnderTest: TestBaseOperand
    val testPacket = byteArrayOf(0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08)

    @Before
    fun setup(){
        System.out.printf("setting up the test\n")
        operandUnderTest = TestBaseOperand(testPacket)
    }

    @Test
    fun getNextInt() {
        //get next int8
        Assert.assertEquals()

    }

    @Test
    fun getNextFloat() {
    }

    @Test
    fun getNextString() {
    }

    @Test
    fun getTypeLen() {
    }
}