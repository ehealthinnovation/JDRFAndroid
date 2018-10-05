package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import java.net.DatagramPacket

class ActiveBolusIDsTest: BaseTest(){
    /**
     * Data class for holding test vector
     */
    class TestVector(
            val testPacket: ByteArray,
            val expectedParsingResult: Boolean,
            val expectedNumberOfActiveBolusIds: Int?,
            val expectedBolusIds: MutableList<Int>
    )

    val testVectors = mutableMapOf<Int, TestVector>()

    init {
        /**
         * Test Packet 1
         * No Active bolus
         */
        testVectors[1] = TestVector(
                testPacket = byteArrayOf(0x00),
                expectedParsingResult = true,
                expectedNumberOfActiveBolusIds = 0,
                expectedBolusIds = ArrayList<Int>()
        )

        /**
         * Test Packet 2
         * One active bolus
         */
        testVectors[2] = TestVector(
                testPacket = byteArrayOf(0x01, 0x11, 0x01),
                expectedParsingResult = true,
                expectedNumberOfActiveBolusIds = 1,
                expectedBolusIds = mutableListOf(0x0111)
        )

        /**
         * Test Packet 3
         * Seven active bolus
         */
        testVectors[3] = TestVector(
                testPacket = byteArrayOf(0x07, 0x11, 0x01, 0x22, 0x02, 0x33, 0x03, 0x44, 0x04, 0x55, 0x05, 0x66, 0x06, 0x77, 0x07),
                expectedParsingResult = true,
                expectedNumberOfActiveBolusIds = 7,
                expectedBolusIds = mutableListOf(0x0111, 0x0222, 0x0333, 0x0444, 0x0555, 0x0666, 0x0777)
        )
    }

        @Test
    fun testParsingSuccessIndicator(){
        System.out.printf("testParsingSuccessIndicator\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedParsingResult, ActiveBolusIDs(testVector.value.testPacket, mockBTCharacteristic(testVector.value.testPacket)).parse())
        }
    }

    @Test
    fun testParsingNumberOfActiveBoluses(){
        System.out.printf("testParsingNumberOfActiveBoluses\n")
        var objectUnderTest: ActiveBolusIDs
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            objectUnderTest =  ActiveBolusIDs(testVector.value.testPacket, mockBTCharacteristic(testVector.value.testPacket))
            objectUnderTest.parse()
            Assert.assertEquals(testVector.value.expectedNumberOfActiveBolusIds, objectUnderTest.numberOfActiveBoluses)
        }
    }

    @Test
    fun testParsingActiveBolusIds(){
        System.out.printf("testParsingActiveBolusIds\n")
        var objectUnderTest: ActiveBolusIDs
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            objectUnderTest =  ActiveBolusIDs(testVector.value.testPacket, mockBTCharacteristic(testVector.value.testPacket))
            objectUnderTest.parse()
            Assert.assertEquals(testVector.value.expectedBolusIds, objectUnderTest.bolusIDs)
        }
    }
}