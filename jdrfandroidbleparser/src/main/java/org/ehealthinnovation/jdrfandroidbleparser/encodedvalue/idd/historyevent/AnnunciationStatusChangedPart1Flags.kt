package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent

import java.util.*

enum class AnnunciationStatusChangedPart1Flags constructor(val bit: Int) {

    /**If this bit is set, the AuxInfo1 field is present. */
    AUXINFO1_PRESENT(1 shl 0),
    /**If this bit is set, the AuxInfo2 field is present. */
    AUXINFO2_PRESENT(1 shl 1);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<AnnunciationStatusChangedPart1Flags> {
            val setFlags = EnumSet.noneOf(AnnunciationStatusChangedPart1Flags::class.java)
            AnnunciationStatusChangedPart1Flags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}