package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser

class ReservoirRemainingAmountChanged : BaseOperandParser {

    override val tag = ReservoirRemainingAmountChanged::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var reservoirRemainingAmount: kotlin.Float? = null

    override fun parse(): Boolean {
        reservoirRemainingAmount = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        return true
    }
}