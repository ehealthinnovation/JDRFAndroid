package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint

import java.util.*

enum class WriteBasalRateProfileTemplateResponseFlags constructor(val bit: Int) {
    /**If this bit is set, the basal rate profile passed the plausibility checks of the Server and was written successfully (i.e., the write transaction of all time blocks of the basal rate profile completed).*/
    TRANSACTION_COMPLETED(1 shl 0);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<WriteBasalRateProfileTemplateResponseFlags> {
            val setFlags = EnumSet.noneOf(WriteBasalRateProfileTemplateResponseFlags::class.java)
            WriteBasalRateProfileTemplateResponseFlags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}