package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter

class ReadISFProfileTemplate : BaseOperandWriter {

    override val tag = ReadISFProfileTemplate::class.java.canonicalName as String

    constructor() : super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var isfProfileTemplateNumber: Int? = null

    override fun compose(): Boolean {
        if (!hasValidArguments()) {
            return false
        }

        isfProfileTemplateNumber?.let { setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT8) }
        return true

    }

    override fun hasValidArguments(): Boolean = (isfProfileTemplateNumber != null)
}