package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint

import java.util.*

enum class GetInsulinOnBoardResponseFlags constructor(val bit:Int) {

    /**If this bit is set, the Remaining Duration field is present. */
    REMAINING_DURATION_PRESENT(1 shl 0);

    companion object {
        /**
         * Takes a passed in 8bit flag value and extracts the set mask values. Returns an
         * [EnumSet]<[Flags]> of set properties
         */
        fun parseFlags(characteristicFlags: Int): EnumSet<GetInsulinOnBoardResponseFlags> {
            val setFlags = EnumSet.noneOf(GetInsulinOnBoardResponseFlags::class.java)
            GetInsulinOnBoardResponseFlags.values().forEach {
                val flag = it.bit
                if (flag and characteristicFlags == flag) setFlags.add(it)
            }
            return setFlags
        }
    }
}