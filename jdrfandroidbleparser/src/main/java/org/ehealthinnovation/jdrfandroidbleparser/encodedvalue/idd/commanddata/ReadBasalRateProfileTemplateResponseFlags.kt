package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commanddata

import java.util.*

enum class ReadBasalRateProfileTemplateResponseFlags constructor(val bit: Int) {
    /**If this bit is set, the fields Second Duration and Second Rate are present. */
    SECOND_TIME_BLOCK_PRESENT(1 shl 0),
    /**If this bit is set, the fields Third Duration and Third Rate are present. */
    THIRD_TIME_BLOCK_PRESENT(1 shl 1);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<ReadBasalRateProfileTemplateResponseFlags> {
            val setFlags = EnumSet.noneOf(ReadBasalRateProfileTemplateResponseFlags::class.java)
            ReadBasalRateProfileTemplateResponseFlags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }

        fun composeFlags(flags: EnumSet<ReadBasalRateProfileTemplateResponseFlags>) :Int {
            var output = 0
            for (flag in flags){
                output += flag.bit
            }
            return output
        }

    }
}