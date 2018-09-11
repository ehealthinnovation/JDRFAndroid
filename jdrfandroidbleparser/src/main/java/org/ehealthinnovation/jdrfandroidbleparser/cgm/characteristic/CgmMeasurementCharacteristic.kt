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

    /**
     * The Flags Field indicates the presence of optional fields and the Sensor Status Annunciation
     * Field in the CGM Measurement record.
     */
    var flags: EnumSet<Flags>? = null

    /**
     *  The CGM Glucose Concentration field contains the CGM glucose concentration in a SFLOAT data
     *  type. The SFLOAT-Type is a 16-bit word comprising a signed 4-bit integer exponent followed
     *  by a signed 12-bit Mantissa.
     *
     *  Note that the unit of the CGM Glucose concentration is in mg/dL only.
     */
    var cgmGlucoseConcentration: Float? = null

    /**
     * The Time Offset field is used in conjunction with the CGM Session Start Time to represent
     * the time difference of the separate CGM measurements in relation to the session start time.
     * Note that this Time Offset field serves also as a sequence number for the client, allowing
     * the client to identify gaps / missing results in the data stream.
     *
     * The Time Offset field is defined as a UINT 16 representing the number of minutes the user-facing
     * time differs from the Session Start Time. The default initial value shall be 0x0000. The Time
     * Offset field shall be incremented by the measurement interval with each CGM measurement record
     */
    var timeOffset: Int? = null

    /**
     * The Sensor Status Annunciation field may form part of the CGM Measurement record. This field
     * has a variable length between 1 and 3 octets. If one or more bits in the Sensor Status
     * Annunciation field are set to “1” the Sensor Status Annunciation field shall form part of
     * every CGM Measurement Record to which it applies.
     */
    var sensorStatusAnnunciation: EnumSet<SensorStatusAnnunciation>? = null

    /**
     * If the device supports CGM Trend information (CGM-Trend-Information Supported bit is set in
     * CGM Features), this field may be included in the record. The presence of this field in a
     * record is indicated by the Flags Field. This field contains the CGM Trend information in
     * (mg/dL)/min as an SFLOAT data type.
     */
    var cgmTrendInformation: Float? = null

    /**
     * If the device supports CGM Quality (CGM-Quality Supported bit is set in CGM Features), this
     * field may be included in the CGM measurement record. The presence of this field in a CGM
     * measurement record is indicated by the Flags Field. This field contains the CGM Quality
     * information in % as an SFLOAT data type
     */
    var cgmQuality: Float? = null

    /**
     * If the device supports E2E-safety (E2E-CRC Supported bit is set in CGM Features), the
     * measurement shall be protected by a CRC calculated over all fields
     */
    val hasCrc = hasCrc



    override fun parse(c: BluetoothGattCharacteristic): Boolean {
        var errorFreeParsing = false
        var sizeCounter = 0
        size = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)

        Flags.parseFlags(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)).let {
            flags = it
            cgmGlucoseConcentration = getNextFloatValue(c, BluetoothGattCharacteristic.FORMAT_SFLOAT)
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