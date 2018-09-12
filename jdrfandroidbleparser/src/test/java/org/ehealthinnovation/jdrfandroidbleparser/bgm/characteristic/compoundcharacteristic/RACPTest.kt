package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp.Opcode
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp.Operator
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp.ResponseCode
import org.junit.Assert.*

class RACPTest : BaseTest() {
    /**
     * Test Packet 1
     * General Response, successful2w
     */
    private val testPacket1 = byteArrayOf(0x06, 0x00, 0x01, 0x01)
    private val expectedParsingResult1 = true
    private val expectedOpcode1 = Opcode.RESPONSE_CODE
    private val expectedOperator1 = Operator.NULL
    private val expectedFilterType1 = null
    private val expectedMinimumSequenceNumber1 = null
    private val expectedMaximumSequenceNumber1 = null
    private val expectedMinimumUserFacingTime1 = null
    private val expectedMaximumUserFacingTime1 = null
    private val expectedNumberOfRecordResponse = null
    private val expectedRequestCode1 = Opcode.REPORT_STORED_RECORDS
    private val expectedRequestResult1 = ResponseCode.SUCCESS

    /**
     * Test Packet xx
     * General Response, successful2w
     */
    private val testPacketxx = byteArrayOf(0x06, 0x00, 0x01, 0x01)
    private val expectedParsingResultxx = true
    private val expectedOpcodexx = Opcode.RESPONSE_CODE
    private val expectedOperatorxx = Operator.NULL
    private val expectedFilterTypexx = null
    private val expectedMinimumSequenceNumberxx = null
    private val expectedMaximumSequenceNumberxx = null
    private val expectedMinimumUserFacingTimexx = null
    private val expectedMaximumUserFacingTimexx = null
    private val expectedNumberOfRecordResponse = null
    private val expectedRequestCodexx = Opcode.REPORT_STORED_RECORDS
    private val expectedRequestResultxx = ResponseCode.SUCCESS
}
