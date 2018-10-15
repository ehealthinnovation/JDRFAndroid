package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser

class ResetTemplateStatusResponse : BaseOperandParser {

    override val tag = ResetTemplateStatusResponse::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var numberOfTemplateReset: Int? = null
    var templateNumbers: MutableList<Int> = mutableListOf()

    override fun parse(): Boolean {
        numberOfTemplateReset = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        numberOfTemplateReset?.let {
            for (index in 0 until it) {
                templateNumbers.add(index, getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
            }
        }
        return true
    }
}