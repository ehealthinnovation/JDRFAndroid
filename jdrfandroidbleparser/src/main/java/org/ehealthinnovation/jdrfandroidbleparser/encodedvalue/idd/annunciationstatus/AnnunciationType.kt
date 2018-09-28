package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.annunciationstatus

enum class AnnunciationType constructor(val key: Int){

    /**A general device fault or system error occurred (e.g., electronical or software error). */
    SYSTEM_ISSUE(0x000F),

    /**A mechanical error occurred.*/
    MECHANICAL_ISSUE(0x0033),

    /**An occlusion occurred (e.g., clogging of infusion set). */
    OCCLUSION_DETECTED(0x003C),

    /**An error related to the replacement or functioning of the reservoir occurred. */
    RESERVOIR_ISSUE(0x0055),

    /**The reservoir is empty. */
    RESERVOIR_EMPTY(0x005A),

    /**The reservoir fill level reached a defined low threshold. */
    RESERVOIR_LOW(0x0066),

    /**There is a priming issue after replacement of reservoir and/or infusion set (e.g., infusion set has not been primed). */
    PRIMING_ISSUE(0x0069),

    /**The physical connection between infusion set (including tubing and/or cannula) and the Insulin Delivery Device is incomplete. */
    INFUSION_SET_INCOMPLETE(0x0096),

    /**The infusion set (including tubing and/or cannula) is not attached to the body. */
    INFUSION_SET_DETACHED(0x0099),

    /**The Insulin Delivery Device has insufficient power to charge the device (i.e., the device cannot properly function). */
    POWER_SOURCE_INSUFFICIENT(0x00A5),

    /**The Insulin Delivery Device has no operational runtime left. The user shall be informed. */
    BATTERY_EMPTY(0x00AA),

    /**The Insulin Delivery Device has a low operational runtime (e.g., the battery charge level reached a defined low threshold, the battery is depleted, or the battery voltage is less than full strength). The user shall be informed. */
    BATTERY_LOW(0x00C3),

    /**The Insulin Delivery Device has a medium operational runtime. This annunciation should be reported at half of the operational runtime. */
    BATTERY_MEDIUM(0x00CC),

    /**The Insulin Delivery Device has a full operational runtime. This annunciation should be reported at full operational runtime. */
    BATTERY_FULL(0x00F0),

    /**The temperature is outside of the normal operating range.  */
    TEMPERATURE_OUT_OF_RANGE(0x00FF),

    /**The air pressure is outside of the normal operating range (e.g., altitude). */
    AIR_PRESSURE_OUT_OF_RANGE(0x0303),

    /**A running bolus was canceled (e.g., Insulin Delivery Device changed from run to standby mode). */
    BOLUS_CANCELED(0x030C),

    /**The temporary basal rate expired (i.e., the programmed duration is over). */
    TBR_OVER(0x0330),

    /**The temporary basal rate canceled (e.g., device changed from run to standby mode). */
    TBR_CANCELED(0x033F),

    /**The delivery reached a defined high threshold based on maximum bolus and maximum basal rates. */
    MAX_DELIVERY(0x0356),

    /**The date time of the device was never set or has been lost (e.g., due to a battery replacement). */
    DATE_TIME_ISSUE(0x0359),

    /**The Insulin Delivery Device reports a temperature measurement. */
    TEMPERATURE(0x0365);

    companion object {
        private val map = AnnunciationType.values().associateBy(AnnunciationType::key)
        fun fromKey(type: Int) = map[type]
    }
}