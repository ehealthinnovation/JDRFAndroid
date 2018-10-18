package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser

class SetTBRTemplateResponse : BaseOperandParser {

    override val tag = SetTBRTemplateResponse::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var tbrTemplateNumber: Int? = null

    override fun parse(): Boolean {
        tbrTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        return true
    }

}