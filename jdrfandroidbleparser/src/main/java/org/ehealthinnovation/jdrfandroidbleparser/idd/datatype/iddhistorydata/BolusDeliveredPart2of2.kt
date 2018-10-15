package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.BolusType
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.BolusDeliveryPart2Flags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.BolusEndReason
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.BolusActivationType
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import unsigned.Uint
import unsigned.toUint
import java.util.*

class BolusDeliveredPart2of2 : BaseOperandParser {

    override val tag = BolusDeliveredPart2of2::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var flags: EnumSet<BolusDeliveryPart2Flags>? = null
    var bolusStartTimeOffset: Uint? = null
    var bolusActivationType: BolusActivationType? = null
    var bolusEndReason: BolusEndReason? = null
    var annunciationInstanceId: Int? = null


    override fun parse(): Boolean {
        flags = BolusDeliveryPart2Flags.parseFlags(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        bolusStartTimeOffset = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT32).toUint()
        flags?.let {
            if (it.contains(BolusDeliveryPart2Flags.BOLUS_ACTIVATION_TYPE_PRESENT)) {
                bolusActivationType = BolusActivationType.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
            }
            if (it.contains(BolusDeliveryPart2Flags.BOLUS_END_REASON_PRESENT)) {
                bolusEndReason = BolusEndReason.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
            }
            if (it.contains(BolusDeliveryPart2Flags.ANNUNCIATION_INSTANCE_ID_PRESENT)) {
                annunciationInstanceId = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
            }
        }
        return true
    }
}