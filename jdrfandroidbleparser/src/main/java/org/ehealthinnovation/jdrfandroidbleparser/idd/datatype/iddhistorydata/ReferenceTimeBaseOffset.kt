package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.sessionstarttime.DstOffset
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.RecordingReason
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.utility.BluetoothDateTime

class ReferenceTimeBaseOffset: BaseOperandParser{

    override val tag =ReferenceTimeBaseOffset::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic):super(rawData, c)

    var recordingReason: RecordingReason?= null
    var baseTime: BluetoothDateTime?= null
    var timeOffset: Int? = null

    override fun parse(): Boolean {
        recordingReason = RecordingReason.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        baseTime = BluetoothDateTime(
               _year = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16),
                _month = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8),
                _day = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8),
                _hour = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8),
                _min = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8),
                _sec = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        )
        timeOffset = getNextInt(BluetoothGattCharacteristic.FORMAT_SINT16)
        return true
    }
}