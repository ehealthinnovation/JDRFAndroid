package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent

enum class BolusEndReason(val key: Int) {


    /**The reason why the Bolus ended is undetermined. */
    UNDETERMINED(0x0F),
    /**The programmed duration of the Bolus is over. */
    PROGRAMMED_DURATION_OVER(0x33),
    /**The Bolus was canceled by an interaction (e.g., by the user via a remote control or the device itself). */
    CANCELED(0x3C),
    /**The Bolus was aborted due to an error. */
    ERROR_ABORT(0x55);


    companion object {
        private val map = BolusEndReason.values().associateBy(BolusEndReason::key)
        fun fromKey(type: Int) = map[type]
    }
}