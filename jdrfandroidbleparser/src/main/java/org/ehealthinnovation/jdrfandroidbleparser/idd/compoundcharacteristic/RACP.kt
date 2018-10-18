package org.ehealthinnovation.jdrfandroidbleparser.idd.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import android.util.Log
import org.ehealthinnovation.jdrfandroidbleparser.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.common.Composable
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.racp.Filter
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.racp.Opcode
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.racp.Operator
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.racp.ResponseCode
import java.util.*


/**
 * This control point is used with a service to provide basic management functionality for the
 * CGM Sensor patient record database. This enables functions including counting records,
 * transmitting records and clearing records based on filter criterion. The filter criterion in
 * the Operand field is defined by the service that references this characteristic as is the format
 * of a record (which may be comprised of one or more characteristics) and the sequence of
 * transferred records.
 */
class RACP : BaseCharacteristic, Composable {
    override val tag: String = RACP::class.java.canonicalName

    constructor(characteristic: BluetoothGattCharacteristic?, hasCrc: Boolean = false, hasE2eCounter: Boolean = false) : super(characteristic, GattCharacteristic.IDD_STATUS_READER_CONTROL_POINT.assigned, hasCrc = hasCrc, hasE2eCounter = hasE2eCounter) {
        this.hasCrc = hasCrc
        this.hasE2eCounter = hasE2eCounter
    }

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
     *  CGM Service, a Filter Type field is defined to enable the flexibility to filter based
     *  on different criteria (i.e., Time offset).
     */
    var filterType: Filter? = null

    /**
     * The following variables stores the possible fields which constitute operands.
     */
    var minimumFilterValueSequenceNumber: Int? = null
    var maximumFilterValueSequenceNumber: Int? = null

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
    var hasCrc: Boolean

    /**
     * E2E Counter
     */
    var hasE2eCounter: Boolean
    var e2eCounter: Int? = null

    /**
     * Main entry point for parsing a [BluetoothGattCharacteristic]
     */
    override fun parse(c: BluetoothGattCharacteristic, hasE2eCounter: Boolean): Boolean {
        var errorFreeParsing = false
        opcode = Opcode.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
        //Extract operator
        when (opcode) {
            null -> {
                Log.e(tag, "Opcode is null, packet might have been corrucpted")
                return errorFreeParsing
            }
            Opcode.REPORT_NUMBER_OF_STORED_RECORDS,
            Opcode.DELETE_STORED_RECORDS,
            Opcode.REPORT_STORED_RECORDS -> {
                operator = Operator.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
                parseGenericOperand(c)
            }
            Opcode.ABORT_OPERATION -> {
                operator = Operator.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
                if (operator != Operator.NULL) {
                    Log.e(tag, "operator must be null, when opcode is Abort Operation")
                    return errorFreeParsing
                }
            }
            Opcode.NUMBER_OF_STORED_RECORDS_RESPONSE -> {
                operator = Operator.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
                if (operator != Operator.NULL) {
                    Log.e(tag, "operator must be null, when opcode is Abort Operation")
                    return errorFreeParsing
                }
                numberOfRecordResponse = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
            }
            Opcode.RESPONSE_CODE -> {
                operator = Operator.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
                if (operator != Operator.NULL) {
                    Log.e(tag, "operator must be null, when opcode is Abort Operation")
                    return errorFreeParsing
                }
                requestOpcode = Opcode.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
                responseCode = ResponseCode.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
            }
            else -> {
                Log.e(tag, "unknown opcode $opcode , exiting parsing")
                return errorFreeParsing
            }
        }

        if (hasE2eCounter) {
            e2eCounter = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)
        }

