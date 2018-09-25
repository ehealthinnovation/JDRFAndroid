package org.ehealthinnovation.jdrfandroidbleparser.cgm.characteristic

import android.bluetooth.BluetoothGattCharacteristic
import android.util.Log
import org.ehealthinnovation.jdrfandroidbleparser.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.common.Composable
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.racp.Filter
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.racp.Opcode
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.racp.Operator
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.racp.ResponseCode


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

    constructor(c: BluetoothGattCharacteristic, hasCrc: Boolean = false, isComposing: Boolean = false) :
            super(c, GattCharacteristic.RECORD_ACCESS_CONTROL_POINT.assigned, hasCrc, isComposing) {
        this.hasCrc = hasCrc
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
    var minimumFilterValueTimeOffset: Int? = null
    var maximumFilterValueTimeOffset: Int? = null

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
     * Main entry point for parsing a [BluetoothGattCharacteristic]
     */
    override fun parse(c: BluetoothGattCharacteristic): Boolean {
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

        if (filterType == Filter.TIME_OFFSET) {
            when (operator) {
                Operator.LESS_THAN_OR_EQUAL_TO -> {
                    maximumFilterValueTimeOffset = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                }
                Operator.GREATER_THAN_OR_EQUAL_TO -> {
                    minimumFilterValueTimeOffset = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                }
                Operator.WITHIN_RANGE_OF_INCLUSIVE -> {
                    minimumFilterValueTimeOffset = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                    maximumFilterValueTimeOffset = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
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
            if(hasCrc){
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
                            Filter.TIME_OFFSET -> {
                                minimumFilterValueTimeOffset?.run {
                                    putIntValue(this, BluetoothGattCharacteristic.FORMAT_UINT16)
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
                            Filter.TIME_OFFSET -> {
                                maximumFilterValueTimeOffset?.run {
                                    putIntValue(this, BluetoothGattCharacteristic.FORMAT_UINT16)
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
                            Filter.TIME_OFFSET -> {
                                minimumFilterValueTimeOffset?.run {
                                    putIntValue(this, BluetoothGattCharacteristic.FORMAT_UINT16)
                                } ?: throw NullPointerException("The minimum value is null")
                                maximumFilterValueTimeOffset?.run {
                                    putIntValue(this, BluetoothGattCharacteristic.FORMAT_UINT16)
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