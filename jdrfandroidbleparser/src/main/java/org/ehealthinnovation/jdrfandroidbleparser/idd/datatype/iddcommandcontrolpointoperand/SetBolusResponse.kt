package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser

class SetBolusResponse : BaseOperandParser {

    override val tag = SetBolusResponse::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var bolusId: Int? = null

    override fun parse(): Boolean {
        bolusId = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
        return true
    }
}