package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.racp

enum class Filter constructor(val key: Int) {
    RESERVED_FOR_FUTURE_USE(0),
    TIME_OFFSET(1);

    companion object {
        private val map = Filter.values().associateBy(Filter::key)
        fun fromKey(type: Int) = map[type]
    }
}