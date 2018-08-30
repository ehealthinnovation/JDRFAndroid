package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.measurement

import java.util.*

enum class SensorStatusAnnunciation constructor(val bit: Int) {
    SESSION_STOPPED(1 shl 0),
    DEVICE_BATTERY_LOW(1 shl 1),
    SENSOR_TYPE_INCORRECT_FOR_DEVICE(1 shl 2),
    SENSOR_MALFUNCTION(1 shl 3),
    DEVICE_SPECIFIC_ALERT(1 shl 4),
    GENERAL_DEVICE_FAULT_HAS_OCCURRED_IN_THE_SENSOR(1 shl 5),
    TIME_SYNCHRONIZATION_BETWEEN_SENSOR_AND_COLLECTOR_REQUIRED(1 shl 8),
    CALIBRATION_NOT_ALLOWED(1 shl 9),
    CALIBRATION_RECOMMENDED(1 shl 10),
    CALIBRATION_REQUIRED(1 shl 11),
    SENSOR_TEMPERATURE_TOO_HIGH_FOR_VALID_RESULT_AT_TIME_OF_MEASUREMENT(1 shl 12),
    SENSOR_TEMPERATURE_TOO_LOW_FOR_VALID_RESULT_AT_TIME_OF_MEASUREMENT(1 shl 13),
    SENSOR_RESULT_LOWER_THAN_THE_PATIENT_LOW_LEVEL(1 shl 16),
    SENSOR_RESULT_HIGHER_THAN_THE_PATIENT_HIGH_LEVEL(1 shl 17),
    SENSOR_RESULT_LOWER_THAN_THE_HYPO_LEVEL(1 shl 18),
    SENSOR_RESULT_HIGHER_THAN_THE_HYPER_LEVEL(1 shl 19),
    SENSOR_RATE_OF_DECREASE_EXCEEDED(1 shl 20),
    SENSOR_RATE_OF_INCREASE_EXCEEDED(1 shl 21),
    SENSOR_RESULT_LOWER_THAN_THE_DEVICE_CAN_PROCESS(1 shl 22),
    SENSOR_RESULT_HIGHER_THAN_THE_DEVICE_CAN_PROCESS(1 shl 23);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[SensorStatusAnnunciation]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<SensorStatusAnnunciation> {
            val setFlags = EnumSet.noneOf(SensorStatusAnnunciation::class.java)
            SensorStatusAnnunciation.values().forEach {
                val flag = it.bit
                if(flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}