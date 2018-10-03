package org.ehealthinnovation.jdrfandroidbleparser.idd.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.status.Flags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.status.OperationalState
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.status.TherapyControlState
import java.util.*

class IddStatusCharacteristic(characteristic: BluetoothGattCharacteristic?, hasCrc: Boolean = false, hasE2eCounter : Boolean) :
        BaseCharacteristic(characteristic, GattCharacteristic.IDD_STATUS.assigned, hasCrc = hasCrc, hasE2eCounter = hasE2eCounter){
    override val tag = IddStatusCharacteristic::class.java.canonicalName as String

    /**
     * The Therapy Control State field describes the therapy state of the insulin delivery (e.g., stop,
pause, run).
     */
    var therapyControlState : TherapyControlState? = null

    /**
     * The Operational State field represents the operational state of the Insulin Delivery Device
     * in the context of running an insulin infusion therapy (e.g., priming).
     */
    var operationalState : OperationalState? = null

    /**
     * The Reservoir Remaining Amount field represents the remaining amount of insulin in the
     * reservoir in IU. If the Reservoir is not attached (i.e., Reservoir Attached bit of Flags
     * field is set to False), the Reservoir Remaining Amount shall be set to NaN.
     */
    var reservoirRemainingAmount: Float? = null

    /**
     * The Flags field exposes status bits of the Insulin Delivery Device (e.g., Reservoir Attached bit).
     */
    var flags: EnumSet<Flags>? = null

    /**
     * E2E-Counter
     */
    var e2eCounter: Int? = null

    override fun parse(c: BluetoothGattCharacteristic, hasE2eCounter: Boolean): Boolean {
       var errorFreeParse = false
        therapyControlState = TherapyControlState.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
        operationalState = OperationalState.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
        reservoirRemainingAmount = getNextFloatValue(c, BluetoothGattCharacteristic.FORMAT_SFLOAT)
        flags = Flags.parseFlags(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
        if(hasE2eCounter){
           e2eCounter = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)
        }
        if(therapyControlState == null || operationalState == null || reservoirRemainingAmount == null || (hasE2eCounter and (e2eCounter == null))){
            return errorFreeParse
        }
        errorFreeParse = true
        return errorFreeParse
    }
}