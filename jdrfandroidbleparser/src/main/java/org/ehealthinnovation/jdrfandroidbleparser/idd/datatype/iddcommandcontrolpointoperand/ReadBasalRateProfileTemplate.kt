package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter

class ReadBasalRateProfileTemplate : BaseOperandWriter {

    override val tag = ReadBasalRateProfileTemplate::class.java.canonicalName as String

    constructor() : super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var basalRateProfileTemplateNumber: Int? = null

    override fun compose(): Boolean {
        if (!hasValidArguments()) {
            return false
        }
        basalRateProfileTemplateNumber?.let { setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT8) }
        return true
    }

    override fun hasValidArguments(): Boolean = basalRateProfileTemplateNumber != null

}