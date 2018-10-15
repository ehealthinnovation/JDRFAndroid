package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.racp

enum class Filter constructor(val key: Int) {

    RESERVED_FOR_FUTURE_USE(0),
    SEQUENCE_NUMBER(0x0F),
    SEQUENCE_NUMBER_FILTERED_BY_REFERENCE_TIME_EVENT(0X33),
    SEQUENCE_NUMBER_FILTERED_BY_NON_REFERENCE_TIME_EVENT(0x3C);

    companion object {
        private val map = Filter.values().associateBy(Filter::key)
        fun fromKey(type: Int) = map[type]
    }
}