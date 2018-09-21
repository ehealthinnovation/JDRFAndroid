package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.dataobjects.RACPDataObject
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp.Filter
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp.Opcode
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp.Operator
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp.ResponseCode
import org.ehealthinnovation.jdrfandroidbleparser.utility.BluetoothDateTime
import org.junit.Assert
import org.junit.Test

class RACPTest : BaseTest() {
    /**
     * Data class for hosting test vector
     */
    class RacpTestVector(
            val testPacket: ByteArray,
            val expectedParsingResult: Boolean,
            val expectedOpcode: Opcode,
            val expectedOperator: Operator?,
            val expectedFilter: Filter? = null,
            val expectedMinimumSequenceNumber: Int? = null,
            val expectedMaximumSequenceNumber: Int? = null,
            val expectedMinimumUserFacingTime: BluetoothDateTime? = null,
            val expectedMaximumUserFacingTime: BluetoothDateTime? = null,
            val expectedRequestOpcode: Opcode? = null,
            val expectedResponseCode: ResponseCode? = null,
            val expectedNumberOfRecord: Int? = null,
            val hasCrc: Boolean = false
    )

    val racpTestVectors = mutableMapOf<Int, RacpTestVector>()

    init {
        /**
         * packet1
         * general response successful
         */
        racpTestVectors[1] = RacpTestVector(
                testPacket = byteArrayOf(0x06, 0x00, 0x01, 0x01),
                expectedParsingResult = true,
                expectedOpcode = Opcode.RESPONSE_CODE,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedResponseCode = ResponseCode.SUCCESS
        )
        /**
         * Packet2
         * Opcode not supported
         */
        racpTestVectors[2] = RacpTestVector(
                testPacket = byteArrayOf(0x06, 0x00, 0x01, 0x02),
                expectedParsingResult = true,
                expectedOpcode = Opcode.RESPONSE_CODE,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedResponseCode = ResponseCode.OP_CODE_NOT_SUPPORTED)

        /**
         * Packet3
         * Invalid Operator
         */
        racpTestVectors[3] = RacpTestVector(
                testPacket = byteArrayOf(0x06, 0x00, 0x01, 0x03),
                expectedParsingResult = true,
                expectedOpcode = Opcode.RESPONSE_CODE,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedResponseCode = ResponseCode.INVALID_OPERATOR
        )
        /**

         * Packet4
         * Operator not supported
         */
        racpTestVectors[4] = RacpTestVector(

                testPacket = byteArrayOf(0x06, 0x00, 0x01, 0x04),
                expectedParsingResult = true,
                expectedOpcode = Opcode.RESPONSE_CODE,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedResponseCode = ResponseCode.OPERATOR_NOT_SUPPORTED

        )
        /**

         * Packet5
         * General response successful
         */
        racpTestVectors[5] = RacpTestVector(

                testPacket = byteArrayOf(0x06, 0x00, 0x01, 0x05),
                expectedParsingResult = true,
                expectedOpcode = Opcode.RESPONSE_CODE,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedResponseCode = ResponseCode.INVALID_OPERAND

        )
        /**

         * Packet6
         * No record found
         */
        racpTestVectors[6] = RacpTestVector(

                testPacket = byteArrayOf(0x06, 0x00, 0x01, 0x06),
                expectedParsingResult = true,
                expectedOpcode = Opcode.RESPONSE_CODE,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedResponseCode = ResponseCode.NO_RECORDS_FOUND

        )
        /**

         * Packet7
         * Abort unsuccessful
         */
        racpTestVectors[7] = RacpTestVector(

                testPacket = byteArrayOf(0x06, 0x00, 0x01, 0x07),
                expectedParsingResult = true,
                expectedOpcode = Opcode.RESPONSE_CODE,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedResponseCode = ResponseCode.ABORT_UNSUCCESSFUL

        )
        /**

         * Packet8
         * Procedure not completed
         */
        racpTestVectors[8] = RacpTestVector(

                testPacket = byteArrayOf(0x06, 0x00, 0x01, 0x08),
                expectedParsingResult = true,
                expectedOpcode = Opcode.RESPONSE_CODE,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedResponseCode = ResponseCode.PROCEDURE_NOT_COMPLETED

        )
        /**

         * Packet9
         * Operand not supported
         */
        racpTestVectors[9] = RacpTestVector(

                testPacket = byteArrayOf(0x06, 0x00, 0x01, 0x09),
                expectedParsingResult = true,
                expectedOpcode = Opcode.RESPONSE_CODE,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedResponseCode = ResponseCode.OPERAND_NOT_SUPPORTED

        )
        /**

         * Packet10
         * reponse parser, opcode - delete stored record
         */
        racpTestVectors[10] = RacpTestVector(

                testPacket = byteArrayOf(0x06, 0x00, 0x02, 0x01),
                expectedParsingResult = true,
                expectedOpcode = Opcode.RESPONSE_CODE,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = Opcode.DELETE_STORED_RECORDS,
                expectedResponseCode = ResponseCode.SUCCESS

        )
        /**

         * Packet11
         * response parser, opcode - abort operation
         */
        racpTestVectors[11] = RacpTestVector(

                testPacket = byteArrayOf(0x06, 0x00, 0x03, 0x01),
                expectedParsingResult = true,
                expectedOpcode = Opcode.RESPONSE_CODE,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = Opcode.ABORT_OPERATION,
                expectedResponseCode = ResponseCode.SUCCESS

        )
        /**

         * Packet12
         * report number of records
         */
        racpTestVectors[12] = RacpTestVector(

                testPacket = byteArrayOf(0x05, 0x00, 0x03, 0x00),
                expectedParsingResult = true,
                expectedOpcode = Opcode.NUMBER_OF_STORED_RECORDS_RESPONSE,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = 3,
                expectedRequestOpcode = null,
                expectedResponseCode = null

        )
        /**

         * Packet13
         * Composing packet - Report all records
         */
        racpTestVectors[13] = RacpTestVector(

                testPacket = byteArrayOf(0x01, 0x01),
                expectedParsingResult = true,
                expectedOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedOperator = Operator.ALL_RECORDS,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = null,
                expectedResponseCode = null

        )
        /**

         * Packet14
         * Composing packet - report records - larger than or equal to
         */
        racpTestVectors[14] = RacpTestVector(

                testPacket = byteArrayOf(0x01, 0x03, 0x01, 0x05, 0x00),
                expectedParsingResult = true,
                expectedOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedOperator = Operator.GREATER_THAN_OR_EQUAL_TO,
                expectedFilter = Filter.SEQUENCE_NUMBER,
                expectedMinimumSequenceNumber = 5,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = null,
                expectedResponseCode = null

        )
        /**

         * Packet15
         * Composing packet - report number of records - less than or equal to
         *
         */
        racpTestVectors[15] = RacpTestVector(

                testPacket = byteArrayOf(0x01, 0x02, 0x01, 0xff.toByte(), 0x00),
                expectedParsingResult = true,
                expectedOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedOperator = Operator.LESS_THAN_OR_EQUAL_TO,
                expectedFilter = Filter.SEQUENCE_NUMBER,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = 255,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = null,
                expectedResponseCode = null

        )
        /**

         * Packet16
         * Composing packet - report records -  between A and B
         */
        racpTestVectors[16] = RacpTestVector(

                testPacket = byteArrayOf(0x01, 0x04, 0x01, 0x01, 0x00, 0xFE.toByte(), 0x00),
                expectedParsingResult = true,
                expectedOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedOperator = Operator.WITHIN_RANGE_OF_INCLUSIVE,
                expectedFilter = Filter.SEQUENCE_NUMBER,
                expectedMinimumSequenceNumber = 1,
                expectedMaximumSequenceNumber = 254,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = null,
                expectedResponseCode = null

        )
        /**

         * Packet17
         * Composing packet - report records -  first record
         */
        racpTestVectors[17] = RacpTestVector(

                testPacket = byteArrayOf(0x01, 0x05),
                expectedParsingResult = true,
                expectedOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedOperator = Operator.FIRST_RECORD,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = null,
                expectedResponseCode = null

        )
        /**

         * Packet18
         * Composing packet - report records -  last record
         */
        racpTestVectors[18] = RacpTestVector(

                testPacket = byteArrayOf(0x01, 0x06),
                expectedParsingResult = true,
                expectedOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedOperator = Operator.LAST_RECORD,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = null,
                expectedResponseCode = null

        )
        /**

         * Packet19
         * Composing packet - report records -  error case, unsupported operator
         */
        racpTestVectors[19] = RacpTestVector(

                testPacket = byteArrayOf(0x01, 0x00),
                expectedParsingResult = false,
                expectedOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = null,
                expectedResponseCode = null
        )

        /**
         * Packet20
         * Composing packet - report records -  error case, unsupported opcode
         */
        racpTestVectors[20] = RacpTestVector(

                testPacket = byteArrayOf(0x00, 0x00),
                expectedParsingResult = false,
                expectedOpcode = Opcode.RESERVED_FOR_FUTURE_USE,
                expectedOperator = null,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = null,
                expectedResponseCode = null
        )

        /**
         * packet21
         * general response successful with crc
         */
        racpTestVectors[21] = RacpTestVector(
                testPacket = byteArrayOf(0x06, 0x00, 0x01, 0x01, -22, 64),
                expectedParsingResult = true,
                expectedOpcode = Opcode.RESPONSE_CODE,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedResponseCode = ResponseCode.SUCCESS,
                hasCrc = true
        )

        /**
         * Packet22
         * Opcode not supported has CRC
         */
        racpTestVectors[2] = RacpTestVector(
                testPacket = byteArrayOf(0x06, 0x00, 0x01, 0x02, 113, 114),
                expectedParsingResult = true,
                expectedOpcode = Opcode.RESPONSE_CODE,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedResponseCode = ResponseCode.OP_CODE_NOT_SUPPORTED,
                hasCrc = true)

        /**
         * Packet23
         * Invalid Operator
         */
        racpTestVectors[23] = RacpTestVector(
                testPacket = byteArrayOf(0x06, 0x00, 0x01, 0x03, -8, 99),
                expectedParsingResult = true,
                expectedOpcode = Opcode.RESPONSE_CODE,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedResponseCode = ResponseCode.INVALID_OPERATOR,
                hasCrc = true
        )
        /**

         * Packet24
         * Operator not supported has CRC
         */
        racpTestVectors[24] = RacpTestVector(

                testPacket = byteArrayOf(0x06, 0x00, 0x01, 0x04, 71, 23),
                expectedParsingResult = true,
                expectedOpcode = Opcode.RESPONSE_CODE,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedResponseCode = ResponseCode.OPERATOR_NOT_SUPPORTED,
                hasCrc = true
        )

        /**

         * Packet25
         * General response successful has CRC
         */
        racpTestVectors[25] = RacpTestVector(

                testPacket = byteArrayOf(0x06, 0x00, 0x01, 0x05, -50, 6),
                expectedParsingResult = true,
                expectedOpcode = Opcode.RESPONSE_CODE,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedResponseCode = ResponseCode.INVALID_OPERAND,
                hasCrc = true
        )
        /**

         * Packet26
         * No record found has CRC
         */
        racpTestVectors[26] = RacpTestVector(

                testPacket = byteArrayOf(0x06, 0x00, 0x01, 0x06, 85, 52),
                expectedParsingResult = true,
                expectedOpcode = Opcode.RESPONSE_CODE,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedResponseCode = ResponseCode.NO_RECORDS_FOUND,
                hasCrc = true
        )
        /**

         * Packet27
         * Abort unsuccessful has CRC
         */
        racpTestVectors[27] = RacpTestVector(

                testPacket = byteArrayOf(0x06, 0x00, 0x01, 0x07, -36, 37),
                expectedParsingResult = true,
                expectedOpcode = Opcode.RESPONSE_CODE,
                expectedOperator = Operator.NULL,
                expectedFilter = null,
                expectedMinimumSequenceNumber = null,
                expectedMaximumSequenceNumber = null,
                expectedMinimumUserFacingTime = null,
                expectedMaximumUserFacingTime = null,
                expectedNumberOfRecord = null,
                expectedRequestOpcode = Opcode.REPORT_STORED_RECORDS,
                expectedResponseCode = ResponseCode.ABORT_UNSUCCESSFUL,
                hasCrc = true
        )
    }


