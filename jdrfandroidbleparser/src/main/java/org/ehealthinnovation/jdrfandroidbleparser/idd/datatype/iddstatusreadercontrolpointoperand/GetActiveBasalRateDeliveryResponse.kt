package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.BasalDeliveryContext
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.GetActiveBasalRateDeliveryResponseFlags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.TBRType
import java.util.*


class GetActiveBasalRateDeliveryResponse: BaseOperandParser {
      override val tag = GetActiveBasalRateDeliveryResponse::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic): super(rawData, c)

    var flags: EnumSet<GetActiveBasalRateDeliveryResponseFlags>? = null
    var activeBasalRateProfileTemplateNumber: Int? = null
    var activeBasalRateCurrentConfigValue: Float? = null
    var tbrType: TBRType? = null
    var tbrAdjustmentValue: Float? = null
    var tbrDurationProgrammed: Int? = null
    var tbrDurationRemaining: Int? = null
    var tbrTemplateNumber: Int? = null
    var basalDeliveryContext: BasalDeliveryContext? = null

    override fun parse(): Boolean {
        flags = GetActiveBasalRateDeliveryResponseFlags.parseFlags(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        activeBasalRateProfileTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        activeBasalRateCurrentConfigValue = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        flags?.let { flags->
           if(flags.contains(GetActiveBasalRateDeliveryResponseFlags.TBR_PRESENT)){
               tbrType = TBRType.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
               tbrAdjustmentValue = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
               tbrDurationProgrammed = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
               tbrDurationRemaining = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
           }
            if(flags.contains(GetActiveBasalRateDeliveryResponseFlags.TBR_TEMPLATE_NUMBER_PRESENT)){
                tbrTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
            }
            if(flags.contains(GetActiveBasalRateDeliveryResponseFlags.BASAL_DELIVERY_CONTEXT_PRESENT)){
                basalDeliveryContext = BasalDeliveryContext.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
            }
        }
        return true
    }
}