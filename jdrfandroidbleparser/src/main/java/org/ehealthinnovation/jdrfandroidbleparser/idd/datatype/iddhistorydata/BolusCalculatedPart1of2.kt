package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.sessionstarttime.DstOffset
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.RecordingReason
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.utility.BluetoothDateTime

class BolusCalculatedPart1of2 : BaseOperandParser {

    override val tag = BolusCalculatedPart1of2::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var recommendedFastAmountMeal: Float? = null
    var recommendedFastAmountCorrection: Float? = null
    var recommendedExtendedAmountMeal: Float? = null
    var recommendedExtendedAmountCorrection: Float? = null

    override fun parse(): Boolean {
        recommendedFastAmountMeal = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        recommendedFastAmountCorrection = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        recommendedExtendedAmountMeal = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        recommendedExtendedAmountCorrection = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
       return true
    }
}