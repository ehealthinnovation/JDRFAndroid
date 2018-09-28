package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.feature

import java.util.*

enum class Flags constructor(val bit:Int) {
    /**If this bit is set, an E2E-Protection is attached to all characteristics. */
    E2E_PROTECTION_SUPPORTED(1 shl 0),
    /**If this bit is set, the Insulin Delivery Device supports the delivery of a basal rate including support for profile templates to define different basal rate profiles. */
    BASAL_RATE_SUPPORTED(1 shl 1),
    /**If this bit is set, the Insulin Delivery Device supports an absolute temporary basal rate in IU/h. */
    TBR_ABSOLUTE_SUPPORTED(1 shl 2),
    /**If this bit is set, the Insulin Delivery Device supports a relative temporary basal rate expressed by a dimensionless scaling factor. */
    TBR_RELATIVE_SUPPORTED(1 shl 3),
    /**If this bit is set, the Insulin Delivery Device supports templates to define different TBRs with preset values. */
    TBR_TEMPLATE_SUPPORTED(1 shl 4),
    /**If this bit is set, the Insulin Delivery Device has the capability to deliver fast boluses. */
    FAST_BOLUS_SUPPORTED(1 shl 5),
    /**If this bit is set, the Insulin Delivery Device has the capability to deliver extended boluses. */
    EXTENDED_BOLUS_SUPPORTED(1 shl 6),
    /**If this bit is set, the Insulin Delivery Device has the capability to deliver multiwave boluses. */
    MULTIWAVE_BOLUS_SUPPORTED(1 shl 7),
    /**If this bit is set, the Insulin Delivery Device supports a bolus delay time in minutes. */
    BOLUS_DELAY_TIME_SUPPORTED(1 shl 8),
    /**If this bit is set, the Insulin Delivery Device supports templates to define boluses (of same or different type) with preset values. */
    BOLUS_TEMPLATE_SUPPORTED(1 shl 9),
    /**If this bit is set, the Insulin Delivery Device supports a bolus activation type, which provides additional information about the source, and if possible, the determination of the bolus amount. */
    BOLUS_ACTIVATION_TYPE_SUPPORTED(1 shl 10),
    /**If this bit is set, the Insulin Delivery Device supports multiple bonded devices. */
    MULTIPLE_BOND_SUPPORTED(1 shl 11),
    /**If this bit is set, the Insulin Delivery Device supports profile templates to define different ISF profiles. */
    ISF_PROFILE_TEMPLATE_SUPPORTED(1 shl 12),
    /**If this bit is set, the Insulin Delivery Device supports profile templates to define different I:CHO Ratio profiles. */
    I2CHO_RATIO_PROFILE_TEMPLATE_SUPPORTED(1 shl 13),
    /**If this bit is set, the Insulin Delivery Device supports target glucose range profile templates to define different target glucose range profiles. */
    TARGET_GLUCOSE_RANGE_PROFILE_TEMPLATE_SUPPORTED(1 shl 14),
    /**If this bit is set, the Insulin Delivery Device enables Clients to get the current insulin on board. */
    INSULIN_ON_BOARD_SUPPORTED(1 shl 15),
    /**If the Feature Extension bit is set, an additional octet is attached (bits 24 to 31), where bit 31 shall be used as Feature Extension bit in the same way. If this bit is set, then another octet is attached (bits 32 to 39) and so on. This is defined to allow future extension of the characteristic. */
    FEATURE_EXTENSION(1 shl 23);

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