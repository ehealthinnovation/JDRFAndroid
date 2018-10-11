package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.CounterType
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.CounterValueSelection

class GetCounter: BaseOperandWriter {

    override val tag = GetCounter::class.java.canonicalName as String

    constructor(): super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var counterType: CounterType? = null
    var counterValueSelection: CounterValueSelection? = null

    override fun compose(): Boolean {
        if(!hasValidArguments()){
            return false
        }
        counterType?.let { setIntValue(it.key, BluetoothGattCharacteristic.FORMAT_UINT8) }
        counterValueSelection?.let { setIntValue(it.key, BluetoothGattCharacteristic.FORMAT_UINT8) }
        return true
    }

    override fun hasValidArguments(): Boolean {
        return !(counterType==null || counterValueSelection == null)
    }

}