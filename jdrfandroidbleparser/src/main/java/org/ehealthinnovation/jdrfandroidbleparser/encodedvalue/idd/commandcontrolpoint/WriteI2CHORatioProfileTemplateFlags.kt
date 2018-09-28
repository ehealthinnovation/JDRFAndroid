package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint

import java.util.*

enum class WriteI2CHORatioProfileTemplateFlags constructor(val bit: Int) {

    /**If this bit is set, it indicates to the Server that all time blocks of the I:CHO ratio profile have been sent (i.e., the I:CHO ratio profile is complete). It shall only be set if the last time blocks of the I:CHO ratio profile are sent. */
    END_TRANSACTION(1 shl 0),
    /**If this bit is set, the fields Second Duration and Second I2CHO Ratio are present. */
    SECOND_TIME_BLOCK_PRESENT(1 shl 1),
    /**If this bit is set, the fields Third Duration and Third I2CHO Ratio are present. */
    THIRD_TIME_BLOCK_PRESENT(1 shl 2);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<WriteI2CHORatioProfileTemplateFlags> {
            val setFlags = EnumSet.noneOf(WriteI2CHORatioProfileTemplateFlags::class.java)
            WriteI2CHORatioProfileTemplateFlags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}