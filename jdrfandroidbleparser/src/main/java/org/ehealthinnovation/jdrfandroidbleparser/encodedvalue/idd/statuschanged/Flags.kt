package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statuschanged

import java.util.*

enum class Flags constructor(val bit:Int) {
    /**If this bit is set, the therapy control state of the Insulin Delivery Device changed. */
    THERAPY_CONTROL_STATE_CHANGED(1 shl 0),

    /**If this bit is set, the operational state of the Insulin Delivery Device changed. */
    OPERATIONAL_STATE_CHANGED(1 shl 1),

    /**If this bit is set, the status of the insulin reservoir changed (caused by a reservoir change or the delivery of insulin). */
    RESERVOIR_STATUS_CHANGED(1 shl 2),

    /**If this bit is set, a new annunciation was created by the Server application. */
    ANNUNCIATION_STATUS_CHANGED(1 shl 3),

    /**If this bit is set, the total daily insulin amount changed due to a bolus or basal delivery. The bit shall be set at the end of an effective delivery. */
    TOTAL_DAILY_INSULIN_STATUS_CHANGED(1 shl 4),

    /**If this bit is set, the current basal rate changed due to a new basal rate value (e.g., caused by a changed basal rate profile, reaching of a time block with another basal rate value or by a TBR). */
    ACTIVE_BASAL_RATE_STATUS_CHANGED(1 shl 5),

    /**If this bit is set, a new bolus was initiated or the status of current Active Bolus changed. */
    ACTIVE_BOLUS_STATUS_CHANGED(1 shl 6),

    /**If this bit is set, a new event has been recorded in the history. */
    HISTORY_EVENT_RECORDED(1 shl 7);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<Flags> {
            val setFlags = EnumSet.noneOf(Flags::class.java)
            Flags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}