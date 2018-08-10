package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.feature

import java.util.*


enum class Flags constructor(val bit: Int) {
    LOW_BATTERY_DETECTION_DURING_MEASUREMENT_SUPPORTED(1 shl 0),
    SENSOR_MALFUNCTION_DETECTION_SUPPORTED(1 shl 1),
    SENSOR_SAMPLE_SIZE_SUPPORTED(1 shl 2),
    SENSOR_STRIP_INSERTION_ERROR_DETECTION_SUPPORTED(1 shl 3),
    SENSOR_STRIP_TYPE_ERROR_DETECTION_SUPPORTED(1 shl 4),
    SENSOR_RESULT_HIGH_LOW_DETECTION_SUPPORTED(1 shl 5),
    SENSOR_TEMPERATURE_HIGH_LOW_DETECTION_SUPPORTED(1 shl 6),
    SENSOR_READ_INTERRUPT_DETECTION_SUPPORTED(1 shl 7),
    GENERAL_DEVICE_FAULT_SUPPORTED(1 shl 8),
    TIME_FAULT_SUPPORTED(1 shl 9),
    MULTIPLE_BOND_SUPPORTED(1 shl 10);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<Flags> {
            val setFlags = EnumSet.noneOf(Flags::class.java)
            Flags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}