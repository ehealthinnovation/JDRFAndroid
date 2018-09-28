package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint

enum class BolusValueSelectionValue constructor(val key: Int){


    PROGRAMMED(0x0F),
    REMAINING(0x33),
    DELIVERED(0x3C);

    companion object {
        private val map = BolusValueSelectionValue.values().associateBy(BolusValueSelectionValue::key)
        fun fromKey(type: Int) = map[type]
    }
}