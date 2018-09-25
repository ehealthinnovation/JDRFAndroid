package org.ehealthinnovation.jdrfandroidbleparser.cgm.characteristic

import android.bluetooth.BluetoothGattCharacteristic
import android.util.Log
import org.ehealthinnovation.jdrfandroidbleparser.cgm.dataobject.CalibrationRecord
import org.ehealthinnovation.jdrfandroidbleparser.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.common.Composable
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.cgmcp.Opcode
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.cgmcp.ResponseCode

/**
The CGM Specific Ops Control Point encapsulates all functionality and mechanisms that are unique to
a CGM-device. This control point is used with a service to provide CGM specific functionality and
the ability to change CGM specific settings of the device. This includes functions like setting the
CGM Communication Interval or the sending a calibration value to the device. The criterion in the
Operand field is defined by the service that references this characteristic
 */
class CgmSpecificOpsControlPointCharacteristic : BaseCharacteristic, Composable {
    override val tag: String = CgmSpecificOpsControlPointCharacteristic::class.java.canonicalName

    constructor(c: BluetoothGattCharacteristic, hasCrc: Boolean = false, isComposing: Boolean = false) :
            super(c, GattCharacteristic.RECORD_ACCESS_CONTROL_POINT.assigned, hasCrc, isComposing){
        this.hasCrc = hasCrc
    }

    /**
     * Op Code specifying the operation
     */
    var opcode: Opcode? = null

    /**
     * Operand
     */
    var operandShortFloat: Float? = null
    var operandUnsignedInteger: Int? = null
    var operandCalibrationRecord: CalibrationRecord? = null
    var operandRequestOpcode: Opcode? = null
    var operandResponseCode: ResponseCode? = null

    var hasCrc: Boolean

    override fun parse(c: BluetoothGattCharacteristic): Boolean {
        var errorFreeParsing = false
        opcode = Opcode.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
        //Extract the operands
        opcode?.let { opcode ->
            when(opcode){
                Opcode.SET_CGM_COMMUNICATION_INTERVAL,
                Opcode.CGM_COMMUNICATION_INTERVAL_RESPONSE->{
                    operandUnsignedInteger = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)
                    if(operandUnsignedInteger==null){
                        throw NullPointerException("Operand is null")
                    }
                }
                Opcode.GET_CGM_COMMUNICATION_INTERVAL,
                Opcode.GET_PATIENT_HIGH_ALERT_LEVEL,
                Opcode.GET_PATIENT_LOW_ALERT_LEVEL,
                Opcode.GET_HYPO_ALERT_LEVEL,
                Opcode.GET_HYPER_ALERT_LEVEL,
                Opcode.GET_RATE_OF_DECREASE_ALERT_LEVEL,
                Opcode.RESET_DEVICE_SPECIFIC_ALERT,
                Opcode.START_THE_SESSION,
                Opcode.STOP_THE_SESSION->{

                }
                Opcode.SET_GLUCOSE_CALIBRATION_VALUE,
                Opcode.GLUCOSE_CALIBRATION_VALUE_RESPONSE->{
                    operandCalibrationRecord = CalibrationRecord(
                            concentration = getNextFloatValue(c, BluetoothGattCharacteristic.FORMAT_SFLOAT),
                            calibrationTime = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16),
                            typeSampleLocation = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8),
                            nextCalibrationTime = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16),
                            recordNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16),
                            calibrationStatus = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)
                    )
                }
                Opcode.GET_GLUCOSE_CALIBRATION_VALUE->{
                    operandUnsignedInteger = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                }
                Opcode.SET_PATIENT_HIGH_ALERT_LEVEL,
                Opcode.SET_PATIENT_LOW_ALERT_LEVEL,
                Opcode.SET_HYPO_ALERT_LEVEL,
                Opcode.SET_HYPER_ALERT_LEVEL,
                Opcode.SET_RATE_OF_DECREASE_ALERT_LEVEL,
                Opcode.SET_RATE_OF_INCREASE_ALERT_LEVEL,
                Opcode.PATIENT_HIGH_ALERT_LEVEL_RESPONSE,
                Opcode.PATIENT_LOW_ALERT_LEVEL_RESPONSE,
                Opcode.HYPO_ALERT_LEVEL_RESPONSE,
                Opcode.HYPER_ALERT_LEVEL_RESPONSE,
                Opcode.RATE_OF_DECREASE_ALERT_LEVEL_RESPONSE,
                Opcode.RATE_OF_INCREASE_ALERT_LEVEL_RESPONSE->{
                    operandShortFloat = getNextFloatValue(c, BluetoothGattCharacteristic.FORMAT_SFLOAT)
                }
                Opcode.RESPONSE_CODE->{
                    operandRequestOpcode = Opcode.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
                    operandResponseCode = ResponseCode.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
                }
                else->{
                    throw IllegalArgumentException()
                }
            }
            errorFreeParsing = true
        }
        return errorFreeParsing
    }

    override fun composeCharacteristic(hasCrc: Boolean): ByteArray {
        opcode?.let {opcode->
            when(opcode){
                Opcode.SET_CGM_COMMUNICATION_INTERVAL->{
                    putIntValue(opcode.key, BluetoothGattCharacteristic.FORMAT_UINT8)

                }

            }

        }

    }

}