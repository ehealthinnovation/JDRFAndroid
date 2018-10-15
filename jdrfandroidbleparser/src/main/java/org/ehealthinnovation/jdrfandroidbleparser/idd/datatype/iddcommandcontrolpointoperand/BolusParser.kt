package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.BolusType
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter

class BolusParser: BaseOperandParser {

    override val tag = BolusParser::class.java.canonicalName as String


    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var bolueType: BolusType? = null
    var bolusFastAmount: Float?= null
    var bolusExtendedAmount: Float?= null
    var bolusDuration: Int?= null

    override fun parse(): Boolean {
        bolueType = BolusType.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        bolusFastAmount = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        bolusExtendedAmount = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        bolusDuration = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
        return true
    }

}