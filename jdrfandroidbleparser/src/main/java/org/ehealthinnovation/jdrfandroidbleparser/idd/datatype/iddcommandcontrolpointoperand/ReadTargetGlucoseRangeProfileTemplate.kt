package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter

class ReadTargetGlucoseRangeProfileTemplate:BaseOperandWriter {

    override val tag = ReadTargetGlucoseRangeProfileTemplate::class.java.canonicalName as String

    constructor(): super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var targetGlucoseRangeProfileTemplateNumber:Int?= null

    override fun compose(): Boolean {
        if (!hasValidArguments()){
            return false
        }

        targetGlucoseRangeProfileTemplateNumber?.let { setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT8) }
        return true

    }

    override fun hasValidArguments(): Boolean = (targetGlucoseRangeProfileTemplateNumber !=null)
}