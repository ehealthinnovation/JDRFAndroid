package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter

class GetBolusTemplate : BaseOperandWriter {

    override val tag = GetBolusTemplate::class.java.canonicalName as String

    constructor() : super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var bolusTemplateNumber: Int? = null

    override fun compose(): Boolean {
        if (!hasValidArguments()) {
            return false
        }

        bolusTemplateNumber?.let { setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT8) }
        return true

    }

    override fun hasValidArguments(): Boolean = (bolusTemplateNumber != null)
}