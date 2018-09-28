package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.status

enum class OperationalState(val key: Int) {
    /**The operational state is undetermined. */
    UNDETERMINED(0x0F),

    /**The Insulin Delivery Device is switched off and no functionality is available (i.e., no delivery and no configuration is possible).*/
    OFF(0x33),

    /**No insulin is delivered and resuming from this state is faster than from state Off (e.g., the device is being set to a state to save energy).*/
    STANDBY(0x3C),

    /**The Insulin Delivery Device prepares the insulin infusion therapy. For example, the Insulin Delivery Device rewinds the piston rod to enable the insertion of a new reservoir or performs a sniffing by detecting the position of the plunger in the reservoir (related to insulin pumps with cartridge). */
    PREPARING(0x55),

    /**The Insulin Delivery Device fills the fluidic path from the reservoir to the body with insulin (e.g., after replacement of the reservoir and/or infusion set). */
    PRIMING(0x5A),

    /**The Insulin Delivery Device waits for an interaction (e.g., waiting for the infusion set to be connected to the body after priming or waiting for a user confirmation of a reported error). */
    WAITING(0x66),

    /**The Insulin Delivery Device is ready for the insulin infusion therapy. */
    READY(0x96);

    companion object {
        private val map = OperationalState.values().associateBy(OperationalState::key)
        fun fromKey(type: Int) = map[type]
    }
}