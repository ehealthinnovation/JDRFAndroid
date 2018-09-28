package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.annunciationstatus

import java.util.*

enum class Flags constructor(val bit: Int) {

    /**If this bit is set, the fields Annunciation Instance ID, Annunciation Type, and Annunciation Status are present (i.e., there is a currently active or already confirmed annunciation). This bit shall not be set if there has not been an annunciation yet. */
    ANNUNCIATION_PRESENT(1 shl 0),

    /**If this bit is set, the AuxInfo1 field is present. */
    AUXINFO1_PRESENT(1 shl 1),

    /**If this bit is set, the AuxInfo2 field is present. */
    AUXINFO2_PRESENT(1 shl 2),

    /**If this bit is set, the AuxInfo3 field is present. */
    AUXINFO3_PRESENT(1 shl 3),

    /**If this bit is set, the AuxInfo4 field is present. */
    AUXINFO4_PRESENT(1 shl 4),

    /**If this bit is set, the AuxInfo5 field is present. */
    AUXINFO5_PRESENT(1 shl 5);

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