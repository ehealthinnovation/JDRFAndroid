package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint

enum class CounterType constructor(val key: Int) {


    /**This counter provides the lifetime of the Insulin Delivery Device. It can be used if the device shall have a limited lifetime for operation. */
    IDD_LIFETIME(0x0F),
    /**This counter provides the warranty time of the Insulin Delivery Device. It can be used if the device shall have a limited warranty time. */
    IDD_WARRANTY_TIME(0x33),
    /**This counter provides the loaner time of the Insulin Delivery Device. It can be used if the device is loaned for a limited time. */
    IDD_LOANER_TIME(0x3C),
    /**This counter provides the operation time of the insulin in the reservoir. It can be used to warn the user when the insulin in the reservoir gets too old.*/
    RESERVOIR_INSULIN_OPERATION_TIME(0x55);

    companion object {
        private val map = CounterType.values().associateBy(CounterType::key)
        fun fromKey(type: Int) = map[type]
    }

}