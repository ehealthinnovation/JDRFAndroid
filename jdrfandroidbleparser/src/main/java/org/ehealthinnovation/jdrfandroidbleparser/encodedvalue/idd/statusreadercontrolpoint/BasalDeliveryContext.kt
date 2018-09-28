package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint

enum class BasalDeliveryContext constructor(val key: Int) {


    /**The basal delivery context is undetermined. */
    UNDETERMINED(0x0F),
    /**The current basal rate was set directly on the Insulin Delivery Device (e.g., an active basal rate reached a new time block, the user changed the active basal rate profile or triggered a TBR directly on the device). */
    DEVICE_BASED(0x33),
    /**The current basal rate was set via a remote control (i.e., an external device). For example, the user changed the active basal rate profile or triggered a TBR via a remote control. */
    REMOTE_CONTROL(0x3C),
    /**The current basal rate was set by an AP Controller (i.e., an external automated device) as part of an Artificial Pancreas Device System (APDS)*/
    ARTIFICIAL_PANCREAS_CONTROLLER(0x55);

    companion object {
        private val map = BasalDeliveryContext.values().associateBy(BasalDeliveryContext::key)
        fun fromKey(type: Int) = map[type]
    }

}