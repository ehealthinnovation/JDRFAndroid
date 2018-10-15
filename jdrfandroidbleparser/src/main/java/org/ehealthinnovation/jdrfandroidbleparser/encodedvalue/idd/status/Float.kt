package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.status

enum class Float constructor(val key: Int) {


    /**The operational state is undetermined. */
    UNDETERMINED(0x0F),

    /**The insulin infusion therapy is stopped but the Insulin Delivery Device can still be configured (e.g., priming). */
    STOP(0x33),

    /**The insulin infusion therapy is paused (i.e., the device does not deliver insulin related to the therapy but delivers the missed amount of insulin after leaving state Pause and entering state Run). Typically the Pause state is limited to several minutes and can be used, for example, to bridge the time during a reservoir change. */
    PAUSE(0x3C),

    /**The insulin infusion therapy is running (i.e., the device delivers insulin related to the therapy). The Insulin Delivery Device cannot be configured in the Run state (e.g., priming). */
    RUN(0x55);

    companion object {
        private val map = Float.values().associateBy(Float ::key)
        fun fromKey(type: Int) = map[type]
    }
}