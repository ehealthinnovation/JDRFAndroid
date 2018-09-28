package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent

import java.util.*

enum class BolusDeliveryPart2Flags constructor(val bit: Int) {

    /**If this bit is set, the Bolus Activation Type field is present. */
    BOLUS_ACTIVATION_TYPE_PRESENT(1 shl 0),
    /**If this bit is set, the Bolus End Reason field is present. */
    BOLUS_END_REASON_PRESENT(1 shl 1),
    /**If this bit is set, the Annunciation Instance ID field is present. */
    ANNUNCIATION_INSTANCE_ID_PRESENT(1 shl 2);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<BolusDeliveryPart2Flags> {
            val setFlags = EnumSet.noneOf(BolusDeliveryPart2Flags::class.java)
            BolusDeliveryPart2Flags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}