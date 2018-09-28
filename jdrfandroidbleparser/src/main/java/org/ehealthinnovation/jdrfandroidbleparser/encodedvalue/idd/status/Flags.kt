package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.status

import java.util.*

enum class Flags constructor(val bit:Int) {
    /**If this bit is set, If this bit is set, the reservoir is attached to the Insulin Delivery Device.*/
    RESERVOIR_ATTACHED(1 shl 0);

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