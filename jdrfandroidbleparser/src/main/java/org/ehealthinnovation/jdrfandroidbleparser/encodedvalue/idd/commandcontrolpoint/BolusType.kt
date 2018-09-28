package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint

enum class BolusType constructor(val key: Int){



    UNDETERMINED(0x0F),
    FAST(0x33),
    EXTENDED(0x3C),
    MULTIWAVE(0x55);

    companion object {
        private val map = BolusType.values().associateBy(BolusType::key)
        fun fromKey(type: Int) = map[type]
    }
}