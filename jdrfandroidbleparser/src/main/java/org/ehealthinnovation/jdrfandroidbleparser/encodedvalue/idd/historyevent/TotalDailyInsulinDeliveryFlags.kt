package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent

import java.util.*

enum class TotalDailyInsulinDeliveryFlags constructor(val bit: Int) {

    /**If this bit is set, the date time of the Insulin Delivery Device will have changed since the last recorded Total Daily Insulin Delivery Even*/
    DATE_TIME_CHANGED_WARNING(1 shl 0);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<TotalDailyInsulinDeliveryFlags> {
            val setFlags = EnumSet.noneOf(TotalDailyInsulinDeliveryFlags::class.java)
            TotalDailyInsulinDeliveryFlags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}