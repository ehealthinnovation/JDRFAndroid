package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.sessionstarttime.DstOffset
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.DeliveredBasalRateChangedFlags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.RecordingReason
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.BasalDeliveryContext
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.utility.BluetoothDateTime
import java.util.*

class DeliveredBasalRateChanged : BaseOperandParser {

    override val tag = DeliveredBasalRateChanged::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var flags: EnumSet<DeliveredBasalRateChangedFlags>? = null
    var oldBasalRateValue: Float? = null
    var newBasalRateValue: Float? = null
    var basalDeliveryContext: BasalDeliveryContext? = null

    override fun parse(): Boolean {
        flags = DeliveredBasalRateChangedFlags.parseFlags(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        oldBasalRateValue = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        newBasalRateValue = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        flags?.let {
            if (it.contains(DeliveredBasalRateChangedFlags.BASAL_DELIVERY_CONTEXT_PRESENT)) {
                basalDeliveryContext = BasalDeliveryContext.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
            }
        }
        return true
    }
}