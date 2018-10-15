package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser

class SetBolusTemplateResponse : BaseOperandParser {

    override val tag = SetBolusTemplateResponse::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var bolusTemplateNumber: Int? = null

    override fun parse(): Boolean {
        bolusTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        return true
    }
}