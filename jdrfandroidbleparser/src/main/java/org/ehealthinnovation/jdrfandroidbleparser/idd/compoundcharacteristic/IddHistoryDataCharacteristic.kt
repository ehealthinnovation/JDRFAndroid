package org.ehealthinnovation.jdrfandroidbleparser.idd.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.Opcode
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.EventType
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommanddataoperand.*
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata.*
import unsigned.Uint
import unsigned.toUint

class IddHistoryDataCharacteristic : BaseCharacteristic {
    override val tag: String = IddHistoryDataCharacteristic::class.java.canonicalName

    constructor(characteristic: BluetoothGattCharacteristic?, hasCrc: Boolean = false, hasE2eCounter: Boolean = false) : super(characteristic, GattCharacteristic.IDD_STATUS_READER_CONTROL_POINT.assigned, hasCrc = hasCrc, hasE2eCounter = hasE2eCounter) {
        this.hasCrc = hasCrc
        this.hasE2eCounter = hasE2eCounter
    }

    var hasCrc: Boolean
    var hasE2eCounter: Boolean
    var eventType: EventType? = null
    var sequenceNumber: Uint? = null
    var relativeOffset: Int? = null
    var eventData: BaseOperandParser? = null
    var e2eCounter: Int? = null


    override fun parse(c: BluetoothGattCharacteristic, hasE2eCounter: Boolean): Boolean {
        var errorFreeParsing = false
        eventType = EventType.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16))
        eventType?.let { eventType ->
            sequenceNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT32).toUint()
            relativeOffset = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)

            var operandRawData = getRawValueAfterOffset(c)
            //here we do some conditioning for the operand raw data since it may contain e2e-counter
            //or crc
            if (hasCrc) {
                operandRawData = operandRawData.let { it.copyOf(it.size - 2) }
            }

            //Now decode the operand
            when (eventType) {
                //todo collapse the event type label in the operand class
                EventType.REFERENCE_TIME -> eventData = ReferenceTime(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.REFERENCE_TIME_BASE_OFFSET -> eventData = ReferenceTimeBaseOffset(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.BOLUS_CALCULATED_PART_1_OF_2 -> eventData = BolusCalculatedPart1of2(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.BOLUS_CALCULATED_PART_2_OF_2 -> eventData = BolusCalculatedPart2of2(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.BOLUS_PROGRAMMED_PART_1_OF_2 -> eventData = BolusProgrammedPart1Of2(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.BOLUS_PROGRAMMED_PART_2_OF_2 -> eventData = BolusProgrammedPart2Of2(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.BOLUS_DELIVERED_PART_1_OF_2 -> eventData = BolusDeliveredPart1of2(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.BOLUS_DELIVERED_PART_2_OF_2 -> eventData = BolusDeliveredPart2of2(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.DELIVERED_BASAL_RATE_CHANGED -> eventData = DeliveredBasalRateChanged(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.TBR_ADJUSTMENT_STARTED -> eventData = TBRAdjustmentStarted(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.TBR_ADJUSTMENT_ENDED -> eventData = TBRAdjustmentEnded(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.TBR_ADJUSTMENT_CHANGED -> eventData = TBRAdjustmentChanged(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.PROFILE_TEMPLATE_ACTIVATED -> eventData = ProfileTemplateActivated(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.BASAL_RATE_PROFILE_TEMPLATE_TIME_BLOCK_CHANGED -> eventData = BasalRateProfileTemplateTimeBlockChanged(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.TOTAL_DAILY_INSULIN_DELIVERY -> eventData = TotalDailyInsulinDelivery(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.THERAPY_CONTROL_STATE_CHANGED -> eventData = TherapyControlStateChanged(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.OPERATIONAL_STATE_CHANGED -> eventData = OperationalStateChanged(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.RESERVOIR_REMAINING_AMOUNT_CHANGED -> eventData = ReservoirRemainingAmountChanged(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.ANNUNCIATION_STATUS_CHANGED_PART_1_OF_2 -> eventData = AnnunciationStatusChangedPart1of2(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.ANNUNCIATION_STATUS_CHANGED_PART_2_OF_2 -> eventData = AnnunciationStatusChangedPart2of2(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.ISF_PROFILE_TEMPLATE_TIME_BLOCK_CHANGED -> eventData = ISFProfileTemplateTimeBlockChanged(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.I2CHO_RATIO_PROFILE_TEMPLATE_TIME_BLOCK_CHANGED -> eventData = I2CHORatioProfileTemplateTimeBlockChanged(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.TARGET_GLUCOSE_RANGE_PROFILE_TEMPLATE_TIME_BLOCK_CHANGED -> eventData = TargetGlucoseRangeProfileTemplateTimeBlockChanged(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.PRIMING_STARTED -> eventData = PrimingStarted(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.PRIMING_DONE -> eventData = PrimingDone(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.BOLUS_TEMPLATE_CHANGED_PART_1_OF_2 -> eventData = BolusTemplateChangedPart1of2(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.BOLUS_TEMPLATE_CHANGED_PART_2_OF_2 -> eventData = BolusTemplateChangedPart2of2(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.TBR_TEMPLATE_CHANGED -> eventData = TBRTemplateChanged(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.MAX_BOLUS_AMOUNT_CHANGED -> eventData = MaxBolusAmountChanged(operandRawData).also { it.apply { errorFreeParsing = parse() } }
                EventType.DATA_CORRUPTION,
                EventType.POINTER_EVENT -> {/*this event type does not have event data*/
                }
                else -> {
                    throw IllegalStateException("Opcode not recognied by the parsing function")
                }
            }
        }

        return errorFreeParsing
    }
}

