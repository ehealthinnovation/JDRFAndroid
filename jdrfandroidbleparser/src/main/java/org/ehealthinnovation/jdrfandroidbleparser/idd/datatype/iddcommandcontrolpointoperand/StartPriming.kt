package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter

class StartPriming :BaseOperandWriter{
    override val tag =StartPriming ::class.java.canonicalName as String

    constructor(): super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var amount: Float? = null

    override fun compose(): Boolean {
        if(!hasValidArguments()){
            return false
        }
        amount?.let { setFloatValue((it*10).toInt(), -1, BluetoothGattCharacteristic.FORMAT_SFLOAT) }
        return true
    }

    override fun hasValidArguments(): Boolean {
        return (amount!=null)
    }
}