package org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.bgmmeasurement

import java.nio.channels.spi.AbstractSelectionKey

enum class SampleLocation constructor(val key: Int) {
    RESERVED_FOR_FUTURE_USE(0),
    FINGER(1),
    ALTERNATIVE_SITE_TEST(2),
    EARLOBE(3),
    CONTROL_SOLUTION(4),
    SAMPLE_LOCATION_VALUE_NOT_AVAILABLE(15);

    companion object {
        private val map = SampleLocation.values().associateBy(SampleLocation::key)
        fun fromKey(keyValue: Int) = map[keyValue]
    }
}