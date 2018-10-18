package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.status.Float
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter

class SetTherapyControlState : BaseOperandWriter {

    override val tag = SetTherapyControlState::class.java.canonicalName as String

    constructor() : super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var therapyControlState: Float? = null

    override fun hasValidArguments(): Boolean {
        return therapyControlState != null
    }

    override fun compose(): Boolean {
        if (!hasValidArguments()) {
            return false
        }
        therapyControlState?.let { setIntValue(it.key, BluetoothGattCharacteristic.FORMAT_UINT8) }
        return true
    }

}