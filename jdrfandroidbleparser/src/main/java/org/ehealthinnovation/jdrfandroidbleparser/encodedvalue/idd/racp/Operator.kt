package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.racp

enum class Operator constructor(val key: Int) {
    NULL(0),
    ALL_RECORDS(0x33),
    LESS_THAN_OR_EQUAL_TO(0x3C),
    GREATER_THAN_OR_EQUAL_TO(0x55),
    WITHIN_RANGE_OF_INCLUSIVE(0x5A),
    FIRST_RECORD(0x66),
    LAST_RECORD(0x69);

    companion object {
        private val map = Operator.values().associateBy(Operator::key)
        fun fromKey(type: Int) = map[type]
    }

}