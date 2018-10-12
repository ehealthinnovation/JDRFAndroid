package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter

class ConfirmAnnunciation: BaseOperandWriter {

    override val tag = ConfirmAnnunciation::class.java.canonicalName as String

    constructor(): super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var annunciationInstanceId: Int? = null

    override fun hasValidArguments(): Boolean = annunciationInstanceId!=null

    override fun compose(): Boolean {
        if(!hasValidArguments()){
            return false
        }
        annunciationInstanceId?.let { setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT16) }
        return true
    }
}