package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.measurement

import java.util.*

enum class Flags constructor(val bit: Int) {
    CGM_TREND_INFORMATION_PRESENT(1 shl 0),
    CGM_QUALITY_PRESENT(1 shl 1),
    SENSOR_STATUS_ANNUNCIATION_FIELD_WARNING_OCTET_PRESENT(1 shl 5),
    SENSOR_STATUS_ANNUNCIATION_FIELD_CALTEMP_OCTET_PRESENT(1 shl 6),
    SENSOR_STATUS_ANNUNCIATION_FIELD_STATUS_OCTET_PRESENT(1 shl 7);

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
            return setFlags
        }
    }
}