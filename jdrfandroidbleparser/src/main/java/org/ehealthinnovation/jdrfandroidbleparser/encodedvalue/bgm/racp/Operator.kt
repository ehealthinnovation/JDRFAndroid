package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp

enum class Operator constructor(val key: Int) {
    NULL(0),
    ALL_RECORDS(1),
    LESS_THAN_OR_EQUAL_TO(2),
    GREATER_THAN_OR_EQUAL_TO(3),
    WITHIN_RANGE_OF_INCLUSIVE(4),
    FIRST_RECORD(5),
    LAST_RECORD(6);

    companion object {
        private val map = Operator.values().associateBy(Operator::key)
        fun fromKey(type: Int) = map[type]
    }

}