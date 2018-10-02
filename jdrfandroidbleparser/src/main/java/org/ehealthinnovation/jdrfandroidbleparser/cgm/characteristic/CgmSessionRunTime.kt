package org.ehealthinnovation.jdrfandroidbleparser.cgm.characteristic

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic

/**
 * The CGM Session Run Time shall define the expected run time of the CGM session. Typically CGM
 * Sensors have a limited run time which they are approved for. However, this characteristic should
 * enable a prediction of the run time depending on physiological effects in future devices.
 * https://www.bluetooth.com/specifications/gatt/viewer?attributeXmlFile=org.bluetooth.characteristic.cgm_session_run_time.xml
 */
class CgmSessionRunTime(characteristic: BluetoothGattCharacteristic?, hasCrc: Boolean) : BaseCharacteristic(characteristic, GattCharacteristic.CGM_SESSION_RUN_TIME.assigned, hasCrc) {

    override val tag = CgmSessionRunTime::class.java.canonicalName as String

    /**
     * The CGM Session Run Time is a relative time, based on the CGM Session Start Time.
     * The unit is in hour
     */
    var sessionRunTime : Int? = null

    override fun parse(c: BluetoothGattCharacteristic, hasE2eCounter : Boolean): Boolean {
        var errorFreeParse = false
        sessionRunTime = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
        errorFreeParse = sessionRunTime != null
        return errorFreeParse
    }


}
