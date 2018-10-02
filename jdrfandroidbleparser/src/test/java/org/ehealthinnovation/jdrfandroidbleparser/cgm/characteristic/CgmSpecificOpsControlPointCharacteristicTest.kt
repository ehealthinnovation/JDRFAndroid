package org.ehealthinnovation.jdrfandroidbleparser.cgm.characteristic

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.cgm.dataobject.CalibrationRecord
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.cgmcp.CalibrationStatus
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.cgmcp.Opcode
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.cgmcp.ResponseCode
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.feature.CgmSampleLocation
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.feature.CgmType
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class CgmSpecificOpsControlPointCharacteristicTest: BaseTest(){

    class  CGMCPTestVector(
            val testPacket: ByteArray,
            val hasCrc: Boolean,
            val expectedParseResult: Boolean,
            var expectedOpcode: Opcode? = null,
            var expectedOperandUnsignedInt: Int?,
            var expectedOperandFloat: Float? = null,
            var expectedOperandRequestOpcode: Opcode? = null,
            var expectedOperandResponseCode: ResponseCode? = null,
            var expectedCalibrationRecord: CalibrationRecord ?= null
    )

    val testVectors= mutableMapOf<Int, CGMCPTestVector>()

    init {
        /**
         * Test Packet 1
         *  Communication interval response
         */
        testVectors[1] = CGMCPTestVector(
                testPacket = byteArrayOf(0x03, 0x01),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.CGM_COMMUNICATION_INTERVAL_RESPONSE,
                expectedOperandUnsignedInt = 1,
                expectedOperandFloat = null,
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )

        /**
         * Test Packet 2
         * Calibration record response
         */
        testVectors[2] = CGMCPTestVector(
                testPacket = byteArrayOf(0x06, 0x39, 0x00, 0x01, 0x02, 0x29, 0x03, 0x04, 0x05, 0x00, 0x04),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.GLUCOSE_CALIBRATION_VALUE_RESPONSE,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = null,
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null,
                expectedCalibrationRecord = CalibrationRecord(
                        calibrationTime = 513,
                        concentration = 57.toFloat(),
                        calibrationDataRecordNumber = 5,
                        nextCalibrationTime = 1027,
                        calibrationType = CgmType.INTERSTITIAL_FLUID,
                        calibrationSampleLocation = CgmSampleLocation.ALTERNATE_SITE_TEST,
                        calibrationStatus = EnumSet.of<CalibrationStatus>(CalibrationStatus.CALIBRATION_PROCESS_PENDING)
                )
        )

        /**
         * Test Packet 3
         * Patient High Response
         */
        testVectors[3] = CGMCPTestVector(
                testPacket = byteArrayOf(0x09, 0x39, 0x00),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.PATIENT_HIGH_ALERT_LEVEL_RESPONSE,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = 57.toFloat(),
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )

        /**
         * Test Packet 4
         * Patient Low Alert Response
         */
        testVectors[4] = CGMCPTestVector(
                testPacket = byteArrayOf(0x0C, 0x39, 0x00),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.PATIENT_LOW_ALERT_LEVEL_RESPONSE,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = 57.toFloat(),
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )

        /**
         * Test Packet 5
         * Hypo Alert Response
         */
        testVectors[5] = CGMCPTestVector(
                testPacket = byteArrayOf(0x0F, 0x39, 0x00),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.HYPO_ALERT_LEVEL_RESPONSE,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = 57.toFloat(),
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )

        /**
         * Test Packet 6
         * Hyper Alert Response
         */
        testVectors[6] = CGMCPTestVector(
                testPacket = byteArrayOf(0x12, 0x39, 0x00),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.HYPER_ALERT_LEVEL_RESPONSE,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = 57.toFloat(),
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )

        /**
         * Test Packet 7
         * Rate of decrease response
         */
        testVectors[7] = CGMCPTestVector(
                testPacket = byteArrayOf(0x15, 0x39, 0x00),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.RATE_OF_DECREASE_ALERT_LEVEL_RESPONSE,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = 57.toFloat(),
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )

        /**
         * Test Packet 8
         * Rate of increase response
         */
        testVectors[8] = CGMCPTestVector(
                testPacket = byteArrayOf(0x18, 0x39, 0x00),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.RATE_OF_INCREASE_ALERT_LEVEL_RESPONSE,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = 57.toFloat(),
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )

        /**
         * Test Packet 9
         * Generic response - stop session, success
         */
        testVectors[9] = CGMCPTestVector(
                testPacket = byteArrayOf(0x1c, 0x1b, 0x01),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.RESPONSE_CODE,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = null,
                expectedOperandRequestOpcode = Opcode.STOP_THE_SESSION,
                expectedOperandResponseCode = ResponseCode.SUCCESS
        )

        /**
         * Test Packet 10
         * Generic response - stop session, opcode not supported
         */
        testVectors[10] = CGMCPTestVector(
                testPacket = byteArrayOf(0x1c, 0x1b, 0x02),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.RESPONSE_CODE,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = null,
                expectedOperandRequestOpcode = Opcode.STOP_THE_SESSION,
                expectedOperandResponseCode = ResponseCode.OP_CODE_NOT_SUPPORTED
        )


        /**
         * Test Packet 11
         * Generic response - stop session, invalid operand
         */
        testVectors[11] = CGMCPTestVector(
                testPacket = byteArrayOf(0x1c, 0x1b, 0x03),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.RESPONSE_CODE,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = null,
                expectedOperandRequestOpcode = Opcode.STOP_THE_SESSION,
                expectedOperandResponseCode = ResponseCode.INVALID_OPERAND
        )

        /**
         * Test Packet 12
         * Generic response - stop session, procedure not completed
         */
        testVectors[12] = CGMCPTestVector(
                testPacket = byteArrayOf(0x1c, 0x1b, 0x04),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.RESPONSE_CODE,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = null,
                expectedOperandRequestOpcode = Opcode.STOP_THE_SESSION,
                expectedOperandResponseCode = ResponseCode.PROCEDURE_NOT_COMPLETED
        )


        /**
         * Test Packet 13
         * Generic response - stop session, parameter out of range
         */
        testVectors[13] = CGMCPTestVector(
                testPacket = byteArrayOf(0x1c, 0x1b, 0x05),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.RESPONSE_CODE,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = null,
                expectedOperandRequestOpcode = Opcode.STOP_THE_SESSION,
                expectedOperandResponseCode = ResponseCode.PARAMETER_OUT_OF_RANGE
        )

        /**
         * Test Packet 14
         * Set communication interval
         */
        testVectors[14] = CGMCPTestVector(
                testPacket = byteArrayOf(0x01, 0x01),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.SET_CGM_COMMUNICATION_INTERVAL,
                expectedOperandUnsignedInt = 1,
                expectedOperandFloat = null,
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )


        /**
         * Test Packet 15
         * Set patient  high */
        testVectors[15] = CGMCPTestVector(
                testPacket = byteArrayOf(0x07, 0x39, 0x00.toByte()),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.SET_PATIENT_HIGH_ALERT_LEVEL,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = 57.toFloat(),
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )

        /**
         * Test Packet 16
         * Set patient  low */
        testVectors[16] = CGMCPTestVector(
                testPacket = byteArrayOf(0x0a, 0x39, 0x00.toByte()),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.SET_PATIENT_LOW_ALERT_LEVEL,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = 57.toFloat(),
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )

        /**
         * Test Packet 17
         * Set  Hypo Alert*/
        testVectors[17] = CGMCPTestVector(
                testPacket = byteArrayOf(0x0D, 0x39, 0x00.toByte()),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.SET_HYPO_ALERT_LEVEL,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = 57.toFloat(),
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )

        /**
         * Test Packet 18
         * Set  Hyper Alert*/
        testVectors[18] = CGMCPTestVector(
                testPacket = byteArrayOf(0x10, 0x39, 0x00.toByte()),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.SET_HYPER_ALERT_LEVEL,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = 57.toFloat(),
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )

        /**
         * Test Packet 19
         * Set Rate of Decrease*/
        testVectors[19] = CGMCPTestVector(
                testPacket = byteArrayOf(0x13, 0x39, 0x00.toByte()),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.SET_RATE_OF_DECREASE_ALERT_LEVEL,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = 57.toFloat(),
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )

        /**
         * Test Packet 20
         * Set Rate of Increase*/
        testVectors[20] = CGMCPTestVector(
                testPacket = byteArrayOf(0x16, 0x39, 0x00.toByte()),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.SET_RATE_OF_INCREASE_ALERT_LEVEL,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = 57.toFloat(),
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )

        /**
         * Test Packet 21
         * Get Communication Interval */
        testVectors[21] = CGMCPTestVector(
                testPacket = byteArrayOf(0x2),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.GET_CGM_COMMUNICATION_INTERVAL,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = null,
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )


        /**
         * Test Packet 22
         * Get patient high alert */
        testVectors[22] = CGMCPTestVector(
                testPacket = byteArrayOf(0x08),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.GET_PATIENT_HIGH_ALERT_LEVEL,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = null,
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )

        /**
         * Test Packet 23
         * Get patient low alert */
        testVectors[23] = CGMCPTestVector(
                testPacket = byteArrayOf(0x0b),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.GET_PATIENT_LOW_ALERT_LEVEL,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = null,
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )

        /**
         * Test Packet 24
         * Get hypo alert alert */
        testVectors[24] = CGMCPTestVector(
                testPacket = byteArrayOf(0x0e),
                hasCrc =  false,
                expectedParseResult = true,
                expectedOpcode =  Opcode.GET_HYPO_ALERT_LEVEL,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = null,
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )


        /**
         * Test Packet 25
         *  Test with CRC, negative case */
        testVectors[25] = CGMCPTestVector(
                testPacket = byteArrayOf(0x03, 0x01, 0x59,  0xca.toByte()),
                hasCrc =  true,
                expectedParseResult = false,
                expectedOpcode =  null,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = null,
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )


        /**
         * Test Packet 26
         *  Get communication interval response*/
        testVectors[26] = CGMCPTestVector(
                testPacket = byteArrayOf(0x03, 0x01, 0x59, 0xcb.toByte()),
                hasCrc =  true,
                expectedParseResult = true,
                expectedOpcode =  Opcode.CGM_COMMUNICATION_INTERVAL_RESPONSE,
                expectedOperandUnsignedInt = 1,
                expectedOperandFloat = null,
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null
        )

        /**
         * Test Packet 27
         * Calibration value response with CRC
         */
        testVectors[27] = CGMCPTestVector(
                testPacket = byteArrayOf(0x06, 0x39, 0x00, 0x01, 0x02, 0x29.toByte(), 0x03, 0x04, 0x05, 0x00, 0x04, 0x1b, 0x38),
                hasCrc =  true,
                expectedParseResult = true,
                expectedOpcode =  Opcode.GLUCOSE_CALIBRATION_VALUE_RESPONSE,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = null,
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null,
                expectedCalibrationRecord = CalibrationRecord(
                        calibrationTime = 513,
                        concentration = 57.toFloat(),
                        calibrationDataRecordNumber = 5,
                        nextCalibrationTime = 1027,
                        calibrationType = CgmType.INTERSTITIAL_FLUID,
                        calibrationSampleLocation = CgmSampleLocation.ALTERNATE_SITE_TEST,
                        calibrationStatus = EnumSet.of(CalibrationStatus.CALIBRATION_PROCESS_PENDING)
                )
        )

        /**
         * Test Packet 28
         * Patient High with CRC
         */
        testVectors[28] = CGMCPTestVector(
                testPacket = byteArrayOf(0x09, 0x39, 0x00, 0x97.toByte(), 0xc4.toByte()),
                hasCrc =  true,
                expectedParseResult = true,
                expectedOpcode =  Opcode.PATIENT_HIGH_ALERT_LEVEL_RESPONSE,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = 57.toFloat(),
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null,
                expectedCalibrationRecord = null
        )

        /**
         * Test Packet 29
         * Hypo Alert with CRC
         */
        testVectors[29] = CGMCPTestVector(
                testPacket = byteArrayOf(0x0F, 0x39, 0x00, 0x4e, 0x12),
                hasCrc =  true,
                expectedParseResult = true,
                expectedOpcode =  Opcode.HYPO_ALERT_LEVEL_RESPONSE,
                expectedOperandUnsignedInt = null,
                expectedOperandFloat = 57.toFloat(),
                expectedOperandRequestOpcode = null,
                expectedOperandResponseCode = null,
                expectedCalibrationRecord = null
        )
    }

    @Test
    fun testParseResult(){
        System.out.printf("testParseResult(n")
        for (testVector in testVectors) {
            System.out.printf("testing testVector ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedParseResult, CgmSpecificOpsControlPointCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc).successfulParsing)
        }
    }

    @Test
    fun testParseOpcode(){
        System.out.printf("testParseOpcode")
        for (testVector in testVectors) {
            System.out.printf("testing testVector ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedOpcode, CgmSpecificOpsControlPointCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc).opcode)
        }
    }

    @Test
    fun testParseOperandInt(){
        System.out.printf("testParseOperandInt")
        for (testVector in testVectors) {
            System.out.printf("testing testVector ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedOperandUnsignedInt, CgmSpecificOpsControlPointCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc).operandUnsignedInteger)
        }
    }

    @Test
    fun testParseOperandFloat(){
        System.out.printf("testParseOperandFloat")
        for (testVector in testVectors) {
            System.out.printf("testing testVector ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedOperandFloat, CgmSpecificOpsControlPointCharacteristic(c = mockBTCharacteristic(testVector.value.testPacket), hasCrc = testVector.value.hasCrc).operandShortFloat)
        }
    }

    @Test
    fun testParseOperandRequestOpcode(){
        System.out.printf("testParseOperandRequestOpcode")
        for (testVector in testVectors) {
            System.out.printf("testing testVector ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedOperandRequestOpcode, CgmSpecificOpsControlPointCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc).operandRequestOpcode)
        }
    }

    @Test
    fun testParseOperandResponseCode(){
        System.out.printf("testParseResponseCode")
        for (testVector in testVectors) {
            System.out.printf("testing testVector ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedOperandResponseCode, CgmSpecificOpsControlPointCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc).operandResponseCode)
        }
    }

    @Test
    fun testParseOperandCalibrationRecord(){
        System.out.printf("testParseOperandCalibrationRecord")
        for (testVector in testVectors) {
            System.out.printf("testing testVector ${testVector.key}\n")
            Assert.assertEquals(true, testVector.value.expectedCalibrationRecord == CgmSpecificOpsControlPointCharacteristic(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc).operandCalibrationRecord)
        }
    }

    @Test
    fun testComposingCgmcp() {
        System.out.printf("testComposingCgmcp\n")
        var testCGMCP: CgmSpecificOpsControlPointCharacteristic
        var successCount = 0
        var skipCount = 0

        for (testVector in testVectors) {
            System.out.printf("testing TestVector ${testVector.key}\n")
            if (testVector.value.expectedParseResult == false) {
                System.out.printf("This case is expected to failed in parsing, so cant be composed backwards.\n")
                skipCount++
                continue
            }
            testCGMCP = CgmSpecificOpsControlPointCharacteristic(mockBTCharacteristic(ByteArray(0)), testVector.value.hasCrc, isComposing = true)
            testCGMCP.opcode = testVector.value.expectedOpcode
            testCGMCP.operandResponseCode = testVector.value.expectedOperandResponseCode
            testCGMCP.operandRequestOpcode = testVector.value.expectedOperandRequestOpcode
            testCGMCP.operandUnsignedInteger = testVector.value.expectedOperandUnsignedInt
            testCGMCP.operandShortFloat = testVector.value.expectedOperandFloat
            testCGMCP.operandCalibrationRecord = testVector.value.expectedCalibrationRecord
            val composedPacket = testCGMCP.composeCharacteristic(testVector.value.hasCrc)
            System.out.printf("composed: " + composedPacket.contentToString() + "\n")
            System.out.printf("test packet: " + testVector.value.testPacket.contentToString() + "\n")
            Assert.assertTrue(testVector.value.testPacket.contentEquals(composedPacket))
        }
        System.out.printf("Total cases: ${testVectors.size} Cases skipped: $skipCount\n")
    }

}