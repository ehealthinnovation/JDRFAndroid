package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.TBRType
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser

class GetTBRTemplateResponse : BaseOperandParser {

    override val tag = GetTBRTemplateResponse::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var tbrTemplateNumber: Int? = null
    var tbrType: TBRType? = null
    var tbrAdjustmentValue: Float? = null
    var tbrDuration: Int? = null

    override fun parse(): Boolean {
        tbrTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        tbrType = TBRType.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        tbrAdjustmentValue = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        tbrDuration = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
        return true
    }
}