package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.TBRType
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter

class SetTBRTemplate : BaseOperandWriter {

    override val tag = SetTBRTemplate::class.java.canonicalName as String

    constructor() : super()

    constructor(c: BluetoothGattCharacteristic) : super(c)


    var tbrTemplateNumber: Int? = null
    var tbrType: TBRType? = null
    var tbrAdjustmentValue: Float? = null
    var tbrDuration: Int? = null

    override fun compose(): Boolean {
        if (!hasValidArguments()) {
            return false
        }
        tbrTemplateNumber?.let { setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT8) }
        tbrType?.let { setIntValue(it.key, BluetoothGattCharacteristic.FORMAT_UINT8) }
        //todo make sure exponent is -1
        tbrAdjustmentValue?.let { setFloatValue((it * 10).toInt(), -1, BluetoothGattCharacteristic.FORMAT_SFLOAT) }
        tbrDuration?.let { setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT16) }
        return true
    }

    override fun hasValidArguments(): Boolean = (tbrAdjustmentValue != null && tbrType != null && tbrTemplateNumber != null && tbrDuration != null)
}
