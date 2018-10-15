package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.SetBolusTemplateFlags
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter
import java.util.*

class SetBolusTemplate : BaseOperandWriter {

    override val tag = SetBolusTemplate ::class.java.canonicalName as String

    constructor(): super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var bolusTemplateNumber: Int? = null
    var flags: EnumSet<SetBolusTemplateFlags>?=null
    var bolus: Bolus? =null
    var bolusDelayTime: Int? = null

    override fun compose(): Boolean {
        if (!hasValidArguments()) {
            return false
        }

        bolusTemplateNumber?.let { setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT8) }
        flags?.let {
            setIntValue(SetBolusTemplateFlags.composeFlags(it), BluetoothGattCharacteristic.FORMAT_UINT8)
            bolus?.let { bolus ->
                if (!bolus.compose()) {
                    return false
                }
                setByteArray(bolus.rawData)
            }
            if (it.contains(SetBolusTemplateFlags.BOLUS_DELAY_TIME_PRESENT)) {
                bolusDelayTime?.let {
                    setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT16)
                }
            }
        }
        return true
    }

    override fun hasValidArguments(): Boolean {
        if(bolusTemplateNumber == null || flags==null || bolus == null){
            return false
        }

        flags?.let {
            if (it.contains(SetBolusTemplateFlags.BOLUS_DELAY_TIME_PRESENT)){
                if (bolusDelayTime==null){
                    return false
                }
            }
        }
        return true
    }


}