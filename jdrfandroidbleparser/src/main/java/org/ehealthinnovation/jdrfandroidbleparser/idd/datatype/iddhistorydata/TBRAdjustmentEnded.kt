package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.sessionstarttime.DstOffset
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.*
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.TBRType
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.utility.BluetoothDateTime
import java.util.*

class TBRAdjustmentEnded : BaseOperandParser {

    override val tag = TBRAdjustmentEnded::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var flags: EnumSet<TBRAdjustmentEndedFlags>? = null
    var lastSetTBRType: TBRType? = null
    var effectiveTBRDuration: Int? = null
    var tbrEndReason: TBREndReason? = null
    var lastSetTBRTemplateNumber: Int? = null
    var annunciationInstanceId: Int? = null

    override fun parse(): Boolean {
        flags = TBRAdjustmentEndedFlags.parseFlags(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        lastSetTBRType = TBRType.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        effectiveTBRDuration = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
        tbrEndReason = TBREndReason.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        flags?.let {
            if (it.contains(TBRAdjustmentEndedFlags.LAST_SET_TBR_TEMPLATE_NUMBER_PRESENT)) {
                lastSetTBRTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
            }
            if (it.contains(TBRAdjustmentEndedFlags.ANNUNCIATION_INSTANCE_ID_PRESENT)) {
                annunciationInstanceId = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
            }
        }
        return true
    }
}