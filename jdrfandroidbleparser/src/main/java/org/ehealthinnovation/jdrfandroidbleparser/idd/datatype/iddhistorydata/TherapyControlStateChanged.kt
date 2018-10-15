package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.status.Float
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser

class TherapyControlStateChanged : BaseOperandParser {

    override val tag = TherapyControlStateChanged::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var oldTherapyControlState: Float? = null
    var newTherapyControlState: Float? = null

    override fun parse(): Boolean {
        oldTherapyControlState = Float.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        newTherapyControlState = Float.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        return true
    }
}