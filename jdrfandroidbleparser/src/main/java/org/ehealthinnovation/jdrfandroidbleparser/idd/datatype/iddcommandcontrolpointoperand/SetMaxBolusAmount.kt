package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter

class SetMaxBolusAmount : BaseOperandWriter {
    override val tag = SetMaxBolusAmount::class.java.canonicalName as String

    constructor() : super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var maxBolusAmount: Float? = null

    override fun compose(): Boolean {
        if (!hasValidArguments()) {
            return false
        }
        maxBolusAmount?.let { setFloatValue((it * 10).toInt(), -1, BluetoothGattCharacteristic.FORMAT_SFLOAT) }
        return true
    }

    override fun hasValidArguments(): Boolean {
        return (maxBolusAmount != null)
    }
}