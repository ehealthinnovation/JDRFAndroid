package org.ehealthinnovation.jdrfandroidbleparser.idd.compoundcharacteristic

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.annunciationstatus.AnnunciationStatusValues
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.annunciationstatus.AnnunciationType
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.annunciationstatus.Flags
import org.ehealthinnovation.jdrfandroidbleparser.utility.CrcHelperTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class IddAnnunciationStatusCharacteristicTest : BaseTest() {
    /**
     * Data class for hosting test vector
     */
    class IddAnnunciationStatusTestVector(
            val testPacket: ByteArray,
            val hasCrc: Boolean,
            val hasE2eCounter: Boolean,
            val expectedParsingResult: Boolean,
            val expectedFlags: EnumSet<Flags>?,
            val expectedAnnunciationId: Int?,
            val expectedAnnunciationType: AnnunciationType?,
            val expectedAnnunciationStatusValues: AnnunciationStatusValues?,
            val expectedAuxInfo: MutableList<Int>,
            val expectedE2eCounter: Int?
    )

    val testVectors = mutableMapOf<Int, IddAnnunciationStatusTestVector>()

    init {
        /**
         * Test Packet 1
         * minimal packet
         */
        testVectors[1] = IddAnnunciationStatusTestVector(
                testPacket = byteArrayOf(0x00),
                hasCrc = false,
                hasE2eCounter = false,
                expectedParsingResult = true,
                expectedFlags = EnumSet.noneOf(Flags::class.java),
                expectedAnnunciationId = null,
                expectedAnnunciationType = null,
                expectedAnnunciationStatusValues = null,
                expectedAuxInfo = mutableListOf(),
                expectedE2eCounter = null
        )

        /**
         * Test Packet 2
         * contain an annunication status
         */
        testVectors[2] = IddAnnunciationStatusTestVector(
                testPacket = byteArrayOf(0x01, 0x34, 0x12, 0xAA.toByte(), 0x00, 0x33),
                hasCrc = false,
                hasE2eCounter = false,
                expectedParsingResult = true,
                expectedFlags = EnumSet.of(Flags.ANNUNCIATION_PRESENT),
                expectedAnnunciationId = 0x1234,
                expectedAnnunciationType = AnnunciationType.BATTERY_EMPTY,
                expectedAnnunciationStatusValues = AnnunciationStatusValues.PENDING,
                expectedAuxInfo = mutableListOf(),
                expectedE2eCounter = null
        )

        /**
         * Test Packet 3
         * contain an annunciation status, 1 auxinfo
         */
        testVectors[3] = IddAnnunciationStatusTestVector(
                testPacket = byteArrayOf(0x03, 0x34, 0x12, 0xAA.toByte(), 0x00, 0x33, 0x01, 0x00),
                hasCrc = false,
                hasE2eCounter = false,
                expectedParsingResult = true,
                expectedFlags = EnumSet.of(Flags.ANNUNCIATION_PRESENT, Flags.AUXINFO1_PRESENT),
                expectedAnnunciationId = 0x1234,
                expectedAnnunciationType = AnnunciationType.BATTERY_EMPTY,
                expectedAnnunciationStatusValues = AnnunciationStatusValues.PENDING,
                expectedAuxInfo = mutableListOf(1),
                expectedE2eCounter = null

        )

        /**
         * Test Packet 4
         * contain an annunciation status, 1 auxinfo -5 auxinfo
         */
        testVectors[4] = IddAnnunciationStatusTestVector(
                testPacket = byteArrayOf(0x7F, 0x34, 0x12, 0xAA.toByte(), 0x00, 0x33, 0x01, 0x00, 0x02, 0x00, 0x03, 0x00, 0x04, 0x00, 0x05, 0x00),
                hasCrc = false,
                hasE2eCounter = false,
                expectedParsingResult = true,
                expectedFlags = EnumSet.of(Flags.ANNUNCIATION_PRESENT, Flags.AUXINFO1_PRESENT, Flags.AUXINFO2_PRESENT, Flags.AUXINFO3_PRESENT, Flags.AUXINFO4_PRESENT, Flags.AUXINFO5_PRESENT),
                expectedAnnunciationId = 0x1234,
                expectedAnnunciationType = AnnunciationType.BATTERY_EMPTY,
                expectedAnnunciationStatusValues = AnnunciationStatusValues.PENDING,
                expectedAuxInfo = mutableListOf(1, 2, 3, 4, 5),
                expectedE2eCounter = null
        )

        /**
         * Test Packet 5
         * contain an annunciation status, 1 auxinfo -5 auxinfo, e2e counter
         */
        testVectors[5] = IddAnnunciationStatusTestVector(
                testPacket = byteArrayOf(0x7F, 0x34, 0x12, 0xAA.toByte(), 0x00, 0x33, 0x01, 0x00, 0x02, 0x00, 0x03, 0x00, 0x04, 0x00, 0x05, 0x00, 0x09),
                hasCrc = false,
                hasE2eCounter = true,
                expectedParsingResult = true,
                expectedFlags = EnumSet.of(Flags.ANNUNCIATION_PRESENT, Flags.AUXINFO1_PRESENT, Flags.AUXINFO2_PRESENT, Flags.AUXINFO3_PRESENT, Flags.AUXINFO4_PRESENT, Flags.AUXINFO5_PRESENT),
                expectedAnnunciationId = 0x1234,
                expectedAnnunciationType = AnnunciationType.BATTERY_EMPTY,
                expectedAnnunciationStatusValues = AnnunciationStatusValues.PENDING,
                expectedAuxInfo = mutableListOf(1, 2, 3, 4, 5),
                expectedE2eCounter = 9
        )

        /**
         * Test Packet 6
         * contain an annunciation status, 1 auxinfo -5 auxinfo, e2e counter, crc
         */
        testVectors[6] = IddAnnunciationStatusTestVector(
                testPacket = byteArrayOf(0x7F, 0x34, 0x12, 0xAA.toByte(), 0x00, 0x33, 0x01, 0x00, 0x02, 0x00, 0x03, 0x00, 0x04, 0x00, 0x05, 0x00, 0x09, 0x73, 0x6A),
                hasCrc = true,
                hasE2eCounter = true,
                expectedParsingResult = true,
                expectedFlags = EnumSet.of(Flags.ANNUNCIATION_PRESENT, Flags.AUXINFO1_PRESENT, Flags.AUXINFO2_PRESENT, Flags.AUXINFO3_PRESENT, Flags.AUXINFO4_PRESENT, Flags.AUXINFO5_PRESENT),
                expectedAnnunciationId = 0x1234,
                expectedAnnunciationType = AnnunciationType.BATTERY_EMPTY,
                expectedAnnunciationStatusValues = AnnunciationStatusValues.PENDING,
                expectedAuxInfo = mutableListOf(1, 2, 3, 4, 5),
                expectedE2eCounter = 9
        )

    }

    @Test
    fun testParsingSuccessIndicator() {
        System.out.printf("testParsingSuccessIndicator\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedParsingResult, IddAnnunciationStatusCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).successfulParsing)
        }
    }

    @Test
    fun testParsingAnnunciationType() {
        System.out.printf("testParsingAnnunciationType\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedAnnunciationType, IddAnnunciationStatusCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).annunciationType)
        }
    }

    @Test
    fun testParsingAnnunciationId() {
        System.out.printf("testParsingAnnunciationId\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedAnnunciationId, IddAnnunciationStatusCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).annunciationInstanceId)
        }
    }


    @Test
    fun testParsingAnnunciationStatus() {
        System.out.printf("testParsingAnnunciationStatus\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedAnnunciationStatusValues, IddAnnunciationStatusCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).annunciationStatus)
        }
    }

    @Test
    fun testParsingAnnunciationAuxInfo() {
        System.out.printf("testParsingAnnunciationAuxInfo\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedAuxInfo, IddAnnunciationStatusCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).auxInfo)
        }
    }

    @Test
    fun testParsingFlags() {
        System.out.printf("testParsingFlags\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedFlags, IddAnnunciationStatusCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).flags)
        }
    }

    @Test
    fun testParsingE2ECounter() {
        System.out.printf("testParsingE2ECounter\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedE2eCounter, IddAnnunciationStatusCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).e2eCounter)
        }
    }
}