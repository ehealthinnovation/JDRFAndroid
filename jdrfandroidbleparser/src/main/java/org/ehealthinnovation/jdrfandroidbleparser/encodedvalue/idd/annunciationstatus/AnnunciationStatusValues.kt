package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.annunciationstatus

enum class AnnunciationStatusValues(val key: Int) {

    /**The status of the annunciation is undetermined. */
    UNDETERMINED(0x0F),

    /**The annunciation is currently pending and requires a user action for snoozing or confirmation. */
    PENDING(0x33),

    /**The annunciation was noticed by the user and is set to pop up again at a short time later (i.e., a snoozed annunciation is still active). The time span shall be defined by the Server application. */
    SNOOZED(0x3C),

    /**The annunciation was confirmed by the user (i.e., a confirmed annunciation is not active anymore). */
    CONFIRMED(0x55);

    companion object {
        private val map = AnnunciationStatusValues.values().associateBy(AnnunciationStatusValues::key)
        fun fromKey(type: Int) = map[type]
    }
}