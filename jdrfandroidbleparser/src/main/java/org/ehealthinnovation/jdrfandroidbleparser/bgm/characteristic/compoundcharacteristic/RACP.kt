package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import android.util.Log
import org.ehealthinnovation.jdrfandroidbleparser.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.common.Composable
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp.Filter
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp.Opcode
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp.Operator
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp.ResponseCode
import org.ehealthinnovation.jdrfandroidbleparser.utility.BluetoothDateTime


/**
 * This control point is used with a service to provide basic management functionality for the
 * Glucose Sensor patient record database. This enables functions including counting records,
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
    var hasCrc: Boolean

    /**
     * Main entry point for parsing a [BluetoothGattCharacteristic]
     */
    override fun parse(c: BluetoothGattCharacteristic, hasE2eCounter : Boolean): Boolean {
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

        if (filterType == Filter.SEQUENCE_NUMBER) {
            when (operator) {
                Operator.LESS_THAN_OR_EQUAL_TO -> {
                    maximumFilterValueSequenceNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                }
                Operator.GREATER_THAN_OR_EQUAL_TO -> {
                    minimumFilterValueSequenceNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                }
                Operator.WITHIN_RANGE_OF_INCLUSIVE -> {
                    minimumFilterValueSequenceNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                    maximumFilterValueSequenceNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                }
                else -> {
                    Log.e(tag, "unknown operator $operator")
                }
            }
        } else if (filterType == Filter.USER_FACING_TIME) {
            when (operator) {
                Operator.LESS_THAN_OR_EQUAL_TO -> {
                    maximumFilterValueUserFacingTime = parseBluetoothDateTime(c)
                }
                Operator.GREATER_THAN_OR_EQUAL_TO -> {
                    minimumFilterValueUserFacingTime = parseBluetoothDateTime(c)
                }
                Operator.WITHIN_RANGE_OF_INCLUSIVE -> {
                    minimumFilterValueUserFacingTime = parseBluetoothDateTime(c)
                    maximumFilterValueUserFacingTime = parseBluetoothDateTime(c)

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
     * A helper function for deserializing [BluetoothDateTime]
     */
    private fun parseBluetoothDateTime(c: BluetoothGattCharacteristic): BluetoothDateTime {
        return BluetoothDateTime(
                _year = getNextIntValue(c, getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)),
                _month = getNextIntValue(c, getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)),
                _day = getNextIntValue(c, getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)),
                _hour = getNextIntValue(c, getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)),
                _min = getNextIntValue(c, getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)),
                _sec = getNextIntValue(c, getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
        )
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
                            ?: throw NullPointerException(" nnumber of records is null")
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
                            Filter.SEQUENCE_NUMBER -> {
                                minimumFilterValueSequenceNumber?.run {
                                    putIntValue(this, BluetoothGattCharacteristic.FORMAT_UINT16)
                                } ?: throw NullPointerException("The minimum value is null")
                            }
                            Filter.USER_FACING_TIME -> {
                                minimumFilterValueUserFacingTime?.run {
                                    putBluetoothDateTime(this)
                                } ?: throw  NullPointerException("The minimum value is null")
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
                            Filter.SEQUENCE_NUMBER -> {
                                maximumFilterValueSequenceNumber?.run {
                                    putIntValue(this, BluetoothGattCharacteristic.FORMAT_UINT16)
                                } ?: throw NullPointerException("The maximum value is null")
                            }
                            Filter.USER_FACING_TIME -> {
                                maximumFilterValueUserFacingTime?.run {
                                    putBluetoothDateTime(this)
                                } ?: throw  NullPointerException("The maximum value is null")
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
                            Filter.SEQUENCE_NUMBER -> {
                                minimumFilterValueSequenceNumber?.run {
                                    putIntValue(this, BluetoothGattCharacteristic.FORMAT_UINT16)
                                } ?: throw NullPointerException("The minimum value is null")
                                maximumFilterValueSequenceNumber?.run {
                                    putIntValue(this, BluetoothGattCharacteristic.FORMAT_UINT16)
                                } ?: throw NullPointerException("The maximum value is null")
                            }
                            Filter.USER_FACING_TIME -> {
                                minimumFilterValueUserFacingTime?.run {
                                    putBluetoothDateTime(this)
                                } ?: throw NullPointerException("The minimum value is null")
                                maximumFilterValueUserFacingTime?.run {
                                    putBluetoothDateTime(this)
                                } ?: throw  NullPointerException("The maximum value is null")
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

    /**
     * A helper function to serialize [BluetoothDateTime]
     */
    private fun putBluetoothDateTime(input: BluetoothDateTime) {
        putIntValue(input._year, BluetoothGattCharacteristic.FORMAT_UINT16)
        putIntValue(input._month, BluetoothGattCharacteristic.FORMAT_UINT8)
        putIntValue(input._day, BluetoothGattCharacteristic.FORMAT_UINT8)
        putIntValue(input._hour, BluetoothGattCharacteristic.FORMAT_UINT8)
        putIntValue(input._min, BluetoothGattCharacteristic.FORMAT_UINT8)
        putIntValue(input._sec, BluetoothGattCharacteristic.FORMAT_UINT8)
    }

}