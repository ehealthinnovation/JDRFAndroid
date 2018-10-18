package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.sessionstarttime.DstOffset
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.RecordingReason
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.TBRAdjustmentStartedFlags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.TBRType
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.utility.BluetoothDateTime
import java.util.*

class TBRAdjustmentStarted : BaseOperandParser {

    override val tag = TBRAdjustmentStarted::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var flags: EnumSet<TBRAdjustmentStartedFlags>? = null
    var tbrType: TBRType? = null
    var tbrAdjustmentValue: Float? = null
    var tbrDurationProgrammed: Int? = null
    var tbrTemplateNumber: Int? = null

    override fun parse(): Boolean {
        flags = TBRAdjustmentStartedFlags.parseFlags(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        tbrType = TBRType.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        tbrAdjustmentValue = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        tbrDurationProgrammed = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
        flags?.let {
            if (it.contains(TBRAdjustmentStartedFlags.TBR_TEMPLATE_NUMBER_PRESENT)) {
                tbrTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
            }
        }
        return true
    }
}