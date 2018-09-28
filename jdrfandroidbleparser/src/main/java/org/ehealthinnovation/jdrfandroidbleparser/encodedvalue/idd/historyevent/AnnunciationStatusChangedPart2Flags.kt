package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent

import java.util.*

enum class AnnunciationStatusChangedPart2Flags constructor(val bit: Int) {
    /**If this bit is set, the AuxInfo3 field is present. */
    AUXINFO3_PRESENT(1 shl 0),
    /**If this bit is set, the AuxInfo4 field is present. */
    AUXINFO4_PRESENT(1 shl 1),
    /**If this bit is set, the AuxInfo5 field is present*/
    AUXINFO5_PRESENT(1 shl 2);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<AnnunciationStatusChangedPart2Flags> {
            val setFlags = EnumSet.noneOf(AnnunciationStatusChangedPart2Flags::class.java)
            AnnunciationStatusChangedPart2Flags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}