package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.sessionstarttime.DstOffset
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.ProfileTemplateTypeValue
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.RecordingReason
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommanddataoperand.ProfileTemplateTimeBlockParser
import org.ehealthinnovation.jdrfandroidbleparser.utility.BluetoothDateTime

class BasalRateProfileTemplateTimeBlockChanged : BaseOperandParser {

    override val tag = BasalRateProfileTemplateTimeBlockChanged::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var basalRateProfileTemplateNumber: Int? = null
    var timeBlockNumber: Int? = null
    var profileTemplateTimeBlockParser: ProfileTemplateTimeBlockParser? = null

    override fun parse(): Boolean {
        basalRateProfileTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
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