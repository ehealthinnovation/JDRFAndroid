package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint

import java.util.*

enum class SetTBRAdjustmentFlags constructor(val bit: Int) {

    /**If this bit is set, the TBR Template Number field is present. */
    TBR_TEMPLATE_NUMBER_PRESENT(1 shl 0),
    /**If this bit is set, the TBR Delivery Context field is present. */
    TBR_DELIVERY_CONTEXT_PRESENT(1 shl 1),
    /**If this bit is set, a currently active TBR shall be completely overwritten with the changed settings; otherwise a new TBR shall be activated (i.e., the changed settings are new settings and are not based on the settings of the currently active TBR). */
    CHANGE_TBR(1 shl 2);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<SetTBRAdjustmentFlags> {
            val setFlags = EnumSet.noneOf(SetTBRAdjustmentFlags::class.java)
            SetTBRAdjustmentFlags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}