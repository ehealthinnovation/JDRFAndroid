package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint

import java.util.*

enum class GetAvailableBolusesResponseFlags constructor(val bit: Int) {


    /**If this bit is set, a fast bolus is currently available to be set. */
    FAST_BOLUS_AVAILABLE(1 shl 0),
    /**If this bit is set, an extended bolus is currently available to be set. */
    EXTENDED_BOLUS_AVAILABLE(1 shl 1),
    /**If this bit is set, a multiwave bolus is currently available to be set. */
    MULTIWAVE_BOLUS_AVAILABLE(1 shl 2);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<GetAvailableBolusesResponseFlags> {
            val setFlags = EnumSet.noneOf(GetAvailableBolusesResponseFlags::class.java)
            GetAvailableBolusesResponseFlags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}