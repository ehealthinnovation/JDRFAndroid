package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser

class PrimingStarted : BaseOperandParser {

    override val tag = PrimingStarted::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var programmedAmount: Float? = null

    override fun parse(): Boolean {
        programmedAmount = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        return true
    }
}