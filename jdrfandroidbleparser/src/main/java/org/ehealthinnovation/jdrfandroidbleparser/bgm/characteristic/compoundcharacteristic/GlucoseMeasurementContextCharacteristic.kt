package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.Units
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.contextmeasurement.*
import java.util.*

/**
 * The Glucose Measurement Context characteristic is a variable length structure containing a Flags
 * field, a Sequence Number field and, based upon the contents of the Flags field, may contain a
 * Carbohydrate ID field, Carbohydrate field, Meal field, Tester-Health field, Exercise Duration
 * field, Exercise Intensity field, Medication ID field, Medication field and a HbA1c field.
 *
 * https://www.bluetooth.com/specifications/gatt/viewer?attributeXmlFile=org.bluetooth.characteristic.glucose_measurement_context.xml
 *
 * @author markiantorno
 */
class GlucoseMeasurementContextCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        BaseCharacteristic(characteristic, GattCharacteristic.GLUCOSE_MEASUREMENT_CONTEXT.assigned) {

    override val tag = GlucoseMeasurementContextCharacteristic::class.java.canonicalName as String

    // Sequence Number
    var sequenceNumber: Int? = null
    // Field exists if the key of bit 7 of the Flags field is set to 1
    var extendedFlag: Int? = null
    // Carbohydrate type, ie: Breakfast, Lunch, etc. Field exists if the key of bit 0 of the Flags field is set to 1
    var carbohydrateId: CarbohydrateId? = null
    // Carbohydrate amount in kg. Field exists if the key of bit 0 of the Flags field is set to 1
    var carbohydrate: Float? = null
    // Field exists if the key of bit 1 of the Flags field is set to 1
    var meal: Meal? = null
    // Field exists if the key of bit 2 of the Flags field is set to 1
    var tester: Tester? = null
    // Field exists if the key of bit 2 of the Flags field is set to 1
    var health: Health? = null
    // Excercise duration in seconds. Field exists if the key of bit 3 of the Flags field is set to 1.
    var exerciseDuration: Int? = null
    // Intensity as percentage. Field exists if the key of bit 3 of the Flags field is set to 1
    var exerciseIntensity: Float? = null
    // Insulin type. Field exists if the key of bit 4 of the Flags field is set to 1
    var medicationId: MedicationId? = null
    // Unit of measured medication, either kg (mass), or L (volume)
    var medicationUnit: Units? = null
    // Numerical measure of medication
    var medicationAmount: Float? = null
    // As percentage. Field exists if the key of bit 6 of the Flags field is set to 1
    var HbA1c: Float? = null

    var c: Calendar? = null

    override fun parse(c: BluetoothGattCharacteristic): Boolean {
        var errorFreeParse = false
        // These flags define which data fields are present in the Characteristic value
        Flags.parseFlags(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)).let {
            sequenceNumber = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
            if (it.contains(Flags.EXTENDED_FLAGS_PRESENT)) {
                extendedFlag = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)
            }
            if (it.contains(Flags.CARBOHYDRATE_ID_AND_CARBOHYDRATE_PRESENT)) {
                carbohydrateId = CarbohydrateId.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
                carbohydrate = getNextFloatValue(c, BluetoothGattCharacteristic.FORMAT_SFLOAT)
            }
            if (it.contains(Flags.MEAL_PRESENT)) {
                meal = Meal.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
            }
            if (it.contains(Flags.TESTER_HEALTH_PRESENT)) getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8).let {
                tester = Tester.fromKey(it and 0x0F)
                health = Health.fromKey((it and 0xF0) shr 4)
            }
            if (it.contains(Flags.EXERCISE_DURATION_AND_EXERCISE_INTENSITY_PRESENT)) {
                exerciseDuration = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                exerciseIntensity = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8) / 100f
            }
            if (it.contains(Flags.MEDICATION_ID_AND_MEDICATION_PRESENT)) {
                medicationId = MedicationId.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
                medicationAmount = getNextFloatValue(c, BluetoothGattCharacteristic.FORMAT_SFLOAT)
            }
            medicationUnit = when {
                it.contains(Flags.MEDICATION_VALUE_UNITS) -> Units.LITRE
                else -> Units.KILOGRAM
            }
            if (it.contains(Flags.HBA1C_PRESENT)) {
                HbA1c = getNextFloatValue(c, BluetoothGattCharacteristic.FORMAT_SFLOAT)
            }
            errorFreeParse = true
        }
        return errorFreeParse
    }
}