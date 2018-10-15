package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.GetBolusTemplateResponseFlags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.TBRType
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import java.lang.IllegalArgumentException
import java.lang.NullPointerException
import java.util.*

class GetBolusTemplateResponse : BaseOperandParser {

    override val tag = GetBolusTemplateResponse::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var bolusTemplateNumber: Int? = null
    var flags: EnumSet<GetBolusTemplateResponseFlags>? = null
    var bolus: BolusParser? = null
    var bolusDelayTime: Int? = null

    override fun parse(): Boolean {
        bolusTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        flags = GetBolusTemplateResponseFlags.parseFlags(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        bolus = BolusParser(getByteArray(7)).let {
            if (it.parse()) {
                it
            } else {
                return false
            }
        }
        flags?.let {
            if (it.contains(GetBolusTemplateResponseFlags.BOLUS_DELAY_TIME_PRESENT)){
                bolusDelayTime = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
            }
        }
        return true
    }
}