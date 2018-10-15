package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.WriteBasalRateProfileTemplateFlags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.WriteBasalRateProfileTemplateResponseFlags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.WriteI2CHORatioProfileTemplateResponseFlags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.WriteISFProfileTemplateResponseFlags
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import java.util.*

class WriteI2CHORatioProfileTemplateResponse : BaseOperandParser {

    override val tag = WriteI2CHORatioProfileTemplateResponse::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var flags: EnumSet<WriteI2CHORatioProfileTemplateResponseFlags>? = null
    var i2choRatioProfileTemplateNumber: Int? = null
    var firstTimeBlockNumberIndex: Int? = null

    override fun parse(): Boolean {
        flags = WriteI2CHORatioProfileTemplateResponseFlags.parseFlags(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        i2choRatioProfileTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        firstTimeBlockNumberIndex = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        return true
    }

}