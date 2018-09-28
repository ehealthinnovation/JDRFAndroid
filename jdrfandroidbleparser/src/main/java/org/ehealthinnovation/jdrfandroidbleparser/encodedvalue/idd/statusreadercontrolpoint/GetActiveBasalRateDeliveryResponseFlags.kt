package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint

import java.util.*

enum class GetActiveBasalRateDeliveryResponseFlags constructor(val bit:Int) {

    /**If this bit is set, there is an active TBR. In this case, the fields TBR Type, TBR Adjustment Value, TBR Duration Programmed, and TBR Duration Remaining are present. */
    TBR_PRESENT(1 shl 0),
    /**If this bit and the TBR Present bit are set, the TBR Template Number field is present. */
    TBR_TEMPLATE_NUMBER_PRESENT(1 shl 1),
    /**If this bit is set, the Basal Delivery Context field is present. */
    BASAL_DELIVERY_CONTEXT_PRESENT(1 shl 2);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<GetActiveBasalRateDeliveryResponseFlags> {
            val setFlags = EnumSet.noneOf(GetActiveBasalRateDeliveryResponseFlags::class.java)
            GetActiveBasalRateDeliveryResponseFlags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}