package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.sessionstarttime.DstOffset
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.RecordingReason
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand.BolusParser
import org.ehealthinnovation.jdrfandroidbleparser.utility.BluetoothDateTime

class BolusProgrammedPart1Of2 : BaseOperandParser {

    override val tag = BolusProgrammedPart1Of2::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var bolusId: Int? = null
    var programmedBolus: BolusParser?= null

    override fun parse(): Boolean {
        bolusId = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
        programmedBolus = BolusParser(getByteArray(7)).let {
            if(it.parse()){
                it
            }else{
                return false
            }
        }
       return true
    }
}