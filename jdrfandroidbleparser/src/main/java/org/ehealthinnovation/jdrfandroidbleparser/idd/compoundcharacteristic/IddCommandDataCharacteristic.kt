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
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommanddataoperand.*

class IddCommandDataCharacteristic: BaseCharacteristic {
    override val tag: String = IddCommandDataCharacteristic::class.java.canonicalName

    constructor(characteristic: BluetoothGattCharacteristic?, hasCrc: Boolean = false, hasE2eCounter: Boolean = false) : super(characteristic, GattCharacteristic.IDD_STATUS_READER_CONTROL_POINT.assigned, hasCrc = hasCrc, hasE2eCounter = hasE2eCounter) {
        this.hasCrc = hasCrc
        this.hasE2eCounter = hasE2eCounter
    }

    var hasCrc: Boolean
    var hasE2eCounter: Boolean
    var requestOpcode: Opcode? = null
    var operandParsing: BaseOperandParser? = null
    var e2eCounter: Int? = null


    override fun parse(c: BluetoothGattCharacteristic, hasE2eCounter: Boolean): Boolean {
        var errorFreeParsing = false
        requestOpcode = Opcode.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16))
        requestOpcode?.let { opcode ->
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
                Opcode.READ_BASAL_RATE_PROFILE_TEMPLATE_RESPONSE-> operandParsing = ReadBasalRateProfileTemplateResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.READ_ISF_PROFILE_TEMPLATE_RESPONSE-> operandParsing = ReadISFProfileTemplateResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.READ_I2CHO_RATIO_PROFILE_TEMPLATE_RESPONSE-> operandParsing = ReadI2CHORatioProfileTemplateResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.READ_TARGET_GLUCOSE_RANGE_PROFILE_TEMPLATE_RESPONSE-> operandParsing = ReadTargetGlucoseRangeProfileTemplateResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.GET_TEMPLATE_STATUS_AND_DETAILS_RESPONSE-> operandParsing = GetTemplateStatusAndDetailsResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                else -> {
                    throw IllegalStateException("Opcode not recognied by the parsing function")
                }
            }
        }

        return errorFreeParsing
    }
}

