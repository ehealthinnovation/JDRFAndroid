package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.BolusType
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser

class BolusDeliveredPart1of2 : BaseOperandParser {

    override val tag = BolusDeliveredPart1of2::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var bolusId: Int? = null
    var bolusType: BolusType? = null
    var deliveredBolusFastAmount: Float? = null
    var deliveredBolusExtendedAmount: Float? = null
    var effectiveBolusDuration: Int? = null

    override fun parse(): Boolean {
        bolusId = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
        bolusType = BolusType.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        deliveredBolusFastAmount = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        deliveredBolusExtendedAmount = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        effectiveBolusDuration = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
        return true

    }
}