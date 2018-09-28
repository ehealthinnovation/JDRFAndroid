package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent

import java.util.*

enum class TBRAdjustmentStartedFlags constructor(val bit: Int) {

    /**If this bit is set, the TBR Template Number field is present. */
    TBR_TEMPLATE_NUMBER_PRESENT(1 shl 0);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<TBRAdjustmentStartedFlags> {
            val setFlags = EnumSet.noneOf(TBRAdjustmentStartedFlags::class.java)
            TBRAdjustmentStartedFlags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}