package org.ehealthinnovation.jdrfandroidbleparser.cgm.characteristic

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.sessionstarttime.DstOffset
import org.ehealthinnovation.jdrfandroidbleparser.utility.BluetoothDateTime

/**
 * The Session Start Time Field defines the time of the initial CGM measurement. The absolute time
 * of the first CGM measurement taken is not known, so the Server stores each CGM measurement with
 * a relative time stamp (Time Offset), starting with 0 for the first measurement (Session Start).
 * https://www.bluetooth.com/specifications/gatt/viewer?attributeXmlFile=org.bluetooth.characteristic.cgm_session_start_time.xml
 */
class CgmSessionStartTime(c : BluetoothGattCharacteristic, hasCrc : Boolean = false) : BaseCharacteristic(c, GattCharacteristic.CGM_SESSION_START_TIME.assigned, hasCrc) {

    override val tag = CgmSessionStartTime::class.java.canonicalName as String

    /**
     * Upon initial connection, if the device supports an automatic start of the CGM session (e.g.,
     * at power on), or after the Start Session procedure, the Client shall write its current time
     * to this characteristic and the Server shall calculate and store the Session Start Time using
     * the time of the client and its own current relative time value.
     */
     var sessionStartTime : BluetoothDateTime? = null

    /**
     * To know an absolute Time, it is necessary to know the Time Zone to which the Session  Start
     * Time is related to. If unknown, the field shall be set to a value of -128. See definition of
     * Time Zone Characteristic in [3].
     */
     var timeZone : Int? = null

    /**
     * To know an absolute Time, it is also necessary to know the Daylight Saving setting.
     * If unknown, the field shall be set to a value of 255.
     */
     var dstOffset : DstOffset? = null


    override fun parse(c: BluetoothGattCharacteristic): Boolean {
        var errorFreeParse = false
        sessionStartTime = BluetoothDateTime(
                _year = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16),
                _month = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8),
                _day = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8),
                _hour = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8),
                _min = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8),
                _sec = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)
        )
        timeZone = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_SINT8)
        dstOffset = DstOffset.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_SINT8))
        errorFreeParse = true
        return errorFreeParse
    }
}