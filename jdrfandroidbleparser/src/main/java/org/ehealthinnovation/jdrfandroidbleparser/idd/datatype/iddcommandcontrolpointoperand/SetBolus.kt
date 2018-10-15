package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import android.location.SettingInjectorService
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.feature.Flags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.SetBolusFlags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.BolusActivationType
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter
import java.util.*

class SetBolus :BaseOperandWriter {

    override val tag = SetBolus ::class.java.canonicalName as String

    constructor(): super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var flags: EnumSet<SetBolusFlags>? = null
    var bolus: Bolus? = null
    var bolusDelayTime:Int? = null
    var bolusTemplateNumber:Int? =null
    var bolusActivationType: BolusActivationType? = null


    override fun compose(): Boolean {
        if(!hasValidArguments()){
            return false
        }
        flags?.let {
            setIntValue(SetBolusFlags.composeFlags(it), BluetoothGattCharacteristic.FORMAT_UINT8)
            bolus?.let { bolus ->
                if(!bolus.compose()){
                    return false
                }
                setByteArray(bolus.rawData)
            }
            if( it.contains(SetBolusFlags.BOLUS_DELAY_TIME_PRESENT)){
                bolusDelayTime?.let{setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT16)}
            }
            if (it.contains(SetBolusFlags.BOLUS_TEMPLATE_NUMBER_PRESENT)){
                bolusTemplateNumber?.let { setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT8) }
            }
            if (it.contains(SetBolusFlags.BOLUS_ACTIVATION_TYPE_PRESENT)){
                bolusActivationType?.let { setIntValue(it.key, BluetoothGattCharacteristic.FORMAT_UINT8) }
            }
        }
        return true
    }

    override fun hasValidArguments(): Boolean {
        if(flags==null || bolus == null){
            return false
        }

        flags?.let {
            if (it.contains(SetBolusFlags.BOLUS_DELAY_TIME_PRESENT)){
                if (bolusDelayTime == null){
                    return false
                }
            }
            if (it.contains(SetBolusFlags.BOLUS_TEMPLATE_NUMBER_PRESENT)){
                if (bolusTemplateNumber == null){
                    return false
                }
            }

            if (it.contains(SetBolusFlags.BOLUS_ACTIVATION_TYPE_PRESENT)){
                if (bolusActivationType == null){
                    return false
                }
            }
        }
        return true
    }
}