    @Test
    fun testParsingSuccessIndicator() {
        System.out.printf("testParsingSuccessIndicator\n")
        for (testVector in racpTestVectors) {
            System.out.printf("testing racpTestVector ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedParsingResult, RACP(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc).successfulParsing)
        }
    }

    @Test
    fun testParsingOpcode() {
        System.out.printf("testParsingOpcode\n")
        for (testVector in racpTestVectors) {
            System.out.printf("testing racpTestVector ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedOpcode, RACP(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc).racpData.opcode)
        }
    }

    @Test
    fun testParsingOperator() {
        System.out.printf("testParsingOperator\n")
        for (testVector in racpTestVectors) {
            System.out.printf("testing racpTestVector ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedOperator, RACP(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc).racpData.operator)
        }
    }

    @Test
    fun testParsingFilter() {
        System.out.printf("testParsingFilter\n")
        for (testVector in racpTestVectors) {
            System.out.printf("testing racpTestVector ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedFilter, RACP(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc).racpData.filterType)
        }
    }

    @Test
    fun testParsingMinimumSequenceNumber() {
        System.out.printf("testMinimumSequenceNumber\n")
        for (testVector in racpTestVectors) {
            System.out.printf("testing racpTestVector ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedMinimumSequenceNumber, RACP(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc).racpData.minimumFilterValueSequenceNumber)
        }
    }

    @Test
    fun testParsingMaximumSequenceNumber() {
        System.out.printf("testMaximumSequenceNumber\n")
        for (testVector in racpTestVectors) {
            System.out.printf("testing racpTestVector ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedMaximumSequenceNumber, RACP(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc).racpData.maximumFilterValueSequenceNumber)
        }
    }

    @Test
    fun testParsingMinimumUserFacingTime() {
        System.out.printf("testMinimumUserFacingTime\n")
        for (testVector in racpTestVectors) {
            System.out.printf("testing racpTestVector ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedMinimumUserFacingTime, RACP(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc).racpData.minimumFilterValueUserFacingTime)
        }
    }

    @Test
    fun testParsingMaximumUserFacingTime() {
        System.out.printf("testMaximumUserFacingTime\n")
        for (testVector in racpTestVectors) {
            System.out.printf("testing racpTestVector ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedMaximumUserFacingTime, RACP(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc).racpData.maximumFilterValueUserFacingTime)
        }
    }

    @Test
    fun testParsingRequestedOpcode() {
        System.out.printf("testRequestedOpcode\n")
        for (testVector in racpTestVectors) {
            System.out.printf("testing racpTestVector ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedRequestOpcode, RACP(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc).racpData.requestOpcode)
        }
    }

    @Test
    fun testParsingRequestedResponse() {
        System.out.printf("testRequestedResponse\n")
        for (testVector in racpTestVectors) {
            System.out.printf("testing racpTestVector ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedResponseCode, RACP(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc).racpData.responseCode)
        }
    }

    @Test
    fun testParsingNumberOfRecordResponse() {
        System.out.printf("testNumberOfRecordResponse\n")
        for (testVector in racpTestVectors) {
            System.out.printf("testing racpTestVector ${testVector.key}\n")
            Assert.assertEquals(testVector.value.expectedNumberOfRecord, RACP(mockBTCharacteristic(testVector.value.testPacket), testVector.value.hasCrc).racpData.numberOfRecordResponse)
        }
    }

    @Test
    fun testComposingRacp() {
        System.out.printf("testComposingRacp\n")
        var testRacp: RACP
        var successCount = 0
        var skipCount = 0
        var racpData: RACPDataObject

        for (testVector in racpTestVectors) {
            System.out.printf("testing racpTestVector ${testVector.key}\n")
            if (testVector.value.expectedParsingResult == false) {
                System.out.printf("This case is expected to failed in parsing, so cant be composed backwards.\n")
                skipCount++
                continue
            }
            testRacp = RACP(mockBTCharacteristic(ByteArray(0)), testVector.value.hasCrc, isComposing = true)
            racpData = RACPDataObject()
            racpData.operator = testVector.value.expectedOperator
            racpData.opcode = testVector.value.expectedOpcode
            racpData.filterType = testVector.value.expectedFilter
            racpData.minimumFilterValueUserFacingTime = testVector.value.expectedMinimumUserFacingTime
            racpData.minimumFilterValueSequenceNumber = testVector.value.expectedMinimumSequenceNumber
            racpData.maximumFilterValueUserFacingTime = testVector.value.expectedMaximumUserFacingTime
            racpData.maximumFilterValueSequenceNumber = testVector.value.expectedMaximumSequenceNumber
            racpData.requestOpcode = testVector.value.expectedRequestOpcode
            racpData.responseCode = testVector.value.expectedResponseCode
            racpData.hasCrc = testVector.value.hasCrc
            racpData.numberOfRecordResponse = testVector.value.expectedNumberOfRecord
            testRacp.racpData = racpData
            val composedPacket = testRacp.composeCharacteristic()
            System.out.printf("composed: " + composedPacket.contentToString() + "\n")
            System.out.printf("test packet: " + testVector.value.testPacket.contentToString() + "\n")
            Assert.assertTrue(testVector.value.testPacket.contentEquals(composedPacket))
        }
        System.out.printf("Total cases: ${racpTestVectors.size} Cases skipped: $skipCount\n")
    }

    @Test
    fun testAppendingCrc() {
        System.out.printf("testAppendingCrC in RACP\n")
        var testRacp: RACP
        var skipCount = 0
        var racpData: RACPDataObject

        for (testVector in racpTestVectors) {
            System.out.printf("testing racpTestVector ${testVector.key}\n")
            if (testVector.value.expectedParsingResult == false) {
                System.out.printf("This case is expected to failed in parsing, so cant be composed backwards.\n")
                skipCount++
                continue
            }
            testRacp = RACP(mockBTCharacteristic(ByteArray(0)), testVector.value.hasCrc, isComposing = true)
            racpData = RACPDataObject()

            racpData.operator = testVector.value.expectedOperator
            racpData.opcode = testVector.value.expectedOpcode
            racpData.filterType = testVector.value.expectedFilter
            racpData.minimumFilterValueUserFacingTime = testVector.value.expectedMinimumUserFacingTime
            racpData.minimumFilterValueSequenceNumber = testVector.value.expectedMinimumSequenceNumber
            racpData.maximumFilterValueUserFacingTime = testVector.value.expectedMaximumUserFacingTime
            racpData.maximumFilterValueSequenceNumber = testVector.value.expectedMaximumSequenceNumber
            racpData.requestOpcode = testVector.value.expectedRequestOpcode
            racpData.responseCode = testVector.value.expectedResponseCode
            racpData.hasCrc = testVector.value.hasCrc
            racpData.numberOfRecordResponse = testVector.value.expectedNumberOfRecord
            testRacp.racpData = racpData
            val composedPacket = testRacp.composeCharacteristic()
            System.out.printf("composed: " + composedPacket.contentToString() + "\n")
            System.out.printf("test packet: " + testVector.value.testPacket.contentToString() + "\n")
            Assert.assertTrue(testVector.value.testPacket.contentEquals(composedPacket))
        }
        System.out.printf("Total cases: ${racpTestVectors.size} Cases skipped: $skipCount\n")
    }
}