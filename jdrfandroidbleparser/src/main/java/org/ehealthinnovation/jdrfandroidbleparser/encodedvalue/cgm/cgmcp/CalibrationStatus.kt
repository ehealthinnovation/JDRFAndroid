package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.cgmcp

import java.util.*

enum class CalibrationStatus constructor(val bit: Int) {
    CALIBRATION_DATA_REJECTED(1 shl 0),
    CALIBRATION_DATA_OUT_OF_RANGE(1 shl 1),
    CALIBRATION_PROCESS_PENDING(1 shl 2);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[CalibrationStatus]> of set properties
         */
        fun parseFlags(calibrationStatusFlag: Int): EnumSet<CalibrationStatus> {
            val setFlags = EnumSet.noneOf(CalibrationStatus::class.java)
            CalibrationStatus.values().forEach {
                val flag = it.bit
                if (flag and calibrationStatusFlag == flag) setFlags.add(it)
            }
            return setFlags
        }

        fun composeFlags(flags: MutableSet<CalibrationStatus>) :Int {
            var output = 0
            for (flag in flags){
                output += flag.bit
            }
            return output
        }
    }

}