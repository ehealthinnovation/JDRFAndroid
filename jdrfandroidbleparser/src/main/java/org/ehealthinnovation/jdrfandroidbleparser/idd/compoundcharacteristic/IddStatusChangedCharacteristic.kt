package org.ehealthinnovation.jdrfandroidbleparser.idd.compoundcharacteristic

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic

/**
 * It is left to the implementation of the Server Application when status changes related to an
insulin delivery (Reservoir Remaining Amount, Total Daily Insulin Status) are triggered. For
example, basal insulin is delivered within a time block (defined by the basal rate profile), but
depending on the specific implementation, the actual delivery may be distributed over several
points in time within that block to provide a continuous basal insulin flow.
The Server shall retain the status of a bit of the Flags field until its value is reset by the Collector
 */
class IddStatusChangedCharacteristic(characteristic: BluetoothGattCharacteristic?) :BaseCharacteristic(characteristic, GattCharacteristic.){
}