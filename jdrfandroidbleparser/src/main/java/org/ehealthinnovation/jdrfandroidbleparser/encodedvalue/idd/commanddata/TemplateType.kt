package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commanddata

enum class TemplateType constructor(val key: Int){

    UNDETERMINED(0x0F),
    BASAL_RATE_PROFILE_TEMPLATE(0x33),
    TBR_TEMPLATE(0x3C),
    BOLUS_TEMPLATE(0x55),
    ISF_PROFILE_TEMPLATE(0x5A),
    I2CHO_RATIO_PROFILE_TEMPLATE(0x66),
    TARGET_GLUCOSE_RANGE_PROFILE_TEMPLATE(0x96);

    companion object {
        private val map = TemplateType.values().associateBy(TemplateType::key)
        fun fromKey(type: Int) = map[type]
    }
}