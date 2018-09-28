package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent

enum class ReasonOfTerminationValue(val key: Int) {


    /**The termination reason of the priming is undetermined. */
    UNDETERMINED(0x0F),
    /**The user aborted the priming of the Insulin Delivery Device (i.e., procedure Stop Priming was executed or the priming was aborted directly on the Insulin Delivery Device). */
    ABORTED_BY_USER(0x33),
    /**The priming volume of the programmed amount provided by the Start Priming procedure was reached. */
    PROGRAMMED_AMOUNT_REACHED(0x3C),
    /**The priming was aborted due to an error. */
    ERROR_ABORT(0x55);


    companion object {
        private val map = ReasonOfTerminationValue.values().associateBy(ReasonOfTerminationValue::key)
        fun fromKey(type: Int) = map[type]
    }
}