        errorFreeParsing = true
        return errorFreeParsing

    }

    /**
     * Helper function to deserialize the generic operand portion of the packet
     */
    private fun parseGenericOperand(c: BluetoothGattCharacteristic) {
        when (operator) {
            null -> throw NullPointerException("operator is null")
            Operator.ALL_RECORDS,
            Operator.FIRST_RECORD,
            Operator.LAST_RECORD -> {
                Log.i(tag, "${operator.toString()} does not need operand")
                return
            }
        }

        filterType = Filter.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))

        if (EnumSet.of(Filter.SEQUENCE_NUMBER, Filter.SEQUENCE_NUMBER_FILTERED_BY_REFERENCE_TIME_EVENT, Filter.SEQUENCE_NUMBER_FILTERED_BY_NON_REFERENCE_TIME_EVENT).contains(filterType)) {
            when (operator) {
                Operator.LESS_THAN_OR_EQUAL_TO -> {
                    maximumFilterValueSequenceNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT32)
                }
                Operator.GREATER_THAN_OR_EQUAL_TO -> {
                    minimumFilterValueSequenceNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT32)
                }
                Operator.WITHIN_RANGE_OF_INCLUSIVE -> {
                    minimumFilterValueSequenceNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT32)
                    maximumFilterValueSequenceNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT32)
                }
                else -> {
                    Log.e(tag, "unknown operator $operator")
                }
            }
        } else {
            throw IllegalStateException("unable to handle filter type $filterType")
        }
    }

    /**
     * Serialize the current [RACP] into a [ByteArray], it can be loaded up onto a communication to
     * write to remote device
     * @param hasCrc if the characteristics need CRC
     * @return the [ByteArray] of the resulting composition
     */
    override fun composeCharacteristic(hasCrc: Boolean): ByteArray {
        opcode?.run {
            when (this) {
                Opcode.REPORT_STORED_RECORDS,
                Opcode.REPORT_NUMBER_OF_STORED_RECORDS,
                Opcode.DELETE_STORED_RECORDS -> {
                    putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                    composeQueryParameters()
                    return rawData
                }
                Opcode.RESPONSE_CODE -> {
                    putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                    putIntValue(Operator.NULL.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                    requestOpcode?.run { putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8) }
                            ?: throw NullPointerException(" request opcode is null")
                    responseCode?.run { putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8) }
                            ?: throw NullPointerException(" reponse code is null")
                }
                Opcode.ABORT_OPERATION -> {
                    putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                    putIntValue(Operator.NULL.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                }
                Opcode.NUMBER_OF_STORED_RECORDS_RESPONSE -> {
                    putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                    putIntValue(Operator.NULL.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                    numberOfRecordResponse?.run { putIntValue(this, BluetoothGattCharacteristic.FORMAT_UINT16) }
                            ?: throw NullPointerException(" number of records is null")
                }
                else -> {
                    Log.e(tag, "unknown opcode")
                }
            }

            if (hasE2eCounter) {
                e2eCounter?.let { putIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT8) }
            }

            if (hasCrc) {
                attachCrc()
            }
        }
        return rawData
    }

    /**
     * A helper function to compose the parameter portion of the packet
     */
    private fun composeQueryParameters() {
        operator?.run {
            when (this) {
                Operator.LAST_RECORD,
                Operator.FIRST_RECORD,
                Operator.ALL_RECORDS -> {
                    putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                }
                Operator.GREATER_THAN_OR_EQUAL_TO -> {
                    putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                    filterType?.run {
                        putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                        when (this) {
                            Filter.SEQUENCE_NUMBER_FILTERED_BY_NON_REFERENCE_TIME_EVENT,
                            Filter.SEQUENCE_NUMBER_FILTERED_BY_REFERENCE_TIME_EVENT,
                            Filter.SEQUENCE_NUMBER -> {
                                minimumFilterValueSequenceNumber?.run {
                                    putIntValue(this, BluetoothGattCharacteristic.FORMAT_UINT32)
                                } ?: throw NullPointerException("The minimum value is null")
                            }
                            else -> {
                                throw IllegalArgumentException("Unsupported filter type")
                            }
                        }
                    }
                }
                Operator.LESS_THAN_OR_EQUAL_TO -> {
                    putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                    filterType?.run {
                        putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                        when (this) {
                            Filter.SEQUENCE_NUMBER_FILTERED_BY_NON_REFERENCE_TIME_EVENT,
                            Filter.SEQUENCE_NUMBER_FILTERED_BY_REFERENCE_TIME_EVENT,
                            Filter.SEQUENCE_NUMBER -> {
                                maximumFilterValueSequenceNumber?.run {
                                    putIntValue(this, BluetoothGattCharacteristic.FORMAT_UINT32)
                                } ?: throw NullPointerException("The maximum value is null")
                            }
                            else -> {
                                throw IllegalArgumentException("Unsupported filter type")
                            }
                        }
                    }
                }
                Operator.WITHIN_RANGE_OF_INCLUSIVE -> {
                    putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                    filterType?.run {
                        putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                        when (this) {

                            Filter.SEQUENCE_NUMBER_FILTERED_BY_NON_REFERENCE_TIME_EVENT,
                            Filter.SEQUENCE_NUMBER_FILTERED_BY_REFERENCE_TIME_EVENT,
                            Filter.SEQUENCE_NUMBER -> {
                                minimumFilterValueSequenceNumber?.run {
                                    putIntValue(this, BluetoothGattCharacteristic.FORMAT_UINT32)
                                } ?: throw NullPointerException("The minimum value is null")
                                maximumFilterValueSequenceNumber?.run {
                                    putIntValue(this, BluetoothGattCharacteristic.FORMAT_UINT32)
                                } ?: throw NullPointerException("The maximum value is null")
                            }
                            else -> {
                                throw IllegalArgumentException("Unsupported filter type")
                            }
                        }
                    }
                }
                else -> {
                    throw IllegalArgumentException("Unknown operator")
                }

            }

        }


    }

}