package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.contextmeasurement

enum class MedicationId constructor(val key: Int) {

    RESERVED_FOR_FUTURE_USE(0),
    RAPID_ACTING_INSULIN(1),
    SHORT_ACTING_INSULIN(2),
    INTERMEDIATE_ACTING_INSULIN(3),
    LONG_ACTING_INSULIN(4),
    PRE_MIXED_INSULIN(5);

    companion object {
        private val map = MedicationId.values().associateBy(MedicationId::key)
        fun fromKey(type: Int) = map[type]
    }
}