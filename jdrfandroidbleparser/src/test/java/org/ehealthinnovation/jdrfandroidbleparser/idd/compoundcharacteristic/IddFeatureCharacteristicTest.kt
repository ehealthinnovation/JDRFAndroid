package org.ehealthinnovation.jdrfandroidbleparser.idd.compoundcharacteristic

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.feature.Flags
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class IddFeatureCharacteristicTest: BaseTest(){
    /**
     * Data class for hosting test vector
     */
    class IddFeatureTestVector(
            val testPacket: ByteArray,
            val hasCrc: Boolean,
            val hasE2eCounter: Boolean,
            val expectedParsingResult: Boolean,
            val expectedE2eCrc: Int?,
            val expectedE2eCounter: Int?,
            val expectedConcentration: Float?,
            val expectedFlags: EnumSet<Flags>?
    )

    val testVectors = mutableMapOf<Int, IddFeatureTestVector>()

    init {
        /**
         * Test packet 1
         * minimal packet
         */
        testVectors[1] = IddFeatureTestVector(
                testPacket = byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0x00, 0x12, 0xF0.toByte(), 0x00, 0x00, 0x00),
                hasCrc = false,
                hasE2eCounter = false,
                expectedParsingResult = true,
                expectedE2eCrc = 0xFFFF,
                expectedE2eCounter = 0,
                expectedConcentration = 1.8.toFloat(),
                expectedFlags = EnumSet.noneOf(Flags::class.java)
        )

        /**
         * Test packet 2
         * minimal packet wrong crc
         */
        testVectors[2] = IddFeatureTestVector(
                testPacket = byteArrayOf(0xFF.toByte(), 0xFE.toByte(), 0x00, 0x12, 0xF0.toByte(), 0x00, 0x00, 0x00),
                hasCrc = false,
                hasE2eCounter = false,
                expectedParsingResult = false,
                expectedE2eCrc = 0xFEFF,
                expectedE2eCounter = 0,
                expectedConcentration = 1.8.toFloat(),
                expectedFlags = EnumSet.noneOf(Flags::class.java)
        )

        /**
         * Test packet 3
         * minimal packet wrong counter value
         */
        testVectors[3] = IddFeatureTestVector(
                testPacket = byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0x01, 0x12, 0xF0.toByte(), 0x00, 0x00, 0x00),
                hasCrc = false,
                hasE2eCounter = false,
                expectedParsingResult = false,
                expectedE2eCrc = 0xFFFF,
                expectedE2eCounter = 1,
                expectedConcentration = 1.8.toFloat(),
                expectedFlags = EnumSet.noneOf(Flags::class.java)
        )

        /**
         * Test packet 4
         * Packet indicating support for E2E protection
         */
        testVectors[4] = IddFeatureTestVector(
                testPacket = byteArrayOf(0xb2.toByte(), 0xdd.toByte(), 0x01, 0x12, 0xF0.toByte(), 0x01, 0x00, 0x00),
                hasCrc = false,
                hasE2eCounter = false,
                expectedParsingResult = true,
                expectedE2eCrc = 0xddb2,
                expectedE2eCounter = 1,
                expectedConcentration = 1.8.toFloat(),
                expectedFlags = EnumSet.of(Flags.E2E_PROTECTION_SUPPORTED)
        )

    }

    @Test
    fun testParsingSuccessIndicator(){
        System.out.printf("testParsingSuccessIndicator\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedParsingResult, IddFeatureCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).successfulParsing)
        }
    }

    @Test
    fun testParsingE2eCrc(){
        System.out.printf("testParsingE2eCrc\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedE2eCrc, IddFeatureCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).e2eCrc)
        }
    }

    @Test
    fun testParsingE2eCounter(){
        System.out.printf("testParsingE2eCounter\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedE2eCounter, IddFeatureCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).e2eCounter)
        }
    }

     @Test
    fun testParsingConcentration(){
        System.out.printf("testParsingConcentration\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedConcentration, IddFeatureCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).insulinConcentration)
        }
    }

    @Test
    fun testParsingFlag(){
        System.out.printf("testParsingFlag\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedFlags, IddFeatureCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).flags)
        }
    }


}