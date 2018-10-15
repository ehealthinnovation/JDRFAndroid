package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.status.OperationalState
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser

class OperationalStateChanged : BaseOperandParser {

    override val tag = OperationalStateChanged::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var oldOperationalState: OperationalState? = null
    var newOperationalState: OperationalState? = null

    override fun parse(): Boolean {
        oldOperationalState = OperationalState.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        newOperationalState = OperationalState.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        return true
    }
}