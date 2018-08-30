package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.feature

import java.util.*

enum class Flags constructor(val bit: Int) {
    CALIBRATION_SUPPORTED(1 shl 0),
    PATIENT_HIGH_LOW_ALERTS_SUPPORTED(1 shl 1),
    HYPO_ALERTS_SUPPORTED(1 shl 2),
    HYPER_ALERTS_SUPPORTED(1 shl 3),
    RATE_OF_INCREASE_DECREASE_ALERTS_SUPPORTED(1 shl 4),
    DEVICE_SPECIFIC_ALERT_SUPPORTED(1 shl 5),
    SENSOR_MALFUNCTION_DETECTION_SUPPORTED(1 shl 6),
    SENSOR_TEMPERATURE_HIGH_LOW_DETECTION_SUPPORTED(1 shl 7),
    SENSOR_RESULT_HIGH_LOW_DETECTION_SUPPORTED(1 shl 8),
    LOW_BATTERY_DETECTION_SUPPORTED(1 shl 9),
    SENSOR_TYPE_ERROR_DETECTION_SUPPORTED(1 shl 10),
    GENERAL_DEVICE_FAULT_SUPPORTED(1 shl 11),
    E2E_CRC_SUPPORTED(1 shl 12),
    MULTIPLE_BOND_SUPPORTED(1 shl 13),
    MULTIPLE_SESSIONS_SUPPORTED(1 shl 14),
    CGM_TREND_INFORMATION_SUPPORTED(1 shl 15),
    CGM_QUALITY_SUPPORTED(1 shl 16);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<Flags> {
            val setFlags = EnumSet.noneOf(Flags::class.java)
            Flags.values().forEach {
                val flag = it.bit
                if(flag and characteristicFlags == flag) setFlags.add(it)
            }
            return  setFlags
        }
    }
}