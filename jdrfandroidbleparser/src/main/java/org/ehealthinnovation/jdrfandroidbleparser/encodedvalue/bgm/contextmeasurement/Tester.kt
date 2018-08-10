package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.contextmeasurement

enum class Tester constructor(val key: Int) {

    RESERVED_FOR_FUTURE_USE(0),
    SELF(1),
    HEALTH_CARE_PROFESSIONAL(2),
    LAB_TEST(3),
    TESTER_VALUE_NOT_AVAILABLE(15);

    companion object {
        private val map = Tester.values().associateBy(Tester::key)
        fun fromKey(type: Int) = map[type]
    }
}