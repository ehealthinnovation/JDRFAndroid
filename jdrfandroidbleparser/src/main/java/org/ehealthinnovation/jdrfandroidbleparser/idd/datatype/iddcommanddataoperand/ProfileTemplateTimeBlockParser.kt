package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommanddataoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter

class ProfileTemplateTimeBlockParser : BaseOperandParser{

    override val tag = ProfileTemplateTimeBlockParser::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic):super(rawData, c)

    var duration:Int? = null
    var rate: Float? = null

    override fun parse(): Boolean {
        duration = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
        rate = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        return true
    }

}