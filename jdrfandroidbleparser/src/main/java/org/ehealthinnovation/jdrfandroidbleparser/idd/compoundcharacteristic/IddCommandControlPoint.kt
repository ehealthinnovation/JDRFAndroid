package org.ehealthinnovation.jdrfandroidbleparser.idd.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import android.util.Log
import org.ehealthinnovation.jdrfandroidbleparser.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.common.Composable
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.Opcode
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand.*

class IddCommandControlPoint : BaseCharacteristic, Composable {
    override val tag: String = IddCommandControlPoint::class.java.canonicalName

    constructor(characteristic: BluetoothGattCharacteristic?, hasCrc: Boolean = false, hasE2eCounter: Boolean = false) : super(characteristic, GattCharacteristic.IDD_STATUS_READER_CONTROL_POINT.assigned, hasCrc = hasCrc, hasE2eCounter = hasE2eCounter) {
        this.hasCrc = hasCrc
        this.hasE2eCounter = hasE2eCounter
    }

    var hasCrc: Boolean
    var hasE2eCounter: Boolean
    var opcode: Opcode? = null
    var operandParsing: BaseOperandParser? = null
    var operandWriter: BaseOperandWriter? = null
    var e2eCounter: Int? = null


    override fun parse(c: BluetoothGattCharacteristic, hasE2eCounter: Boolean): Boolean {
        var errorFreeParsing = false
        opcode = Opcode.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16))
        opcode?.let { opcode ->
            var operandRawData = getRawValueAfterOffset(c)
            //here we do some conditioning for the operand raw data since it may contain e2e-counter
            //or crc
            if (hasCrc) {
                operandRawData = operandRawData.let { it.copyOf(it.size - 2) }
            }

            if (hasE2eCounter) {
                operandRawData = operandRawData.let {
                    e2eCounter = it.last().toInt()
                    it.copyOf(it.size - 1)
                }
            }

            //Now decode the operand
            when (opcode) {
                Opcode.RESPONSE_CODE -> operandParsing = GenericResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.SNOOZE_ANNUNCIATIONRESPONSE -> operandParsing = SnoozeAnnunciationResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.CONFIRM_ANNUNCIATIONRESPONSE -> operandParsing = ConfirmAnnunciationResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.READ_BASAL_RATE_PROFILE_TEMPLATE_RESPONSE -> {
                    Log.i(tag, "this opcode does not have operand")
                }
                Opcode.WRITE_BASAL_RATE_PROFILE_TEMPLATE_RESPONSE -> operandParsing = WriteBasalRateProfileTemplateResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.GET_TBR_TEMPLATE_RESPONSE -> operandParsing = GetTBRTemplateResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.SET_BOLUS_RESPONSE -> operandParsing = SetBolusResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.CANCEL_BOLUS_RESPONSE -> operandParsing = CancelBolusResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.GET_AVAILABLE_BOLUSES_RESPONSE -> operandParsing = GetAvailableBolusesResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.GET_BOLUS_TEMPLATE_RESPONSE -> operandParsing = GetBolusTemplateResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.SET_BOLUS_TEMPLATE_RESPONSE -> operandParsing = SetBolusTemplateResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.RESET_TEMPLATE_STATUS_RESPONSE -> operandParsing = ResetTemplateStatusResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.ACTIVATE_PROFILE_TEMPLATES_RESPONSE -> operandParsing = ActivateProfileTemplatesResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.GET_ACTIVATED_PROFILE_TEMPLATES_RESPONSE -> operandParsing = GetActivatedProfileTemplatesResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.WRITE_ISF_PROFILE_TEMPLATE_RESPONSE -> operandParsing = WriteISFProfileTemplateResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.WRITE_I2CHO_RATIO_PROFILE_TEMPLATE_RESPONSE -> operandParsing = WriteI2CHORatioProfileTemplateResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.WRITE_TARGET_GLUCOSE_RANGE_PROFILE_TEMPLATE_RESPONSE -> operandParsing = WriteTargetGlucoseRangeProfileTemplateResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.GET_MAX_BOLUS_AMOUNT_RESPONSE -> operandParsing = GetMaxBolusAmountResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                else -> {
                    throw IllegalStateException("Opcode not recognied by the parsing function")
                }
            }
        }

        return errorFreeParsing
    }

    override fun composeCharacteristic(hasCrc: Boolean): ByteArray {
        var operandBuffer: ByteArray

        opcode?.run {
            putIntValue(this.key, BluetoothGattCharacteristic.FORMAT_UINT8)
            when (this) {
                Opcode.SET_THERAPY_CONTROL_STATE,
                Opcode.SNOOZE_ANNUNCIATION,
                Opcode.CONFIRM_ANNUNCIATION,
                Opcode.READ_BASAL_RATE_PROFILE_TEMPLATE,
                Opcode.WRITE_BASAL_RATE_PROFILE_TEMPLATE,
                Opcode.SET_TBR_ADJUSTMENT,
                Opcode.GET_TBR_TEMPLATE,
                Opcode.SET_TBR_TEMPLATE,
                Opcode.SET_BOLUS,
                Opcode.CANCEL_BOLUS,
                Opcode.GET_BOLUS_TEMPLATE,
                Opcode.SET_BOLUS_TEMPLATE,
                Opcode.GET_TEMPLATE_STATUS_AND_DETAILS,
                Opcode.RESET_TEMPLATE_STATUS,
                Opcode.ACTIVATE_PROFILE_TEMPLATES,
                Opcode.GET_ACTIVATED_PROFILE_TEMPLATES,
                Opcode.START_PRIMING,
                Opcode.SET_INITIAL_RESERVOIR_FILL_LEVEL,
                Opcode.READ_ISF_PROFILE_TEMPLATE,
                Opcode.WRITE_ISF_PROFILE_TEMPLATE,
                Opcode.READ_I2CHO_RATIO_PROFILE_TEMPLATE,
                Opcode.WRITE_I2CHO_RATIO_PROFILE_TEMPLATE,
                Opcode.READ_TARGET_GLUCOSE_RANGE_PROFILE_TEMPLATE,
                Opcode.WRITE_TARGET_GLUCOSE_RANGE_PROFILE_TEMPLATE,
                Opcode.SET_MAX_BOLUS_AMOUNT -> {
                    operandBuffer = operandWriter?.apply { compose() }?.rawData ?: throw NullPointerException("Operand argument is null")
                    putByteArray(operandBuffer)
                }
                Opcode.SET_FLIGHT_MODE,
                Opcode.CANCEL_TBR_ADJUSTMENT,
                Opcode.GET_AVAILABLE_BOLUSES,
                Opcode.STOP_PRIMING,
                Opcode.RESET_RESERVOIR_INSULIN_OPERATION_TIME,
                Opcode.GET_MAX_BOLUS_AMOUNT -> {
                    //these opcode does not require operands
                }
                else -> {
                    throw IllegalStateException("Opcode not recognied by the parsing function")
                }
            }
            if (hasE2eCounter) {
                e2eCounter?.let { putIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT8) }
                        ?: throw NullPointerException("e2e counter is null")
            }
            if (hasCrc) {
                attachCrc()
            }
            return rawData
        } ?: throw NullPointerException("opcode is null")
    }
}
