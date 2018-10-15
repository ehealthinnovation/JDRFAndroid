package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommanddataoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commanddata.ReadBasalRateProfileTemplateResponseFlags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commanddata.ReadTargetGlucoseRangeProfileTemplateResponseFlags
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import java.util.*

class ReadTargetGlucoseRangeProfileTemplateResponse: BaseOperandParser{

    override val tag =ReadTargetGlucoseRangeProfileTemplateResponse::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic):super(rawData, c)

    var flags: EnumSet<ReadTargetGlucoseRangeProfileTemplateResponseFlags>? = null

    var targetGlucoseRangeProfileTemplateNumber: Int? = null
    var firstTimeBlockNumberIndex: Int? = null
    var firstBlock: ProfileTemplateTimeBlockParser? = null
    //todo replace this with an array
    var secondBlock: ProfileTemplateTimeBlockParser? = null

    override fun parse(): Boolean {
        flags = ReadTargetGlucoseRangeProfileTemplateResponseFlags.parseFlags(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        targetGlucoseRangeProfileTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        firstTimeBlockNumberIndex = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        firstBlock = ProfileTemplateTimeBlockParser(getByteArray(6)).let {
            if (it.parse()) {
                it
            } else {
                return false
            }
        }
        flags?.let {
            if (it.contains(ReadTargetGlucoseRangeProfileTemplateResponseFlags.SECOND_TIME_BLOCK_PRESENT)) {
                secondBlock = ProfileTemplateTimeBlockParser(getByteArray(6)).let {
                    if (it.parse()) {
                        it
                    } else {
                        return false
                    }
                }
            }
        }
        return true
    }

}