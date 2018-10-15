package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser

class GetActivatedProfileTemplatesResponse : BaseOperandParser {


    override val tag = GetActivatedProfileTemplatesResponse::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var numberOfTemplateActivated: Int? = null
    var profileTemplateNumbers: MutableList<Int> = mutableListOf()

    override fun parse(): Boolean {
        numberOfTemplateActivated = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        numberOfTemplateActivated?.let {
            for (index in 0 until it) {
                profileTemplateNumbers.add(index, getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
            }
        }
        return true
    }
}