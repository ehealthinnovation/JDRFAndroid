package org.ehealthinnovation.jdrfandroidbleparser.cgm.characteristic

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.measurement.SensorStatusAnnunciation
import java.util.*

/**
 * The CGM Status allows the client to actively request the status from the CGM Sensor, particularly
 * when the CGM measurement is not running and the status cannot be given in the measurement result
 * in the Status Annunciation Field.
 * https://www.bluetooth.com/specifications/gatt/viewer?attributeXmlFile=org.bluetooth.characteristic.cgm_status.xml
 */
class CgmStatusCharacteristic(c : BluetoothGattCharacteristic, hasCrc : Boolean = false) : BaseCharacteristic(c, GattCharacteristic.CGM_STATUS.assigned, hasCrc){


    override val tag = CgmStatusCharacteristic::class.java.canonicalName as String

    /**
     * The Time Offset Field specifies the actual relative time difference to the session start time.
     */
    var timeOffset : Int? = null

    /**
     * The structure of the CGM Status Field shall be identical to the structure of the Status
     * Annunciation Field, as defined in the CGM Measurement Characteristic "Sensor Status
     * Annunciation Field". It always consists of three octets regardless the value.
     */
    var cgmStatus : EnumSet<SensorStatusAnnunciation>? = null


    override fun parse(c: BluetoothGattCharacteristic, hasE2eCounter : Boolean): Boolean {
        var errorFreeParse = false
        timeOffset = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)

        //The structure for the status field is 24 bit, non-standard integer size
        var statusFlagHolder : Int
        statusFlagHolder = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)
        statusFlagHolder += getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8) shl 8
        statusFlagHolder += getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8) shl 16

        cgmStatus = SensorStatusAnnunciation.parseFlags(statusFlagHolder)

        errorFreeParse = true

        return errorFreeParse
    }


}