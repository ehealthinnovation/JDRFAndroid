package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statuschanged.Flags
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class ResetStatusTest: BaseTest(){

    /**
     * Data class for hosting test vector
     */
    class TestVector(
            val testPacket: ByteArray,
            val expectedParsingResult: Boolean,
            val expectedFlags: EnumSet<Flags>?
    )

    val testVectors = mutableMapOf<Int, TestVector>()

    init {
        /**
         * Test packet 1
         * Normal reset status
         */
        testVectors[1] = TestVector(
                testPacket = byteArrayOf(0x21,0x00),
                expectedParsingResult = true,
                expectedFlags = EnumSet.of(Flags.THERAPY_CONTROL_STATE_CHANGED, Flags.ACTIVE_BASAL_RATE_STATUS_CHANGED)
        )
    }


    @Test
    fun testParsingSuccessIndicator(){
        System.out.printf("testParsingSuccessIndicator\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedParsingResult, ResetStatus(testVector.value.testPacket, mockBTCharacteristic(testVector.value.testPacket)).parse())
        }
    }

    @Test
    fun testParsingFlags(){
        System.out.printf("testParsingFlags\n")
        var objectUnderTest: ResetStatus
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            objectUnderTest =  ResetStatus(testVector.value.testPacket, mockBTCharacteristic(testVector.value.testPacket))
            objectUnderTest.parse()
            Assert.assertEquals(testVector.value.expectedFlags, objectUnderTest.flags)
        }
    }




}