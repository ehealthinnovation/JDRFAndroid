package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.sessionstarttime.DstOffset
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.RecordingReason
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.utility.BluetoothDateTime

class ReferenceTime : BaseOperandParser {

    override val tag = ReferenceTime::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var recordingReason: RecordingReason? = null
    var dateTime: BluetoothDateTime? = null
    var timeZone: Int? = null
    var dstOffset: DstOffset? = null

    override fun parse(): Boolean {
        recordingReason = RecordingReason.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        dateTime = BluetoothDateTime(
                _year = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16),
                _month = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8),
                _day = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8),
                _hour = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8),
                _min = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8),
                _sec = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        )
        timeZone = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        dstOffset = DstOffset.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        return true
    }
}