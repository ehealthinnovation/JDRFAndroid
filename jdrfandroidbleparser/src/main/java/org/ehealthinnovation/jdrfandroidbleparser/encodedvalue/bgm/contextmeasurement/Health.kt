package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.contextmeasurement

enum class Health constructor(val key: Int) {

    RESERVED_FOR_FUTURE_USE(0),
    MINOR_HEALTH_ISSUES(1),
    MAJOR_HEALTH_ISSUES(2),
    DURING_MENSES(3),
    UNDER_STRESS(4),
    NO_HEALTH_ISSUES(5),
    HEALTH_VALUE_NOT_AVAILABLE(15);

    companion object {
        private val map = Health.values().associateBy(Health::key)
        fun fromKey(type: Int) = map[type]
    }
}