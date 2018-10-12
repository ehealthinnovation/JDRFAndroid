package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.Opcode
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.ResponseCode
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser

class GenericResponse:BaseOperandParser {

    override val tag = GenericResponse::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic):super(rawData, c)

    var requestOpCode: Opcode?= null
    var responseCodeValue: ResponseCode? = null

    override fun parse(): Boolean {
        requestOpCode = Opcode.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16))
        responseCodeValue = ResponseCode.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        return true
    }
}