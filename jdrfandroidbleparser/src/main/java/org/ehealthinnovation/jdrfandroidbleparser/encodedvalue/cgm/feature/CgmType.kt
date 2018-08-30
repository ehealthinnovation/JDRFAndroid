package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.feature

enum class CgmType constructor(val key: Int) {

    RESERVED_FOR_FUTURE_USE(0),
    CAPILLARY_WHOLE_BLOOD(1),
    CAPILLARY_PLASMA(2),
    VENOUS_WHOLE_BLOOD(3),
    VENOUS_PLASMA(4),
    ARTERIAL_WHOLE_BLOOD(5),
    ARTERIAL_PLASMA(6),
    UNDETERMINED_WHOLE_BLOOD(7),
    UNDETERMINED_PLASMA(8),
    INTERSTITIAL_FLUID(9),
    CONTROL_SOLUTION(10);


    companion object {
        private val map = CgmType.values().associateBy(CgmType::key)
        fun fromKey(type: Int) = map[type]
    }
}