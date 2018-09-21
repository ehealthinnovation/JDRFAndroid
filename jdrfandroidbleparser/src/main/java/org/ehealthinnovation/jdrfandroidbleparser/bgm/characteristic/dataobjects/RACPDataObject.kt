package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.dataobjects

import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp.Filter
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp.Opcode
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp.Operator
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp.ResponseCode
import org.ehealthinnovation.jdrfandroidbleparser.utility.BluetoothDateTime

/**
 * A data object to hold the result of parsing from [RACP].
 * It also configures a command to be translated into [ByteArray] through [RACP]
 */
class RACPDataObject {
    /**
     * Op Code specifying the operation
     */
    var opcode: Opcode? = null


    /**
     * [operator] is a modifier to the operand. For example, [Operator.LAST_RECORD],
     * [Operator.LESS_THAN_OR_EQUAL_TO]
     */
    var operator: Operator? = null

    /** Since the value of the Operand is defined per service, when the RACP is used with the
     *  Glucose Service, a Filter Type field is defined to enable the flexibility to filter based
     *  on different criteria (i.e., Sequence Number or optionally User Facing Time).
     */
    var filterType: Filter? = null

    /**
     * The following variables stores the possible fields which constitute operands.
     */
    var minimumFilterValueSequenceNumber: Int? = null
    var maximumFilterValueSequenceNumber: Int? = null
    var minimumFilterValueUserFacingTime: BluetoothDateTime? = null
    var maximumFilterValueUserFacingTime: BluetoothDateTime? = null

    /**
     * When the Report Number of Stored Records Op Code is written to the Record Access Control
     * Point, the Server shall calculate and respond with a record count in UINT16 format based on
     * filter criteria, Operator and Operand values.
     */
    var numberOfRecordResponse: Int? = null
    var requestOpcode: Opcode? = null
    var responseCode: ResponseCode? = null


    /**
     * Indicate whether the packet requires CRC
     */
    var hasCrc: Boolean = false

}

