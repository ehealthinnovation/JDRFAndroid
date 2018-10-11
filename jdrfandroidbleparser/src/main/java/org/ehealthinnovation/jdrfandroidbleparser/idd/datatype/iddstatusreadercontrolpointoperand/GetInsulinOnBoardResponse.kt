package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.GetInsulinOnBoardResponseFlags
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import java.util.*

class GetInsulinOnBoardResponse : BaseOperandParser {

    override val tag = GenericReponse::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic):super(rawData, c)

    var flags : EnumSet<GetInsulinOnBoardResponseFlags>? = null
    var insulinOnBoard : Float? = null
    var remainingDuration: Int? = null

    override fun parse(): Boolean {
        flags = GetInsulinOnBoardResponseFlags.parseFlags(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        insulinOnBoard = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        flags?.let {flags->
            if(flags.contains(GetInsulinOnBoardResponseFlags.REMAINING_DURATION_PRESENT)){
                remainingDuration = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
            }
        }
        return true
    }
}