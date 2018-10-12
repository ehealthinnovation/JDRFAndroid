package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter

class ProfileTemplateTimeBlock : BaseOperandWriter {

    override val tag = ProfileTemplateTimeBlock::class.java.canonicalName as String

    constructor() : super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var duration:Int? = null
    var rate: Float? = null

    override fun compose(): Boolean {
        if(!hasValidArguments()){
            return false
        }
        setIntValue(duration!!, BluetoothGattCharacteristic.FORMAT_UINT16)
        //todo The spec does not state which exponent to use
        setFloatValue((rate!!*10).toInt(), -1, BluetoothGattCharacteristic.FORMAT_SFLOAT)
        return true
    }

    override fun hasValidArguments(): Boolean = (duration!=null && rate!=null)

}