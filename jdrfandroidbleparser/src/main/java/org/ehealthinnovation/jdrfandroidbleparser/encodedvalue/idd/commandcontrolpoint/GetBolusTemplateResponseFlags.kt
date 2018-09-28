package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint

import java.util.*

enum class GetBolusTemplateResponseFlags constructor(val bit: Int) {

    /**If this bit is set, the Bolus Delay Time field is present. */
    BOLUS_DELAY_TIME_PRESENT(1 shl 0),
    /**If this bit is set, the reason for the bolus is the correction of a high blood glucose level. */
    BOLUS_DELIVERY_REASON_CORRECTION(1 shl 1),
    /**If this bit is set, the reason for the bolus is to cover the intake of food. */
    BOLUS_DELIVERY_REASON_MEAL(1 shl 2);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<GetBolusTemplateResponseFlags> {
            val setFlags = EnumSet.noneOf(GetBolusTemplateResponseFlags::class.java)
            GetBolusTemplateResponseFlags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}