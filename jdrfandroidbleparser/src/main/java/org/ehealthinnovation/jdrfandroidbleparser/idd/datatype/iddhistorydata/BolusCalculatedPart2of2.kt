package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.sessionstarttime.DstOffset
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.RecordingReason
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.utility.BluetoothDateTime

class BolusCalculatedPart2of2 : BaseOperandParser {

    override val tag = BolusCalculatedPart2of2::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var confirmedFastAmountMeal: Float? = null
    var confirmedFastAmountCorrection: Float? = null
    var confirmedExtendedAmountMeal: Float? = null
    var confirmedExtendedAmountCorrection: Float? = null

    override fun parse(): Boolean {
        confirmedFastAmountMeal = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        confirmedFastAmountCorrection = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        confirmedExtendedAmountMeal = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        confirmedExtendedAmountCorrection = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        return true
    }
}