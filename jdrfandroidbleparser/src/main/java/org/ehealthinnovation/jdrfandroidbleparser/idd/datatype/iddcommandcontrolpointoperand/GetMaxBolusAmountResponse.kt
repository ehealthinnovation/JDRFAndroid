package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser

class GetMaxBolusAmountResponse : BaseOperandParser {

    override val tag = GetMaxBolusAmountResponse::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var maxBolusAmount: Float? = null

    override fun parse(): Boolean {
        maxBolusAmount = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        return true
    }
}