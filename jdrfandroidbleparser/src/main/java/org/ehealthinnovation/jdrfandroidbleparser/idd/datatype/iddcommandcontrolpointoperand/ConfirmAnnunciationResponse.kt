package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser

class ConfirmAnnunciationResponse: BaseOperandParser {

    override val tag = ConfirmAnnunciationResponse::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic):super(rawData, c)

    var annunciationInstanceId : Int? = null

    override fun parse(): Boolean {
        annunciationInstanceId = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
        return true
    }
}