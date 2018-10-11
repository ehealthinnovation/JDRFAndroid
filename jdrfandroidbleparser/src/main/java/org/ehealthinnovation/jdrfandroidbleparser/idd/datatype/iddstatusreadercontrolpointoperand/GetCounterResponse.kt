package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.CounterType
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.CounterValueSelection
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser

class GetCounterResponse : BaseOperandParser {

    override val tag = GetCounterResponse ::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic):super(rawData, c)

    var counterType: CounterType? = null
    var counterValueSelection: CounterValueSelection? = null
    var counterValue: Int? = null

    override fun parse(): Boolean {
        counterType = CounterType.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        counterValueSelection = CounterValueSelection.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        counterValue = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT32)
        return true
    }
}