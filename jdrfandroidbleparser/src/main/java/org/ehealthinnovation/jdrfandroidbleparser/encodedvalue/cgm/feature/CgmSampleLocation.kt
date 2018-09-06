package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.feature

enum class CgmSampleLocation constructor(val key: Int) {

    RESERVED_FOR_FUTURE_USE(0),
    FINGER(1),
    ALTERNATE_SITE_TEST(2),
    EARLOBE(3),
    CONTROL_SOLUTION(4),
    SUBCUTANEOUS_TISSUE(5),
    SAMPLE_LOCATION_VALUE_NOT_AVAILABLE(15);

    companion object {
        private val map = CgmSampleLocation.values().associateBy(CgmSampleLocation::key)
        fun fromKey(type: Int) = map[type]
    }
}