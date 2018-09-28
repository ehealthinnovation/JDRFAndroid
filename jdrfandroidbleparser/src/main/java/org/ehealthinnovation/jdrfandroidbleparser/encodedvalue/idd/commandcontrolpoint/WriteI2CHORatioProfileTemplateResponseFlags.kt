package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint

import java.util.*

enum class WriteI2CHORatioProfileTemplateResponseFlags constructor(val bit: Int) {


    /**If this bit is set, the I:CHO ratio profile passed the plausibility checks of the Server and was written successfully (i.e., the write transaction of all time blocks of the I:CHO ratio profile completed).*/
    TRANSACTION_COMPLETED(1 shl 0);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<WriteI2CHORatioProfileTemplateResponseFlags> {
            val setFlags = EnumSet.noneOf(WriteI2CHORatioProfileTemplateResponseFlags::class.java)
            WriteI2CHORatioProfileTemplateResponseFlags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}