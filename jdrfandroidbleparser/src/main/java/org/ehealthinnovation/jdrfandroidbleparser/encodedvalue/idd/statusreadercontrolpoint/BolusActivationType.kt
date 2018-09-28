package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint

enum class BolusActivationType constructor(val key: Int) {


    /**The activation type is undetermined. */
    UNDETERMINED(0x0F),
    /**The bolus was defined by the user. */
    MANUAL_BOLUS(0x33),
    /**The bolus was recommended by a calculation algorithm (e.g., a bolus calculator) and confirmed by the user. */
    RECOMMENDED_BOLUS(0x3C),
    /**The user changed a recommended bolus. */
    MANUALLY_CHANGED_RECOMMENDED_BOLUS(0x55),
    /**The bolus was activated without user interaction (e.g., by an APDS)*/
    COMMANDED_BOLUS(0x5A);

    companion object {
        private val map = BolusActivationType.values().associateBy(BolusActivationType::key)
        fun fromKey(type: Int) = map[type]
    }

}