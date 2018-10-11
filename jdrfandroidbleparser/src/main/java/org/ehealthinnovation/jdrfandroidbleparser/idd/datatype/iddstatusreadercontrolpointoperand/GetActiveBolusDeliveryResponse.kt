package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.BolusType
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.BolusActivationType
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.GetActiveBolusDeliveryResponseFlags
import java.util.*

/**
 * If the Get Active Bolus Delivery Op Code is written to the IDD Status Reader Control Point with
 * an Operand containing a Bolus ID and a Bolus Value Selection field, the Server shall look up the
 * corresponding Active Bolus and indicate the IDD Status Reader Control Point with a Get Active
 * Bolus Delivery Response Op Code and an Active Bolus Delivery record.
 */
class GetActiveBolusDeliveryResponse : BaseOperandParser {


    override val tag = ActiveBolusIDs::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic): super(rawData, c)

    var flags: EnumSet<GetActiveBolusDeliveryResponseFlags>? = null
    var bolusId: Int? = null
    var bolusType: BolusType? = null
    var bolusFastAmount: Float? = null
    var bolusExtendedAmount: Float? = null
    var bolusDuration: Int? = null
    var bolusDelayTime: Int? = null
    var bolusTemplateNumber: Int? = null
    var bolusActivationType: BolusActivationType? = null

    override fun parse(): Boolean {
        flags = GetActiveBolusDeliveryResponseFlags.parseFlags(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        bolusId = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
        bolusType = BolusType.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        bolusFastAmount = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        bolusExtendedAmount = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        bolusDuration = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
        flags?.let { flags->
           if (flags.contains(GetActiveBolusDeliveryResponseFlags.BOLUS_DELAY_TIME_PRESENT)){
               bolusDelayTime = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
           }

            if(flags.contains(GetActiveBolusDeliveryResponseFlags.BOLUS_TEMPLATE_NUMBER_PRESENT)){
               bolusTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
            }

            if(flags.contains(GetActiveBolusDeliveryResponseFlags.BOLUS_ACTIVATION_TYPE_PRESENT)){
                bolusActivationType = BolusActivationType.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
            }
           }
        return true
    }
}