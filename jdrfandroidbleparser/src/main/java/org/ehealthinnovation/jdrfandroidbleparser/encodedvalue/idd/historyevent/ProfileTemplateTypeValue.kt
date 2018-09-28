package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent

enum class ProfileTemplateTypeValue(val key: Int) {
    UNDETERMINED(0x0F),
    BASAL_RATE_PROFILE_TEMPLATE(0x33),
    ISF_PROFILE_TEMPLATE(0x3C),
    I2CHO_RATIO_PROFILE_TEMPLATE(0x55),
    TARGET_GLUCOSE_RANGE_PROFILE_TEMPLATE(0x5A);

    companion object {
        private val map = ProfileTemplateTypeValue.values().associateBy(ProfileTemplateTypeValue::key)
        fun fromKey(type: Int) = map[type]
    }
}