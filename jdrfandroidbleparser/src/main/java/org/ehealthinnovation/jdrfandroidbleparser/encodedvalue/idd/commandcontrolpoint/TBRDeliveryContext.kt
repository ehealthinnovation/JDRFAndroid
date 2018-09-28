package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint

enum class TBRDeliveryContext constructor(val key: Int){

    /**The delivery context is undetermined. */
    UNDETERMINED(0x0F),
    /**The TBR was initiated directly on the Insulin Delivery Device (e.g., the user triggers a TBR directly on the device). */
    DEVICE_BASED(0x33),
    /**The TBR was initiated via a remote control (i.e., an external device). For example, the user triggers a TBR via a remote control. */
    REMOTE_CONTROL(0x3C),
    /**The TBR was initiated by an AP Controller (i.e., an external automated device) as part of an APDS. */
    AP_CONTROLLER(0x55);

    companion object {
        private val map = TBRDeliveryContext.values().associateBy(TBRDeliveryContext::key)
        fun fromKey(type: Int) = map[type]
    }
}