package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommanddataoperand.ProfileTemplateTimeBlockParser

class ISFProfileTemplateTimeBlockChanged : BaseOperandParser {

    override val tag = ISFProfileTemplateTimeBlockChanged::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var isfRateProfileTemplateNumber: Int? = null
    var timeBlockNumber: Int? = null
    var profileTemplateTimeBlockParser: ProfileTemplateTimeBlockParser? = null

    override fun parse(): Boolean {
        isfRateProfileTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        timeBlockNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        profileTemplateTimeBlockParser = ProfileTemplateTimeBlockParser(getByteArray(4)).let {
            if (it.parse()) {
                it
            } else {
                return false
            }
        }
        return true
    }
}