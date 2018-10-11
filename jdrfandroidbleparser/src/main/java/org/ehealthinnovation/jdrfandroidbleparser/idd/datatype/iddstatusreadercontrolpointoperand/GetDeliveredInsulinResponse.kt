package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic

class GetDeliveredInsulinResponse : BaseOperandParser {

    override val tag = GenericReponse::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic):super(rawData, c)

    var bolusAmountDelivered : Float? = null
    var basalAmountDelivered : Float? = null

    override fun parse(): Boolean {
        bolusAmountDelivered = getNextFloat(BluetoothGattCharacteristic.FORMAT_FLOAT)
        basalAmountDelivered = getNextFloat(BluetoothGattCharacteristic.FORMAT_FLOAT)
        return true
    }
}