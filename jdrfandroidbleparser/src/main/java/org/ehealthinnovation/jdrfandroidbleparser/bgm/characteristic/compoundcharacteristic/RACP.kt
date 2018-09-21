package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import android.util.Log
import org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.dataobjects.RACPDataObject
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
            super(c, GattCharacteristic.RECORD_ACCESS_CONTROL_POINT.assigned, hasCrc, isComposing)


    lateinit var racpData: RACPDataObject

    /**
     * Main entry point for parsing a [BluetoothGattCharacteristic]
     */
    override fun parse(c: BluetoothGattCharacteristic): Boolean {
        var errorFreeParsing = false
        racpData = RACPDataObject()
        racpData.opcode = Opcode.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
        //Extract operator
        when (racpData.opcode) {
            null -> {
                Log.e(tag, "Opcode is null, packet might have been corrucpted")
                return errorFreeParsing
            }
            Opcode.REPORT_NUMBER_OF_STORED_RECORDS,
            Opcode.DELETE_STORED_RECORDS,
            Opcode.REPORT_STORED_RECORDS -> {
                racpData.operator = Operator.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
                parseGenericOperand(c)
            }
            Opcode.ABORT_OPERATION -> {
                racpData.operator = Operator.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
                if (racpData.operator != Operator.NULL) {
                    Log.e(tag, "operator must be null, when opcode is Abort Operation")
                    return errorFreeParsing
                }
            }
            Opcode.NUMBER_OF_STORED_RECORDS_RESPONSE -> {
                racpData.operator = Operator.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
                if (racpData.operator != Operator.NULL) {
                    Log.e(tag, "operator must be null, when opcode is Abort Operation")
                    return errorFreeParsing
                }
                racpData.numberOfRecordResponse = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
            }
            Opcode.RESPONSE_CODE -> {
                racpData.operator = Operator.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
                if (racpData.operator != Operator.NULL) {
                    Log.e(tag, "operator must be null, when opcode is Abort Operation")
                    return errorFreeParsing
                }
                racpData.requestOpcode = Opcode.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
                racpData.responseCode = ResponseCode.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
            }
            else -> {
                Log.e(tag, "unknown opcode $racpData.opcode , exiting parsing")
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
        when (racpData.operator) {
            null -> throw NullPointerException("operator is null")
            Operator.ALL_RECORDS,
            Operator.FIRST_RECORD,
            Operator.LAST_RECORD -> {
                Log.i(tag, "${racpData.operator.toString()} does not need operand")
                return
            }
        }

        racpData.filterType = Filter.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))

        if (racpData.filterType == Filter.SEQUENCE_NUMBER) {
            when (racpData.operator) {
                Operator.LESS_THAN_OR_EQUAL_TO -> {
                    racpData.maximumFilterValueSequenceNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                }
                Operator.GREATER_THAN_OR_EQUAL_TO -> {
                    racpData.minimumFilterValueSequenceNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                }
                Operator.WITHIN_RANGE_OF_INCLUSIVE -> {
                    racpData.minimumFilterValueSequenceNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                    racpData.maximumFilterValueSequenceNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                }
                else -> {
                    Log.e(tag, "unknown operator $racpData.operator")
                }
            }
        } else if (racpData.filterType == Filter.USER_FACING_TIME) {
            when (racpData.operator) {
                Operator.LESS_THAN_OR_EQUAL_TO -> {
                    racpData.maximumFilterValueUserFacingTime = parseBluetoothDateTime(c)
                }
                Operator.GREATER_THAN_OR_EQUAL_TO -> {
                    racpData.minimumFilterValueUserFacingTime = parseBluetoothDateTime(c)
                }
                Operator.WITHIN_RANGE_OF_INCLUSIVE -> {
                    racpData.minimumFilterValueUserFacingTime = parseBluetoothDateTime(c)
                    racpData.maximumFilterValueUserFacingTime = parseBluetoothDateTime(c)

                }
                else -> {
                    Log.e(tag, "unknown operator $racpData.operator")
                }
            }
        } else {
            throw IllegalStateException("unable to handle filter type $racpData.filterType")
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
    override fun composeCharacteristic(): ByteArray {
        racpData.opcode?.run {
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
                    racpData.requestOpcode?.run { putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8) }
                            ?: throw NullPointerException(" request opcode is null")
                    racpData.responseCode?.run { putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8) }
                            ?: throw NullPointerException(" reponse code is null")
                }
                Opcode.ABORT_OPERATION -> {
                    putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                    putIntValue(Operator.NULL.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                }
                Opcode.NUMBER_OF_STORED_RECORDS_RESPONSE -> {
                    putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                    putIntValue(Operator.NULL.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                    racpData.numberOfRecordResponse?.run { putIntValue(this, BluetoothGattCharacteristic.FORMAT_UINT16) }
                            ?: throw NullPointerException(" nnumber of records is null")
                }
                else -> {
                    Log.e(tag, "unknown opcode")
                }
            }
            if (racpData.hasCrc) {
                attachCrc()
            }
        }
        return rawData
    }

    /**
     * A helper function to compose the parameter portion of the packet
     */
    private fun composeQueryParameters() {
        racpData.operator?.run {
            when (this) {
                Operator.LAST_RECORD,
                Operator.FIRST_RECORD,
                Operator.ALL_RECORDS -> {
                    putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                }
                Operator.GREATER_THAN_OR_EQUAL_TO -> {
                    putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                    racpData.filterType?.run {
                        putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                        when (this) {
                            Filter.SEQUENCE_NUMBER -> {
                                racpData.minimumFilterValueSequenceNumber?.run {
                                    putIntValue(this, BluetoothGattCharacteristic.FORMAT_UINT16)
                                } ?: throw NullPointerException("The minimum value is null")
                            }
                            Filter.USER_FACING_TIME -> {
                                racpData.minimumFilterValueUserFacingTime?.run {
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
                    racpData.filterType?.run {
                        putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                        when (this) {
                            Filter.SEQUENCE_NUMBER -> {
                                racpData.maximumFilterValueSequenceNumber?.run {
                                    putIntValue(this, BluetoothGattCharacteristic.FORMAT_UINT16)
                                } ?: throw NullPointerException("The maximum value is null")
                            }
                            Filter.USER_FACING_TIME -> {
                                racpData.maximumFilterValueUserFacingTime?.run {
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
                    racpData.filterType?.run {
                        putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8)
                        when (this) {
                            Filter.SEQUENCE_NUMBER -> {
                                racpData.minimumFilterValueSequenceNumber?.run {
                                    putIntValue(this, BluetoothGattCharacteristic.FORMAT_UINT16)
                                } ?: throw NullPointerException("The minimum value is null")
                                racpData.maximumFilterValueSequenceNumber?.run {
                                    putIntValue(this, BluetoothGattCharacteristic.FORMAT_UINT16)
                                } ?: throw NullPointerException("The maximum value is null")
                            }
                            Filter.USER_FACING_TIME -> {
                                racpData.minimumFilterValueUserFacingTime?.run {
                                    putBluetoothDateTime(this)
                                } ?: throw NullPointerException("The minimum value is null")
                                racpData.maximumFilterValueUserFacingTime?.run {
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