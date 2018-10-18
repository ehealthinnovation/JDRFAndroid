package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter

class ResetTemplateStatus : BaseOperandWriter {

    override val tag = ResetTemplateStatus::class.java.canonicalName as String

    constructor() : super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var numberOfTemplateToReset: Int? = null
    var templateNumber: MutableList<Int> = mutableListOf()

    override fun compose(): Boolean {
        numberOfTemplateToReset?.let {
            setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT8)
            for (index in 0 until it) {
                setIntValue(templateNumber!![index], BluetoothGattCharacteristic.FORMAT_UINT8)
            }
        }
        return true
    }

    override fun hasValidArguments(): Boolean {
        if (numberOfTemplateToReset == null || templateNumber == null) {
            return false
        }

        if (templateNumber.size != numberOfTemplateToReset) {
            return false
        }

        return true
    }

}