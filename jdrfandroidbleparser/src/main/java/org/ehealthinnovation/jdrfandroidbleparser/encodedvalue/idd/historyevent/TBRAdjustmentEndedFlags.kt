package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent

import java.util.*

enum class TBRAdjustmentEndedFlags constructor(val bit: Int) {

    /**If this bit is set, the Last Set TBR Template Number field is present. */
    LAST_SET_TBR_TEMPLATE_NUMBER_PRESENT(1 shl 0),
    /**If this bit is set, the Annunciation Instance ID field is present. */
    ANNUNCIATION_INSTANCE_ID_PRESENT(1 shl 1);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<TBRAdjustmentEndedFlags> {
            val setFlags = EnumSet.noneOf(TBRAdjustmentEndedFlags::class.java)
            TBRAdjustmentEndedFlags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}