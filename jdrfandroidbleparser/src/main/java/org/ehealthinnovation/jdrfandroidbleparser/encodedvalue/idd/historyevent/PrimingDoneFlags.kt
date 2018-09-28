package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent

import java.util.*

enum class PrimingDoneFlags constructor(val bit: Int) {

    /**If this bit is set, the Annunciation Instance ID field is present. */
    ANNUNCIATION_INSTANCE_ID_PRESENT(1 shl 0);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<PrimingDoneFlags> {
            val setFlags = EnumSet.noneOf(PrimingDoneFlags::class.java)
            PrimingDoneFlags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}