package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint

enum class TBRType constructor(val key: Int) {


    UNDETERMINED(0x0F),
    ABSOLUTE(0x33),
    RELATIVE(0x3C);


    companion object {
        private val map = TBRType.values().associateBy(TBRType::key)
        fun fromKey(type: Int) = map[type]
    }

}