package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint

import java.util.*

enum class WriteTargetGlucoseRangeProfileTemplateFlags constructor(val bit: Int) {

    /**If this bit is set, it indicates to the Server that all time blocks of the target glucose range profile have been sent (i.e., the target glucose range profile is complete). It shall only be set if the last time blocks of the target glucose range profile are sent. */
    END_TRANSACTION(1 shl 0),
    /**If this bit is set, the fields Second Duration, Second Lower Target Glucose Limit, and Second Upper Target Glucose Limit are present. */
    SECOND_TIME_BLOCK_PRESENT(1 shl 1);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<WriteTargetGlucoseRangeProfileTemplateFlags> {
            val setFlags = EnumSet.noneOf(WriteTargetGlucoseRangeProfileTemplateFlags::class.java)
            WriteTargetGlucoseRangeProfileTemplateFlags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }

        fun composeFlags(flags: EnumSet<WriteTargetGlucoseRangeProfileTemplateFlags>) :Int {
            var output = 0
            for (flag in flags){
                output += flag.bit
            }
            return output
        }


    }
}