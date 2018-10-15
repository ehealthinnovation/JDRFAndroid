package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser

class MaxBolusAmountChanged : BaseOperandParser {

    override val tag = MaxBolusAmountChanged::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var oldMaxBolusAmount: Float? = null
    var newMaxBolusAmount: Float? = null

    override fun parse(): Boolean {
        oldMaxBolusAmount = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        newMaxBolusAmount = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        return true
    }
}