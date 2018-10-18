package org.ehealthinnovation.jdrfandroidbleparser.idd.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.common.Composable
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.Opcode
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand.*

class IddStatusReaderControlPoint : BaseCharacteristic, Composable {
    override val tag: String = IddStatusReaderControlPoint::class.java.canonicalName

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
                Opcode.RESPONSE_CODE -> operandParsing = GenericReponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.RESET_STATUS -> operandParsing = ResetStatus(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.GET_ACTIVE_BOLUS_IDS_RESPONSE -> operandParsing = ActiveBolusIDs(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.GET_ACTIVE_BOLUS_DELIVERY_RESPONSE -> operandParsing = GetActiveBolusDeliveryResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.GET_ACTIVE_BASAL_RATE_DELIVERY_RESPONSE -> operandParsing = GetActiveBasalRateDeliveryResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.GET_TOTAL_DAILY_INSULIN_STATUS_RESPONSE -> operandParsing = GetTotalDailyInsulinStatusResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.GET_COUNTER_RESPONSE -> operandParsing = GetCounterResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.GET_DELIVERED_INSULIN_RESPONSE -> operandParsing = GetDeliveredInsulinResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                Opcode.GET_INSULIN_ON_BOARD_RESPONSE -> operandParsing = GetInsulinOnBoardResponse(operandRawData).also { it.apply { errorFreeParsing = parse() } }
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
                Opcode.GET_ACTIVE_BOLUS_DELIVERY,
                Opcode.GET_COUNTER,
                Opcode.GET_DELIVERED_INSULIN -> {
                    operandBuffer = operandWriter?.apply { compose() }?.rawData ?: throw NullPointerException("Operand argument is null")
                    putByteArray(operandBuffer)
                }
                Opcode.GET_ACTIVE_BOLUS_IDS,
                Opcode.GET_ACTIVE_BASAL_RATE_DELIVERY,
                Opcode.GET_TOTAL_DAILY_INSULIN_STATUS,
                Opcode.GET_INSULIN_ON_BOARD -> {
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
