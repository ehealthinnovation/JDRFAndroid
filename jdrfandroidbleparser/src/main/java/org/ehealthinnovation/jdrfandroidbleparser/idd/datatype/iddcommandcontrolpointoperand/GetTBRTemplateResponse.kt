package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser

class GetTBRTemplateResponse: BaseOperandParser{

    override val tag = GetTBRTemplateResponse::class.java.canonicalName as String

    constructor(): super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    override fun parse(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}