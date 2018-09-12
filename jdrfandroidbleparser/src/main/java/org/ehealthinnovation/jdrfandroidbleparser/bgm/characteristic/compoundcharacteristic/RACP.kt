package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic

import android.bluetooth.BluetoothGatt
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
import kotlin.math.log

class RACP(c: BluetoothGattCharacteristic, hasCrc: Boolean = false) :
        BaseCharacteristic(c, GattCharacteristic.RECORD_ACCESS_CONTROL_POINT.assigned, hasCrc),
        Composable {
    override val tag: String = RACP::class.java.canonicalName


    var opcode: Opcode? = null
    var operator: Operator? = null
    var filterType: Filter? = null

    /**
     * The following variables stores the possible variables from parsing
     */
    var minimumFilterValueSequenceNumber: Int? = null
    var maximumFilterValueSequenceNumber: Int? = null
    var minimumFilterValueUserFacingTime: BluetoothDateTime? = null
    var maximumFilterValueUserFacingTime: BluetoothDateTime? = null
    var numberOfRecordResponse : Int? = null
    var requestOpcode : Opcode? = null
    var responseCode : ResponseCode? = null

    val hasCrc: Boolean = hasCrc

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

    private fun parseGenericOperand(c: BluetoothGattCharacteristic) {
        if (filterType == Filter.SEQUENCE_NUMBER) {
            when (operator) {
                Operator.LESS_THAN_OR_EQUAL_TO -> {
                    maximumFilterValueSequenceNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                }
                Operator.GREATER_THAN_OR_EQUAL_TO -> {
                    minimumFilterValueSequenceNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                }
                Operator.WITHIN_RANGE_OF_INCLUSIVE -> {
                    maximumFilterValueSequenceNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                    minimumFilterValueSequenceNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                }
                Operator.ALL_RECORDS,
                Operator.FIRST_RECORD,
                Operator.LAST_RECORD -> {
                    Log.i(tag, "${operator.toString()} does not need operand")
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
                Operator.ALL_RECORDS,
                Operator.FIRST_RECORD,
                Operator.LAST_RECORD -> {
                    Log.i(tag, "opcode $opcode does not need operands")
                }
                else -> {
                   Log.e(tag, "unknown operator $operator")
                }
            }
        }else{
            throw IllegalStateException("unable to handle filter type $filterType")
        }
    }

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

    override fun composeCharacteristic(hasCrc: Boolean): ByteArray {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}