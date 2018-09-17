package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp.Filter
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp.Opcode
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp.Operator
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp.ResponseCode
import org.ehealthinnovation.jdrfandroidbleparser.utility.BluetoothDateTime
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class RACPTest : BaseTest(){
    /**
     * Data class for hosting test vector
      */
    class racpTestVector(
            val testPacket: ByteArray,
            val expectedParsingResult: Boolean,
            val expectedOpcode: Opcode,
            val expectedOperator: Operator,
            val expectedFilter: Filter? = null,
            val expectedMinimumSequenceNumber: Int? = null,
            val expectedMaximumSequenceNumber: Int? = null,
            val expectedMinimumUserFacingTime: BluetoothDateTime? = null,
            val expectedMaximumUserFacingTime: BluetoothDateTime? = null,
            val expectedRequestOpcode: Opcode? = null,
            val expectedResponseCode: ResponseCode? = null
    )

    val racpTestVectors: Map<Int, racpTestVector> = HashMap()
    /**
     * Packet1
     * General response successful
     */
    init {
        racpTestVectors.

    }
    private val testPacket1 : ByteArray = byteArrayOf(0x06, 0x00, 0x01, 0x01)
    private val expectedParsedResult1 : Boolean = true
    private val expectedOpcode1 : Opcode = Opcode.RESPONSE_CODE
    private val expectedOperator1 : Operator = Operator.NULL
    private val expectedFilterType1  = null
    private val expectedMinimumSequenceNumber1 = null
    private val expectedMaximumSequenceNumber1 = null
    private val expectedMinimumUserFacingTime1 = null
    private val expectedMaximumUserFacingTime1 = null
    private val expectedNumberOfRecordResponse1 = null
    private val expectedRequestOpcode1 : Opcode = Opcode.REPORT_STORED_RECORDS
    private val expectedResponseCode1 : ResponseCode = ResponseCode.SUCCESS

    /**
     * Packet2
     * Opcode not supported
     */
    private val testPacket2 : ByteArray = byteArrayOf(0x06, 0x00, 0x01, 0x02)
    private val expectedParsedResult2 : Boolean = true
    private val expectedOpcode2 : Opcode = Opcode.RESPONSE_CODE
    private val expectedOperator2 : Operator = Operator.NULL
    private val expectedFilterType2 = null
    private val expectedMinimumSequenceNumber2 = null
    private val expectedMaximumSequenceNumber2 = null
    private val expectedMinimumUserFacingTime2 = null
    private val expectedMaximumUserFacingTime2 = null
    private val expectedNumberOfRecordResponse2 = null
    private val expectedRequestOpcode2 : Opcode = Opcode.REPORT_STORED_RECORDS
    private val expectedResponseCode2 : ResponseCode = ResponseCode.OP_CODE_NOT_SUPPORTED

    /**
     * Packet3
     * Invalid Operator
     */
    private val testPacket3 : ByteArray = byteArrayOf(0x06, 0x00, 0x01, 0x03)
    private val expectedParsedResult3 : Boolean = true
    private val expectedOpcode3 : Opcode = Opcode.RESPONSE_CODE
    private val expectedOperator3 : Operator = Operator.NULL
    private val expectedFilterType3 = null
    private val expectedMinimumSequenceNumber3 = null
    private val expectedMaximumSequenceNumber3 = null
    private val expectedMinimumUserFacingTime3 = null
    private val expectedMaximumUserFacingTime3 = null
    private val expectedNumberOfRecordResponse3 = null
    private val expectedRequestOpcode3 : Opcode = Opcode.REPORT_STORED_RECORDS
    private val expectedResponseCode3 : ResponseCode = ResponseCode.INVALID_OPERATOR

    /**
     * Packet4
     * Operator not supported
     */
    private val testPacket4 : ByteArray = byteArrayOf(0x06, 0x00, 0x01, 0x04)
    private val expectedParsedResult4 : Boolean = true
    private val expectedOpcode4 : Opcode = Opcode.RESPONSE_CODE
    private val expectedOperator4 : Operator = Operator.NULL
    private val expectedFilterType4  = null
    private val expectedMinimumSequenceNumber4 = null
    private val expectedMaximumSequenceNumber4 = null
    private val expectedMinimumUserFacingTime4 = null
    private val expectedMaximumUserFacingTime4 = null
    private val expectedNumberOfRecordResponse4 = null
    private val expectedRequestOpcode4 : Opcode = Opcode.REPORT_STORED_RECORDS
    private val expectedResponseCode4 : ResponseCode = ResponseCode.OPERATOR_NOT_SUPPORTED

    /**
     * Packet5
     * General response successful
     */
    private val testPacket5 : ByteArray = byteArrayOf(0x06, 0x00, 0x01, 0x05)
    private val expectedParsedResult5 : Boolean = true
    private val expectedOpcode5 : Opcode = Opcode.RESPONSE_CODE
    private val expectedOperator5 : Operator = Operator.NULL
    private val expectedFilterType5  = null
    private val expectedMinimumSequenceNumber5 = null
    private val expectedMaximumSequenceNumber5 = null
    private val expectedMinimumUserFacingTime5 = null
    private val expectedMaximumUserFacingTime5 = null
    private val expectedNumberOfRecordResponse5 = null
    private val expectedRequestOpcode5 : Opcode = Opcode.REPORT_STORED_RECORDS
    private val expectedResponseCode5 : ResponseCode = ResponseCode.INVALID_OPERAND

    /**
     * Packet6
     * No record found
     */
    private val testPacket6 : ByteArray = byteArrayOf(0x06, 0x00, 0x01, 0x06)
    private val expectedParsedResult6 : Boolean = true
    private val expectedOpcode6 : Opcode = Opcode.RESPONSE_CODE
    private val expectedOperator6 : Operator = Operator.NULL
    private val expectedFilterType6 = null
    private val expectedMinimumSequenceNumber6 = null
    private val expectedMaximumSequenceNumber6 = null
    private val expectedMinimumUserFacingTime6 = null
    private val expectedMaximumUserFacingTime6 = null
    private val expectedNumberOfRecordResponse6 = null
    private val expectedRequestOpcode6 : Opcode = Opcode.REPORT_STORED_RECORDS
    private val expectedResponseCode6 : ResponseCode = ResponseCode.NO_RECORDS_FOUND

    /**
     * Packet7
     * Abort unsuccessful
     */
    private val testPacket7 : ByteArray = byteArrayOf(0x06, 0x00, 0x01, 0x07)
    private val expectedParsedResult7 : Boolean = true
    private val expectedOpcode7 : Opcode = Opcode.RESPONSE_CODE
    private val expectedOperator7 : Operator = Operator.NULL
    private val expectedFilterType7 = null
    private val expectedMinimumSequenceNumber7 = null
    private val expectedMaximumSequenceNumber7 = null
    private val expectedMinimumUserFacingTime7 = null
    private val expectedMaximumUserFacingTime7 = null
    private val expectedNumberOfRecordResponse7 = null
    private val expectedRequestOpcode7 : Opcode = Opcode.REPORT_STORED_RECORDS
    private val expectedResponseCode7 : ResponseCode = ResponseCode.ABORT_UNSUCCESSFUL

    /**
     * Packet8
     * Procedure not completed
     */
    private val testPacket8 : ByteArray = byteArrayOf(0x06, 0x00, 0x01, 0x08)
    private val expectedParsedResult8 : Boolean = true
    private val expectedOpcode8 : Opcode = Opcode.RESPONSE_CODE
    private val expectedOperator8 : Operator = Operator.NULL
    private val expectedFilterType8 = null
    private val expectedMinimumSequenceNumber8 = null
    private val expectedMaximumSequenceNumber8 = null
    private val expectedMinimumUserFacingTime8 = null
    private val expectedMaximumUserFacingTime8 = null
    private val expectedNumberOfRecordResponse8 = null
    private val expectedRequestOpcode8 : Opcode = Opcode.REPORT_STORED_RECORDS
    private val expectedResponseCode8 : ResponseCode = ResponseCode.PROCEDURE_NOT_COMPLETED

    /**
     * Packet9
     * Operand not supported
     */
    private val testPacket9 : ByteArray = byteArrayOf(0x06, 0x00, 0x01, 0x09)
    private val expectedParsedResult9 : Boolean = true
    private val expectedOpcode9 : Opcode = Opcode.RESPONSE_CODE
    private val expectedOperator9 : Operator = Operator.NULL
    private val expectedFilterType9  = null
    private val expectedMinimumSequenceNumber9 = null
    private val expectedMaximumSequenceNumber9 = null
    private val expectedMinimumUserFacingTime9 = null
    private val expectedMaximumUserFacingTime9 = null
    private val expectedNumberOfRecordResponse9 = null
    private val expectedRequestOpcode9 : Opcode = Opcode.REPORT_STORED_RECORDS
    private val expectedResponseCode9 : ResponseCode = ResponseCode.OPERAND_NOT_SUPPORTED

    /**
     * Packet10
     * reponse parser, opcode - delete stored record
     */
    private val testPacket10 : ByteArray = byteArrayOf(0x06, 0x00, 0x02, 0x01)
    private val expectedParsedResult10 : Boolean = true
    private val expectedOpcode10 : Opcode = Opcode.RESPONSE_CODE
    private val expectedOperator10 : Operator = Operator.NULL
    private val expectedFilterType10  = null
    private val expectedMinimumSequenceNumber10 = null
    private val expectedMaximumSequenceNumber10 = null
    private val expectedMinimumUserFacingTime10 = null
    private val expectedMaximumUserFacingTime10 = null
    private val expectedNumberOfRecordResponse10 = null
    private val expectedRequestOpcode10 : Opcode = Opcode.DELETE_STORED_RECORDS
    private val expectedResponseCode10 : ResponseCode = ResponseCode.SUCCESS

    /**
     * Packet11
     * response parser, opcode - abort operation
     */
    private val testPacket11 : ByteArray = byteArrayOf(0x06, 0x00, 0x03, 0x01)
    private val expectedParsedResult11 : Boolean = true
    private val expectedOpcode11 : Opcode = Opcode.RESPONSE_CODE
    private val expectedOperator11 : Operator = Operator.NULL
    private val expectedFilterType11  = null
    private val expectedMinimumSequenceNumber11 = null
    private val expectedMaximumSequenceNumber11 = null
    private val expectedMinimumUserFacingTime11 = null
    private val expectedMaximumUserFacingTime11 = null
    private val expectedNumberOfRecordResponse11 = null
    private val expectedRequestOpcode11 : Opcode = Opcode.ABORT_OPERATION
    private val expectedResponseCode11 : ResponseCode = ResponseCode.SUCCESS

    /**
     * Packet12
     * report number of records
     */
    private val testPacket12 : ByteArray = byteArrayOf(0x05, 0x00, 0x03, 0x00)
    private val expectedParsedResult12 : Boolean = true
    private val expectedOpcode12 : Opcode = Opcode.NUMBER_OF_STORED_RECORDS_RESPONSE
    private val expectedOperator12 : Operator = Operator.NULL
    private val expectedFilterType12 = null
    private val expectedMinimumSequenceNumber12 = null
    private val expectedMaximumSequenceNumber12 = null
    private val expectedMinimumUserFacingTime12 = null
    private val expectedMaximumUserFacingTime12 = null
    private val expectedNumberOfRecordResponse12 = 3
    private val expectedRequestOpcode12 = null
    private val expectedResponseCode12 = null

    /**
     * Packet13
     * Composing packet - Report all records
     */
    private val testPacket13 : ByteArray = byteArrayOf(0x01, 0x01)
    private val expectedParsedResult13 : Boolean = true
    private val expectedOpcode13 : Opcode = Opcode.REPORT_STORED_RECORDS
    private val expectedOperator13 : Operator = Operator.ALL_RECORDS
    private val expectedFilterType13 = null
    private val expectedMinimumSequenceNumber13 = null
    private val expectedMaximumSequenceNumber13 = null
    private val expectedMinimumUserFacingTime13 = null
    private val expectedMaximumUserFacingTime13 = null
    private val expectedNumberOfRecordResponse13 = null
    private val expectedRequestOpcode13 = null
    private val expectedResponseCode13 = null

    /**
     * Packet14
     * Composing packet - report records - larger than or equal to
     */
    private val testPacket14 : ByteArray = byteArrayOf(0x01, 0x03, 0x01, 0x05, 0x00)
    private val expectedParsedResult14 : Boolean = true
    private val expectedOpcode14 : Opcode = Opcode.REPORT_STORED_RECORDS
    private val expectedOperator14 : Operator = Operator.GREATER_THAN_OR_EQUAL_TO
    private val expectedFilterType14 : Filter = Filter.SEQUENCE_NUMBER
    private val expectedMinimumSequenceNumber14 = 5
    private val expectedMaximumSequenceNumber14 = null
    private val expectedMinimumUserFacingTime14 = null
    private val expectedMaximumUserFacingTime14 = null
    private val expectedNumberOfRecordResponse14 = null
    private val expectedRequestOpcode14 = null
    private val expectedResponseCode14 = null

    /**
     * Packet15
     * Composing packet - report number of records - less than or equal to
     *
     */
    private val testPacket15 : ByteArray = byteArrayOf(0x01, 0x02, 0x01, 0xff.toByte(), 0x00)
    private val expectedParsedResult15 : Boolean = true
    private val expectedOpcode15 : Opcode = Opcode.REPORT_STORED_RECORDS
    private val expectedOperator15 : Operator = Operator.LESS_THAN_OR_EQUAL_TO
    private val expectedFilterType15 : Filter = Filter.SEQUENCE_NUMBER
    private val expectedMinimumSequenceNumber15 = null
    private val expectedMaximumSequenceNumber15 = 255
    private val expectedMinimumUserFacingTime15 = null
    private val expectedMaximumUserFacingTime15 = null
    private val expectedNumberOfRecordResponse15 = null
    private val expectedRequestOpcode15 = null
    private val expectedResponseCode15 = null

    /**
     * Packet16
     * Composing packet - report records -  between A and B
     */
    private val testPacket16 : ByteArray = byteArrayOf(0x01, 0x04, 0x01, 0x01, 0x00, 0xFE.toByte(), 0x00)
    private val expectedParsedResult16 : Boolean = true
    private val expectedOpcode16 : Opcode = Opcode.REPORT_STORED_RECORDS
    private val expectedOperator16 : Operator = Operator.WITHIN_RANGE_OF_INCLUSIVE
    private val expectedFilterType16 : Filter = Filter.SEQUENCE_NUMBER
    private val expectedMinimumSequenceNumber16 = 1
    private val expectedMaximumSequenceNumber16 = 254
    private val expectedMinimumUserFacingTime16 = null
    private val expectedMaximumUserFacingTime16 = null
    private val expectedNumberOfRecordResponse16 = null
    private val expectedRequestOpcode16 = null
    private val expectedResponseCode16 = null

    /**
     * Packet17
     * Composing packet - report records -  first record
     */
    private val testPacket17 : ByteArray = byteArrayOf(0x01, 0x05)
    private val expectedParsedResult17 : Boolean = true
    private val expectedOpcode17 : Opcode = Opcode.REPORT_STORED_RECORDS
    private val expectedOperator17 : Operator = Operator.FIRST_RECORD
    private val expectedFilterType17 = null
    private val expectedMinimumSequenceNumber17 = null
    private val expectedMaximumSequenceNumber17 = null
    private val expectedMinimumUserFacingTime17 = null
    private val expectedMaximumUserFacingTime17 = null
    private val expectedNumberOfRecordResponse17 = null
    private val expectedRequestOpcode17 = null
    private val expectedResponseCode17 = null

    /**
     * Packet18
     * Composing packet - report records -  last record
     */
    private val testPacket18 : ByteArray = byteArrayOf(0x01, 0x06)
    private val expectedParsedResult18 : Boolean = true
    private val expectedOpcode18 : Opcode = Opcode.REPORT_STORED_RECORDS
    private val expectedOperator18 : Operator = Operator.FIRST_RECORD
    private val expectedFilterType18 = null
    private val expectedMinimumSequenceNumber18 = null
    private val expectedMaximumSequenceNumber18 = null
    private val expectedMinimumUserFacingTime18 = null
    private val expectedMaximumUserFacingTime18 = null
    private val expectedNumberOfRecordResponse18 = null
    private val expectedRequestOpcode18 = null
    private val expectedResponseCode18 = null

    /**
     * Packet19
     * Composing packet - report records -  error case, unsupported operator
     */
    private val testPacket19 = byteArrayOf(0x01,0x00)
    private val expectedParsedResult19 : Boolean = false
    private val expectedOpcode19 : Opcode = Opcode.REPORT_STORED_RECORDS
    private val expectedOperator19 : Operator = Operator.NULL
    private val expectedFilterType19 = null
    private val expectedMinimumSequenceNumber19 = null
    private val expectedMaximumSequenceNumber19 = null
    private val expectedMinimumUserFacingTime19 = null
    private val expectedMaximumUserFacingTime19 = null
    private val expectedNumberOfRecordResponse19 = null
    private val expectedRequestOpcode19 = null
    private val expectedResponseCode19 = null

     /**
     * Packet20
     * Composing packet - report records -  error case, unsupported opcode
     */
    private val testPacket20 = byteArrayOf(0x00,0x00)
    private val expectedParsedResult20 : Boolean = false
    private val expectedOpcode20 : Opcode = Opcode.RESERVED_FOR_FUTURE_USE
    private val expectedOperator20 : Operator = Operator.NULL
    private val expectedFilterType20 = null
    private val expectedMinimumSequenceNumber20 = null
    private val expectedMaximumSequenceNumber20 = null
    private val expectedMinimumUserFacingTime20 = null
    private val expectedMaximumUserFacingTime20 = null
    private val expectedNumberOfRecordResponse20 = null
    private val expectedRequestOpcode20 = null
    private val expectedResponseCode20 = null


    @Test
    fun testParsingSuccessIndicator(){
        Assert.assertEquals(expectedParsedResult1, RACP(mockBTCharacteristic(testPacket1), false).successfulParsing)
        Assert.assertEquals(expectedParsedResult2, RACP(mockBTCharacteristic(testPacket2), false).successfulParsing)
        Assert.assertEquals(expectedParsedResult3, RACP(mockBTCharacteristic(testPacket3), false).successfulParsing)
        Assert.assertEquals(expectedParsedResult4, RACP(mockBTCharacteristic(testPacket4), false).successfulParsing)
        Assert.assertEquals(expectedParsedResult5, RACP(mockBTCharacteristic(testPacket5), false).successfulParsing)
        Assert.assertEquals(expectedParsedResult6, RACP(mockBTCharacteristic(testPacket6), false).successfulParsing)
        Assert.assertEquals(expectedParsedResult7, RACP(mockBTCharacteristic(testPacket7), false).successfulParsing)
        Assert.assertEquals(expectedParsedResult8, RACP(mockBTCharacteristic(testPacket8), false).successfulParsing)
        Assert.assertEquals(expectedParsedResult9, RACP(mockBTCharacteristic(testPacket9), false).successfulParsing)
        Assert.assertEquals(expectedParsedResult10, RACP(mockBTCharacteristic(testPacket10), false).successfulParsing)
        Assert.assertEquals(expectedParsedResult11, RACP(mockBTCharacteristic(testPacket11), false).successfulParsing)
        Assert.assertEquals(expectedParsedResult12, RACP(mockBTCharacteristic(testPacket12), false).successfulParsing)
        Assert.assertEquals(expectedParsedResult13, RACP(mockBTCharacteristic(testPacket13), false).successfulParsing)
        Assert.assertEquals(expectedParsedResult14, RACP(mockBTCharacteristic(testPacket14), false).successfulParsing)
        Assert.assertEquals(expectedParsedResult15, RACP(mockBTCharacteristic(testPacket15), false).successfulParsing)
        Assert.assertEquals(expectedParsedResult16, RACP(mockBTCharacteristic(testPacket16), false).successfulParsing)
        Assert.assertEquals(expectedParsedResult17, RACP(mockBTCharacteristic(testPacket17), false).successfulParsing)
        Assert.assertEquals(expectedParsedResult18, RACP(mockBTCharacteristic(testPacket18), false).successfulParsing)
        Assert.assertEquals(expectedParsedResult19, RACP(mockBTCharacteristic(testPacket19), false).successfulParsing)
        Assert.assertEquals(expectedParsedResult20, RACP(mockBTCharacteristic(testPacket20), false).successfulParsing)

    }

    @Test
    fun testParsingOpcode(){

    }
}