package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter

class GetTBRTemplate : BaseOperandWriter {

    override val tag = GetTBRTemplate::class.java.canonicalName as String

    constructor() : super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var tbrTemplateNumber: Int? = null

    override fun compose(): Boolean {
        if (!hasValidArguments()) {
            return false
        }
        tbrTemplateNumber?.let { setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT8) }
        return true
    }

    override fun hasValidArguments(): Boolean = tbrTemplateNumber != null
}