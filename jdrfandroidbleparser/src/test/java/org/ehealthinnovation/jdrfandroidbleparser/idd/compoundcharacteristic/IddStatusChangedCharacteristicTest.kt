package org.ehealthinnovation.jdrfandroidbleparser.idd.compoundcharacteristic

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statuschanged.Flags
import org.junit.Assert
import org.junit.Test
import java.util.*

class IddStatusChangedCharacteristicTest : BaseTest() {
    /**
     * Data class for hosting test vector
     */
    class IddStatusChangedTestVector(
            val testPacket: ByteArray,
            val hasCrc: Boolean,
            val hasE2eCounter: Boolean,
            val expectedParsingResult: Boolean,
            val expectedFlags: EnumSet<Flags>?,
            val expectedE2eCounter: Int?
    )

    val testVectors = mutableMapOf<Int, IddStatusChangedTestVector>()

    init {
        /**
         * Test Packet 1
         * Normal idd status change test vector
         */
        testVectors[1] = IddStatusChangedTestVector(
                testPacket = byteArrayOf(0x95.toByte(), 0x00),
                hasCrc = false,
                hasE2eCounter = false,
                expectedParsingResult = true,
                expectedFlags = EnumSet.of<Flags>(Flags.THERAPY_CONTROL_STATE_CHANGED, Flags.RESERVOIR_STATUS_CHANGED, Flags.TOTAL_DAILY_INSULIN_STATUS_CHANGED, Flags.HISTORY_EVENT_RECORDED),
                expectedE2eCounter = null
        )

        /**
         * Test Packet 2
         * Normal idd status change, with counter
         */
        testVectors[2] = IddStatusChangedTestVector(
                testPacket = byteArrayOf(0x95.toByte(), 0x00, 0x12),
                hasCrc = false,
                hasE2eCounter = true,
                expectedParsingResult = true,
                expectedFlags = EnumSet.of<Flags>(Flags.THERAPY_CONTROL_STATE_CHANGED, Flags.RESERVOIR_STATUS_CHANGED, Flags.TOTAL_DAILY_INSULIN_STATUS_CHANGED, Flags.HISTORY_EVENT_RECORDED),
                expectedE2eCounter = 18
        )

        /**
         * Test Packet 3
         *  Normal idd status change, with counter and CRC
         */
        testVectors[3] = IddStatusChangedTestVector(
                testPacket = byteArrayOf(0x95.toByte(), 0x00, 0x12, 0x64.toByte(), 0xBA.toByte()),
                hasCrc = true,
                hasE2eCounter = true,
                expectedParsingResult = true,
                expectedFlags = EnumSet.of<Flags>(Flags.THERAPY_CONTROL_STATE_CHANGED, Flags.RESERVOIR_STATUS_CHANGED, Flags.TOTAL_DAILY_INSULIN_STATUS_CHANGED, Flags.HISTORY_EVENT_RECORDED),
                expectedE2eCounter = 18
        )


        /**
         * Test Packet 4
         *  Normal idd status change, without counter and with CRC
         */
        testVectors[4] = IddStatusChangedTestVector(
                testPacket = byteArrayOf(0x95.toByte(), 0x00, 0x5D.toByte(), 0x97.toByte()),
                hasCrc = true,
                hasE2eCounter = false,
                expectedParsingResult = true,
                expectedFlags = EnumSet.of<Flags>(Flags.THERAPY_CONTROL_STATE_CHANGED, Flags.RESERVOIR_STATUS_CHANGED, Flags.TOTAL_DAILY_INSULIN_STATUS_CHANGED, Flags.HISTORY_EVENT_RECORDED),
                expectedE2eCounter = null
        )

        /**
         * Test Packet 5
         *  Normal idd status change, without counter and with CRC (negative case)
         */
        testVectors[5] = IddStatusChangedTestVector(
                testPacket = byteArrayOf(0x95.toByte(), 0x00, 0x5D.toByte(), 0x98.toByte()),
                hasCrc = true,
                hasE2eCounter = false,
                expectedParsingResult = false,
                expectedFlags = null,
                expectedE2eCounter = null
        )
    }


    @Test
    fun testParsingSuccessIndicator() {
        System.out.printf("testParsingSuccessIndicator\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedParsingResult, IddStatusChangedCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).successfulParsing)
        }
    }

    @Test
    fun testParsingFlags() {
        System.out.printf("testParsingFlags\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedFlags, IddStatusChangedCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).flag)
        }
    }

    @Test
    fun testParsingE2eCounter() {
        System.out.printf("testParsingE2eCounter\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            System.out.printf("the e2ecounter is ${testVector.value.hasE2eCounter}\n")
            Assert.assertEquals(testVector.value.expectedE2eCounter, IddStatusChangedCharacteristic(characteristic = mockBTCharacteristic(testVector.value.testPacket), hasCrc = testVector.value.hasCrc, hasE2eCounter = testVector.value.hasE2eCounter).e2eCounter)
        }
    }


}