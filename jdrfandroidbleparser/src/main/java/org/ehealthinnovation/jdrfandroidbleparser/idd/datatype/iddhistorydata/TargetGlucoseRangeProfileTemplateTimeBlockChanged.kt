package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommanddataoperand.ProfileTemplateTimeBlockParser
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommanddataoperand.RangeProfileTemplateBlockParser

class TargetGlucoseRangeProfileTemplateTimeBlockChanged : BaseOperandParser {

    override val tag = TargetGlucoseRangeProfileTemplateTimeBlockChanged::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var targetGlucoseRangeProfileTemplateNumber: Int? = null
    var timeBlockNumber: Int? = null
    var profileTemplateTimeBlockParser: RangeProfileTemplateBlockParser? = null

    override fun parse(): Boolean {
        targetGlucoseRangeProfileTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        timeBlockNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        profileTemplateTimeBlockParser = RangeProfileTemplateBlockParser(getByteArray(6)).let {
            if (it.parse()) {
                it
            } else {
                return false
            }
        }
        return true
    }
}