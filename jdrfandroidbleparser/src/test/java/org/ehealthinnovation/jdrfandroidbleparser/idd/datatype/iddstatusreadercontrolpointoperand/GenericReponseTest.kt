package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.Opcode
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.ResponseCode
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class GenericReponseTest: BaseTest(){
    /**
     * Data class for holding test vector
     */
    class TestVector(
            val testPacket: ByteArray,
            val expectedParsingResult: Boolean,
            val expectedRequestCode: Opcode,
            val expectedResponseCode: ResponseCode
    )

    val testVectors = mutableMapOf<Int, TestVector>()

    init {
        /**
         * Test Packet 1
         * Normal response vector
         */
        testVectors[1] = TestVector(
                testPacket = byteArrayOf(0x0C, 0x03, 0x0F),
                expectedParsingResult = true,
                expectedRequestCode = Opcode.RESET_STATUS,
                expectedResponseCode = ResponseCode.SUCCESS
        )

         /**
         * Test Packet 2
         * Normal response vector, failed response
         */
        testVectors[2] = TestVector(
                testPacket = byteArrayOf(0x95.toByte(), 0x03, 0x71),
                expectedParsingResult = true,
                expectedRequestCode = Opcode.GET_TOTAL_DAILY_INSULIN_STATUS,
                expectedResponseCode = ResponseCode.INVALID_OPERAND
        )


    }


    @Test
    fun testParsingSuccessIndicator(){
        System.out.printf("testParsingSuccessIndicator\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedParsingResult, GenericReponse(testVector.value.testPacket, mockBTCharacteristic(testVector.value.testPacket)).parse())
        }
    }

    @Test
    fun testParsingRequestOpcode(){
        System.out.printf("testParsingRequestOpcode\n")
        var objectUnderTest: GenericReponse
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            objectUnderTest =  GenericReponse(testVector.value.testPacket, mockBTCharacteristic(testVector.value.testPacket))
            objectUnderTest.parse()
            Assert.assertEquals(testVector.value.expectedRequestCode, objectUnderTest.opcode)
        }
    }

    @Test
    fun testParsingResponseOpcode(){
        System.out.printf("testParsingResponse\n")
        var objectUnderTest: GenericReponse
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            objectUnderTest =  GenericReponse(testVector.value.testPacket, mockBTCharacteristic(testVector.value.testPacket))
            objectUnderTest.parse()
            Assert.assertEquals(testVector.value.expectedResponseCode, objectUnderTest.responseCode)
        }
    }
}