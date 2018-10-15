package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.WriteBasalRateProfileTemplateFlags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.WriteBasalRateProfileTemplateResponseFlags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.WriteISFProfileTemplateResponseFlags
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import java.util.*

class WriteISFProfileTemplateResponse : BaseOperandParser {

    override val tag = WriteISFProfileTemplateResponse::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var flags: EnumSet<WriteISFProfileTemplateResponseFlags>? = null
    var isfProfileTemplateNumber: Int? = null
    var firstTimeBlockNumberIndex: Int? = null

    override fun parse(): Boolean {
        flags = WriteISFProfileTemplateResponseFlags.parseFlags(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        isfProfileTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        firstTimeBlockNumberIndex = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        return true
    }

}