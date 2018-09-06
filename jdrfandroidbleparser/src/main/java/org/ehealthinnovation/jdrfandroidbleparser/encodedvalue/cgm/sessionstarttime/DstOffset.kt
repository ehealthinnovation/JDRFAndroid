package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.sessionstarttime

enum class DstOffset(val key: Int) {
    STANDARD_TIME(0),
    HALF_AN_HOUR_DAYLIGHT_TIME(2),
    DAYLIGHT_TIME(4),
    DOUBLE_DAYLIGHT_TIME(8),
    DST_IS_NOT_KNOWN(255);

    companion object {
        private val map = DstOffset.values().associateBy(DstOffset::key)
        fun fromKey(type: Int) = map[type]
    }
}