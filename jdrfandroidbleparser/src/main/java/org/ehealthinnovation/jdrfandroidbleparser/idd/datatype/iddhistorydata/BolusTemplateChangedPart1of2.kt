package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.BolusType
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser

class BolusTemplateChangedPart1of2 : BaseOperandParser {

    override val tag = BolusTemplateChangedPart1of2::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var bolusTemplateNumber: Int? = null
    var bolusType: BolusType? = null
    var bolusFastAmount: Float? = null
    var bolusExtendedAmount: Float? = null
    var bolusDuration: Int? = null

    override fun parse(): Boolean {
        bolusTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        bolusType = BolusType.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        bolusFastAmount = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        bolusExtendedAmount = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        bolusDuration = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
        return true

    }
}