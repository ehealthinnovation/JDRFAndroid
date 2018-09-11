package org.ehealthinnovation.jdrfandroidbleparser.cgm.characteristic

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.measurement.Flags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.measurement.SensorStatusAnnunciation
import java.util.*

/**
 * The CGM Measurement characteristic is a variable length structure containing one or more CGM
 * Measurement records, each comprising a Size field, a Flags Field, a Glucose Concentration field,
 * a Time Offset field, a Sensor Status Annunciation field (optional), a CGM Trend Information Field
 * (optional), a CGM Quality Field (optional), and an E2E-CRC Field (mandatory if this feature is
 * supported).
 * https://www.bluetooth.com/specifications/gatt/viewer?attributeXmlFile=org.bluetooth.characteristic.cgm_measurement.xml
 */
class CgmMeasurementCharacteristic(c :BluetoothGattCharacteristic, hasCrc : Boolean = false) : BaseCharacteristic(c, GattCharacteristic.CGM_MEASUREMENT.assigned, hasCrc) {
    override val tag: String = CgmMeasurementCharacteristic::class.java.canonicalName

    /**
     * The Size Field represents the size of the CGM Measurement record in an UINT 8. The minimum
     * size is 6 octets and is enlarged if more octets are indicated by the Flags Field (Sensor
     * Status Annunciation Field, CGM Trend Information Field and CGM Quality Field) and the
     * optional E2E-CRC Field. The Size Field itself is included in the length calculation.
     */
    var size: Int? = null


    var flags: EnumSet<Flags>? = null
    var cgmGlucoseConcentration: Float? = null
    var timeOffset: Int? = null
    var sensorStatusAnnunciation: EnumSet<SensorStatusAnnunciation>? = null
    var cgmTrendInformation: Float? = null
    var cgmQuality: Float? = null
    val hasCrc = hasCrc



    override fun parse(c: BluetoothGattCharacteristic): Boolean {
        var errorFreeParsing = false
        var sizeCounter = 0
        size = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)

        Flags.parseFlags(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)).let {
            flags = it
            cgmGlucoseConcentration = getNextFloatValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
            timeOffset = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
            var annunciationFlagHolder: Int = 0
            if (it.contains(Flags.SENSOR_STATUS_ANNUNCIATION_FIELD_STATUS_OCTET_PRESENT)) {
                annunciationFlagHolder = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)
            }

            if (it.contains(Flags.SENSOR_STATUS_ANNUNCIATION_FIELD_CALTEMP_OCTET_PRESENT)) {
                annunciationFlagHolder += getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8) shl 8
            }

            if (it.contains(Flags.SENSOR_STATUS_ANNUNCIATION_FIELD_WARNING_OCTET_PRESENT)) {
                annunciationFlagHolder += getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8) shl 16
            }

            sensorStatusAnnunciation = SensorStatusAnnunciation.parseFlags(annunciationFlagHolder)


            if (it.contains(Flags.CGM_TREND_INFORMATION_PRESENT)) {
                cgmTrendInformation = getNextFloatValue(c, BluetoothGattCharacteristic.FORMAT_SFLOAT)
            }

            if (it.contains(Flags.CGM_QUALITY_PRESENT)) {
                cgmQuality = getNextFloatValue(c, BluetoothGattCharacteristic.FORMAT_SFLOAT)
            }

        }

        errorFreeParsing =  let{ if(hasCrc) {2} else {0}} + offset == size
        return errorFreeParsing
    }
}