package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint

import java.util.*

enum class WriteBasalRateProfileTemplateFlags constructor(val bit: Int) {
    /**If this bit is set, it signals the Server that all time blocks of the basal rate profile have been sent (i.e., the basal rate profile is complete). It shall only be set if the last time blocks of the basal rate profile are sent. */
    END_TRANSACTION(1 shl 0),
    /**If this bit is set, the fields Second Duration and Second Rate are present. */
    SECOND_TIME_BLOCK_PRESENT(1 shl 1),
    /**If this bit is set, the fields Third Duration and Third Rate are present. */
    THIRD_TIME_BLOCK_PRESENT(1 shl 2);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<WriteBasalRateProfileTemplateFlags> {
            val setFlags = EnumSet.noneOf(WriteBasalRateProfileTemplateFlags::class.java)
            WriteBasalRateProfileTemplateFlags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }

        fun composeFlags(flags: EnumSet<WriteBasalRateProfileTemplateFlags>) :Int {
            var output = 0
            for (flag in flags){
                output += flag.bit
            }
            return output
        }

    }
}