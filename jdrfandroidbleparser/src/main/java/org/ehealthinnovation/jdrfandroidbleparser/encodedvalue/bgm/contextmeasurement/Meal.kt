package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.contextmeasurement

enum class Meal constructor(val key: Int) {

    RESERVED_FOR_FUTURE_USE(0),
    PREPRANDIAL(1),
    POSTPRANDIAL(2),
    FASTING(3),
    CASUAL(4),
    BEDTIME(5);

    companion object {
        private val map = Meal.values().associateBy(Meal::key)
        fun fromKey(type: Int) = map[type]
    }
}