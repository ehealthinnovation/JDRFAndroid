package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.Opcode
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.ResponseCode
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser

/**
 * This class parses the operand of generic response
 */
class GenericReponse : BaseOperandParser {
    override val tag = GenericReponse::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    /**
     *  The request opcode
     */
    var opcode: Opcode? = null
    /**
     * The response code of the request operator
     */
    var responseCode: ResponseCode? = null

    override fun parse(): Boolean {
        var errorFreeparse = false
        opcode = Opcode.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16))
        responseCode = ResponseCode.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        //If the getNextInt returns a value, meaning error free parse is true
        errorFreeparse = true
        return errorFreeparse
    }

}