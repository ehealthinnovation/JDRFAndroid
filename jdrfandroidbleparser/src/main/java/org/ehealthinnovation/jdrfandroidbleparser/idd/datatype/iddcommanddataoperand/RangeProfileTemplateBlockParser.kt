package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommanddataoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter

class RangeProfileTemplateBlockParser : BaseOperandParser {

    override val tag = RangeProfileTemplateBlockParser::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic):super(rawData, c)

    var duration:Int? = null
    var lowerLimit: Float? = null
    var upperLimit: Float? = null

    override fun parse(): Boolean {
        duration = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
        lowerLimit = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        upperLimit = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        return true
    }

}