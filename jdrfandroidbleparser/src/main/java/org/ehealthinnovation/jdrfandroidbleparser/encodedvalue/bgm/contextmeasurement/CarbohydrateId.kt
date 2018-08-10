package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.contextmeasurement

enum class CarbohydrateId constructor(val key: Int) {

    RESERVED_FOR_FUTURE_USE(0),
    BREAKFAST(1),
    LUNCH(2),
    DINNER(3),
    SNACK(4),
    DRINK(5),
    SUPPER(6),
    BRUNCH(7);

    companion object {
        private val map = CarbohydrateId.values().associateBy(CarbohydrateId::key)
        fun fromKey(type: Int) = map[type]
    }
}