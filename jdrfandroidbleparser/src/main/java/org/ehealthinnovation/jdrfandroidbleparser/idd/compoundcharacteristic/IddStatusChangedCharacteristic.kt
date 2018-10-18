package org.ehealthinnovation.jdrfandroidbleparser.idd.compoundcharacteristic

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statuschanged.Flags
import java.util.*

/**
 * It is left to the implementation of the Server Application when status changes related to an
insulin delivery (Reservoir Remaining Amount, Total Daily Insulin Status) are triggered. For
example, basal insulin is delivered within a time block (defined by the basal rate profile), but
depending on the specific implementation, the actual delivery may be distributed over several
points in time within that block to provide a continuous basal insulin flow.
The Server shall retain the status of a bit of the Flags field until its value is reset by the Collector
 */
class IddStatusChangedCharacteristic(characteristic: BluetoothGattCharacteristic?, hasCrc: Boolean = false, hasE2eCounter: Boolean) : BaseCharacteristic(characteristic, GattCharacteristic.IDD_STATUS_CHANGED.assigned, hasCrc, hasE2eCounter = hasE2eCounter) {
    override val tag = IddStatusChangedCharacteristic::class.java.canonicalName as String

    /**
     * The set of flags indicating which status has changed since the last reset
     */
    var flag: EnumSet<Flags>? = null

    /**
     * The E2E-Counter field, when used correctly, provides that messages are received in the correct
    order. This field contains its own uint8 counter. There are two different types of E2E-Counter
    values that can be included within the E2E-Counter field: a transmit E2E-Counter value that is
    sent by the Server and a received E2E-Counter value from the Client.
    The Server shall increment the transmit E2E-Counter value of a characteristic with each read
    response, indication, and notification of that characteristic. In addition, the Server shall
    increment the transmit E2E-Counter value of a control point with each indicated response to an
    executed control point procedure.
     */
    var e2eCounter: Int? = null

    override fun parse(c: BluetoothGattCharacteristic, hasE2eCounter: Boolean): Boolean {
        var errorFreeParse = false
        var flagValue: Int? = 0
        flagValue = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
        flagValue?.let {
            flag = Flags.parseFlags(it)
            if (hasE2eCounter == true) {
                e2eCounter = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)
            }
            errorFreeParse = true
        }
        return errorFreeParse
    }
}