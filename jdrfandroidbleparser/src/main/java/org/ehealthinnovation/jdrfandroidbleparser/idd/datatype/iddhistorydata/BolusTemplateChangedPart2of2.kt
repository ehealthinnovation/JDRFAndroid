package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.BolusType
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.GetBolusTemplateResponseFlags
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import java.util.*

class BolusTemplateChangedPart2of2 : BaseOperandParser {

    override val tag = BolusTemplateChangedPart2of2::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var flags: EnumSet<GetBolusTemplateResponseFlags>? = null
    var bolusDelayTime: Int? = null

    override fun parse(): Boolean {
        flags = GetBolusTemplateResponseFlags.parseFlags(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        flags?.let {
            if (it.contains(GetBolusTemplateResponseFlags.BOLUS_DELAY_TIME_PRESENT)) {
                bolusDelayTime = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
            }
        }
        return true
    }
}