package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.SetTBRAdjustmentFlags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.TBRDeliveryContext
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.TBRType
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter
import java.util.*

class SetTBRAdjustment: BaseOperandWriter {

    override val tag = SetTBRAdjustment::class.java.canonicalName as String

    constructor(): super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var flags: EnumSet<SetTBRAdjustmentFlags>? = null
    var tbrType : TBRType? = null
    var tbrAdjustmentValue: Float? = null
    var tbrDuration: Int? = null
    var tbrTemplateNumber: Int? = null
    var tbrDeliveryContext: TBRDeliveryContext? = null

    override fun compose(): Boolean {
        if( !hasValidArguments()){
            return false
        }
        flags?.let {
            setIntValue(SetTBRAdjustmentFlags.composeFlags(it), BluetoothGattCharacteristic.FORMAT_UINT8)
            tbrType?.let{
                setIntValue(it.key, BluetoothGattCharacteristic.FORMAT_UINT8)
            }
            tbrAdjustmentValue?.let {
                setFloatValue((it*10).toInt(), -1, BluetoothGattCharacteristic.FORMAT_SFLOAT)
            }
            tbrDuration?.let {
                setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT16)
            }
            if(it.contains(SetTBRAdjustmentFlags.TBR_TEMPLATE_NUMBER_PRESENT)){
                tbrTemplateNumber?.let { setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT8) }
            }
            if(it.contains(SetTBRAdjustmentFlags.TBR_DELIVERY_CONTEXT_PRESENT)){
                tbrDeliveryContext?.let { setIntValue(it.key, BluetoothGattCharacteristic.FORMAT_UINT8) }
            }
        }
        return true
    }

    override fun hasValidArguments(): Boolean {
        if(flags == null || tbrType == null || tbrAdjustmentValue == null || tbrDuration == null){
            return false
        }
        flags?.let {
            if (it.contains(SetTBRAdjustmentFlags.TBR_TEMPLATE_NUMBER_PRESENT).xor(tbrTemplateNumber == null)){
                return false
            }

            if (it.contains(SetTBRAdjustmentFlags.TBR_DELIVERY_CONTEXT_PRESENT).xor(tbrDeliveryContext==null)){
                return false
            }
        }
        return true
    }
}