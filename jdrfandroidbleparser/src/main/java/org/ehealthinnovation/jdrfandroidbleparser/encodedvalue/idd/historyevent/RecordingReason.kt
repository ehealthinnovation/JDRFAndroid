package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent

enum class RecordingReason(val key: Int) {


    /**The reason is undetermined. */
    UNDETERMINED(0x0F),
    /**The date time of the Insulin Delivery Device was changed (i.e., at least one of the fields – Date Time, Time Zone, or DST Offset changed). */
    SET_DATE_TIME(0x33),
    /**The Reference Time event was recorded periodically (e.g., at each full hour). */
    PERIODIC_RECORDING(0x3C),
    /**The Insulin Delivery Device lost its date time (e.g., due to battery replacement).That is, the date time of the device is set to its default value and the Relative Offset field of the Reference Time event is 0. */
    DATE_TIME_LOSS(0x55);


    companion object {
        private val map = RecordingReason.values().associateBy(RecordingReason::key)
        fun fromKey(type: Int) = map[type]
    }
}