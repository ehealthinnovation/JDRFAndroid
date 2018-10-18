package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter

class ActivateProfileTemplates : BaseOperandWriter {

    override val tag = ActivateProfileTemplates::class.java.canonicalName as String

    constructor() : super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var numberOfTemplateToActivate: Int? = null
    var profileTemplateNumber: MutableList<Int> = mutableListOf()

    override fun compose(): Boolean {
        numberOfTemplateToActivate?.let {
            setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT8)
            for (index in 0 until it) {
                setIntValue(profileTemplateNumber!![index], BluetoothGattCharacteristic.FORMAT_UINT8)
            }
        }
        return true
    }

    override fun hasValidArguments(): Boolean {
        if (numberOfTemplateToActivate == null || profileTemplateNumber == null) {
            return false
        }

        if (profileTemplateNumber.size != numberOfTemplateToActivate) {
            return false
        }

        return true
    }

}