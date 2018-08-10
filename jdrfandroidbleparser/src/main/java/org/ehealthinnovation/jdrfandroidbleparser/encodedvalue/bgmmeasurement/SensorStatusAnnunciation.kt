package org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.bgmmeasurement

import java.util.*

enum class SensorStatusAnnunciation constructor(val bit: Int) {
    DEVICE_BATTERY_LOW_AT_TIME_OF_MEASUREMENT(1 shl 0),
    SENSOR_MALFUNCTION_OR_FAULTING_AT_TIME_OF_MEASUREMENT(1 shl 1),
    SAMPLE_SIZE_FOR_BLOOD_OR_CONTROL_SOLUTION_INSUFFICIENT_AT_TIME_OF_MEASUREMENT(1 shl 2),
    STRIP_INSERTION_ERROR(1 shl 3),
    STRIP_TYPE_INCORRECT_FOR_DEVICE(1 shl 4),
    SENSOR_RESULT_HIGHER_THAN_THE_DEVICE_CAN_PROCESS(1 shl 5),
    SENSOR_RESULT_LOWER_THAN_THE_DEVICE_CAN_PROCESS(1 shl 6),
    SENSOR_TEMPERATURE_TOO_HIGH_FOR_VALID_TEST_RESULT_AT_TIME_OF_MEASUREMENT(1 shl 7),
    SENSOR_TEMPERATURE_TOO_LOW_FOR_VALID_TEST_RESULT_AT_TIME_OF_MEASUREMENT(1 shl 8),
    SENSOR_READ_INTERRUPTED_BECAUSE_STRIP_WAS_PULLED_TOO_SOON_AT_TIME_OF_MEASUREMENT(1 shl 9),
    GENERAL_DEVICE_FAULT_HAS_OCCURRED_IN_THE_SENSOR(1 shl 10),
    TIME_FAULT_HAS_OCCURRED_IN_THE_SENSOR_AND_TIME_MAY_BE_INACCURATE(1 shl 11);

    companion object {
        fun parseFlags(flags: Int): EnumSet<SensorStatusAnnunciation> {
            val setFlags = EnumSet.noneOf(SensorStatusAnnunciation::class.java)
            SensorStatusAnnunciation.values().forEach {
                val flag = it.bit
                if (flag and flags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}