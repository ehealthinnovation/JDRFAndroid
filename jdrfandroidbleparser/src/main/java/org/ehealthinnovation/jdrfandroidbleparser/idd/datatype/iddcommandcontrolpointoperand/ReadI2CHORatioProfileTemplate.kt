package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter

class ReadI2CHORatioProfileTemplate:BaseOperandWriter {

    override val tag = ReadI2CHORatioProfileTemplate::class.java.canonicalName as String

    constructor(): super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var i2choRatioProfileTemplateNumber:Int?= null

    override fun compose(): Boolean {
        if (!hasValidArguments()){
            return false
        }

        i2choRatioProfileTemplateNumber?.let { setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT8) }
        return true

    }

    override fun hasValidArguments(): Boolean = (i2choRatioProfileTemplateNumber!=null)
}