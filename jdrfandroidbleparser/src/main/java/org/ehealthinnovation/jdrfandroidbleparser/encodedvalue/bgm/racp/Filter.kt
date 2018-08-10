package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp

enum class Filter constructor(val key: Int) {
    RESERVED_FOR_FUTURE_USE(0),
    SEQUENCE_NUMBER(1),
    USER_FACING_TIME(2);

    companion object {
        private val map = Filter.values().associateBy(Filter::key)
        fun fromKey(type: Int) = map[type]
    }
}