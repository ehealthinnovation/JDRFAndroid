package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter

class CancelBolus : BaseOperandWriter {

    override val tag = CancelBolus::class.java.canonicalName as String

    constructor() : super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var bolusId: Int? = null

    override fun compose(): Boolean {
        if (!hasValidArguments()) {
            return false
        }
        bolusId?.let { setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT16) }
        return true
    }

    override fun hasValidArguments(): Boolean = (bolusId != null)
}