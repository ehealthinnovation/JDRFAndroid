package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic

class GetTotalDailyInsulinStatusResponse : BaseOperandParser{

    override val tag = GetTotalDailyInsulinStatusResponse ::class.java.canonicalName as String

    var totalDailyInsulinSumOfBolusDelivered : Float? = null
    var totalDailyInsulinSumOfBasalDelivered : Float? = null
    var totalDailyInsulinSumOfBolusAndBasalDelivered : Float? = null

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic): super(rawData, c)

    override fun parse(): Boolean {
        totalDailyInsulinSumOfBolusDelivered = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        totalDailyInsulinSumOfBasalDelivered = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        totalDailyInsulinSumOfBolusAndBasalDelivered = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        return true
    }
}