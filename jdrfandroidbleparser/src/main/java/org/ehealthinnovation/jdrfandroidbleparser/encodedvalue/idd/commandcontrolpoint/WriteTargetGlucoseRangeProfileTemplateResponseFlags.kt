package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint

import java.util.*

enum class WriteTargetGlucoseRangeProfileTemplateResponseFlags constructor(val bit: Int) {

    /**If this bit is set, the target glucose range profile passed the plausibility checks of the Server and was written successfully (i.e., the write transaction of all time blocks of the target glucose range profile completed). */
    TRANSACTION_COMPLETED(1 shl 0);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<WriteTargetGlucoseRangeProfileTemplateResponseFlags> {
            val setFlags = EnumSet.noneOf(WriteTargetGlucoseRangeProfileTemplateResponseFlags::class.java)
            WriteTargetGlucoseRangeProfileTemplateResponseFlags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}