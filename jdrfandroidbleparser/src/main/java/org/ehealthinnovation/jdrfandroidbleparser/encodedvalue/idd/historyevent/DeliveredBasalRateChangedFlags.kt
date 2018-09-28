package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent

import java.util.*

enum class DeliveredBasalRateChangedFlags constructor(val bit: Int) {

    /**If this bit is set, the Basal Delivery Context field is present. */
    BASAL_DELIVERY_CONTEXT_PRESENT(1 shl 0);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<DeliveredBasalRateChangedFlags> {
            val setFlags = EnumSet.noneOf(DeliveredBasalRateChangedFlags::class.java)
            DeliveredBasalRateChangedFlags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}