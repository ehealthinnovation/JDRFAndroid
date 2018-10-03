package org.ehealthinnovation.jdrfandroidbleparser.idd.compoundcharacteristic

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.status.Flags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.status.OperationalState
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.status.TherapyControlState
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class IddStatusCharacteristicTest : BaseTest(){
    /**
     * Data class for hosting test vector
     */
    class IddStatusTestVector(
        val testPacket: ByteArray,
        val hasCrc: Boolean,
        val hasE2eCounter: Boolean,
        val expectedParsingResult: Boolean,
        val expectedTherapyControlState: TherapyControlState?,
        val expectedOperationalState: OperationalState?,
        val expectedReservoirRemainingAmount: Float?,
        val expectedFlags: EnumSet<Flags>,
        val expectedE2eCounter: Int?
    )

    val testVectors = mutableMapOf<Int, IddStatusTestVector>()

    init {
        /**
         * Test Packet 1
         * Normal idd status test vector.
         * Therapy Control State - run, operational state state - ready, reservoir amount 5.2IU
         * Flag - reservoir attached
         */
        testVectors[1] = IddStatusTestVector(
                testPacket = byteArrayOf(0x55, 0x96.toByte(), 0x34, 0xF0.toByte(), 0x01),
                hasCrc = false,
                hasE2eCounter = false,
                expectedParsingResult = true,
                expectedTherapyControlState = TherapyControlState.RUN,
                expectedOperationalState = OperationalState.READY,
                expectedReservoirRemainingAmount = 5.2.toFloat(),
                expectedFlags = EnumSet.of(Flags.RESERVOIR_ATTACHED),
                expectedE2eCounter = null
        )

         /**
         * Test Packet 2
         * Normal idd status test vector.
         * Therapy Control State - run, operational state state - ready, reservoir amount 5.2IU
         * Flag - reservoir attached, with cunter
         */
        testVectors[2] = IddStatusTestVector(
                testPacket = byteArrayOf(0x55, 0x96.toByte(), 0x34, 0xF0.toByte(), 0x01, 0x34),
                hasCrc = false,
                hasE2eCounter = true,
                expectedParsingResult = true,
                expectedTherapyControlState = TherapyControlState.RUN,
                expectedOperationalState = OperationalState.READY,
                expectedReservoirRemainingAmount = 5.2.toFloat(),
                expectedFlags = EnumSet.of(Flags.RESERVOIR_ATTACHED),
                expectedE2eCounter = 52
        )

        /**
         * Test Packet 3
         * Normal idd status test vector.
         * Therapy Control State - run, operational state state - ready, reservoir amount 5.2IU
         * Flag - reservoir attached, without counter, with CRC
         */
        testVectors[3] = IddStatusTestVector(
                testPacket = byteArrayOf(0x55, 0x96.toByte(), 0x34, 0xF0.toByte(), 0x01, 0xA5.toByte(), 0x4E),
                hasCrc = true,
                hasE2eCounter = false,
                expectedParsingResult = true,
                expectedTherapyControlState = TherapyControlState.RUN,
                expectedOperationalState = OperationalState.READY,
                expectedReservoirRemainingAmount = 5.2.toFloat(),
                expectedFlags = EnumSet.of(Flags.RESERVOIR_ATTACHED),
                expectedE2eCounter = null
        )

        /**
         * Test Packet 4
         * Normal idd status test vector.
         * Therapy Control State - run, operational state state - ready, reservoir amount 5.2IU
         * Flag - reservoir attached, with counter, with CRC
         */
        testVectors[4] = IddStatusTestVector(
                testPacket = byteArrayOf(0x55, 0x96.toByte(), 0x34, 0xF0.toByte(), 0x01, 0x34, 0x4E, 0x85.toByte()),
                hasCrc = true,
                hasE2eCounter = true,
                expectedParsingResult = true,
                expectedTherapyControlState = TherapyControlState.RUN,
                expectedOperationalState = OperationalState.READY,
                expectedReservoirRemainingAmount = 5.2.toFloat(),
                expectedFlags = EnumSet.of(Flags.RESERVOIR_ATTACHED),
                expectedE2eCounter = 52
        )

    }

    @Test
    fun testParsingSuccessIndicator() {
        System.out.printf("testParsingSuccessIndicator\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedParsingResult, IddStatusCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).successfulParsing)
        }
    }

    @Test
    fun testParsingTherapyState() {
        System.out.printf("testParsingTherapyStatete\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedTherapyControlState, IddStatusCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).therapyControlState)
        }
    }

    @Test
    fun testParsingOperationState() {
        System.out.printf("testParsingOperationState\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedOperationalState, IddStatusCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).operationalState)
        }
    }

    @Test
    fun testParsingRemainingReservoirAmount() {
        System.out.printf("testParsingRemainingReservoirAmount\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedReservoirRemainingAmount, IddStatusCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).reservoirRemainingAmount)
        }
    }

    @Test
    fun testParsingFlag() {
        System.out.printf("testParsingFlag\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedFlags, IddStatusCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).flags)
        }
    }

    @Test
    fun testParsingE2ecounter() {
        System.out.printf("testParsingE2eCounter\n")
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedE2eCounter, IddStatusCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc, testVector.value.hasE2eCounter).e2eCounter)
        }
    }


}