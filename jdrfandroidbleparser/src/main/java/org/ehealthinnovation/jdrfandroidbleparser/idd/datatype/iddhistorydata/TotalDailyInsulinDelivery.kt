package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.sessionstarttime.DstOffset
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.ProfileTemplateTypeValue
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.RecordingReason
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.TotalDailyInsulinDeliveryFlags
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.utility.BluetoothDateTime
import java.util.*

class TotalDailyInsulinDelivery : BaseOperandParser {

    override val tag = TotalDailyInsulinDelivery::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var flags: EnumSet<TotalDailyInsulinDeliveryFlags>? = null
    var totalDailyInsulinSumOfBolusDelivered: Float? = null
    var totalDailyInsulinSumOfBasalDelivered: Float? = null
    var year: Int? = null
    var month: Int? = null
    var day: Int? = null

    override fun parse(): Boolean {
        flags = TotalDailyInsulinDeliveryFlags.parseFlags(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        totalDailyInsulinSumOfBolusDelivered = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        totalDailyInsulinSumOfBasalDelivered = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        year = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
        month = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        day = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        return true
    }
}