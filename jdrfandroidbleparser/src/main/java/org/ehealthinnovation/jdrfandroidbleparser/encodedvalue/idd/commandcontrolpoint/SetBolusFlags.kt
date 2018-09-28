package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint

import java.util.*

enum class SetBolusFlags constructor(val bit: Int) {

    /**If this bit is set, the Bolus Delay Time field is present. */
    BOLUS_DELAY_TIME_PRESENT(1 shl 0),
    /**If this bit is set, the Bolus Template Number field is present. */
    BOLUS_TEMPLATE_NUMBER_PRESENT(1 shl 1),
    /**If this bit is set, the Bolus Activation Type field is present. */
    BOLUS_ACTIVATION_TYPE_PRESENT(1 shl 2),
    /**If this bit is set, the reason for the bolus is the correction of a high blood glucose level. */
    BOLUS_DELIVERY_REASON_CORRECTION(1 shl 3),
    /**If this bit is set, the reason for the bolus is to cover the intake of food. */
    BOLUS_DELIVERY_REASON_MEAL(1 shl 4);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<SetBolusFlags> {
            val setFlags = EnumSet.noneOf(SetBolusFlags::class.java)
            SetBolusFlags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}