package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommanddataoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.WriteBasalRateProfileTemplateResponseFlags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commanddata.ReadBasalRateProfileTemplateResponseFlags
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import java.util.*

class ReadBasalRateProfileTemplateResponse : BaseOperandParser {

    override val tag = ReadBasalRateProfileTemplateResponse::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var flags: EnumSet<ReadBasalRateProfileTemplateResponseFlags>? = null

    var baseRateProfileTemplateNumber: Int? = null
    var firstTimeBlockNumberIndex: Int? = null
    var firstBlock: ProfileTemplateTimeBlockParser? = null
    //todo replace this with an array
    var secondBlock: ProfileTemplateTimeBlockParser? = null
    var thirdBlock: ProfileTemplateTimeBlockParser? = null

    override fun parse(): Boolean {
        flags = ReadBasalRateProfileTemplateResponseFlags.parseFlags(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        baseRateProfileTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        firstTimeBlockNumberIndex = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        firstBlock = ProfileTemplateTimeBlockParser(getByteArray(4)).let {
            if (it.parse()) {
                it
            } else {
                return false
            }
        }
        flags?.let {
            if (it.contains(ReadBasalRateProfileTemplateResponseFlags.SECOND_TIME_BLOCK_PRESENT)) {
                secondBlock = ProfileTemplateTimeBlockParser(getByteArray(4)).let {
                    if (it.parse()) {
                        it
                    } else {
                        return false
                    }
                }
            }

            if (it.contains(ReadBasalRateProfileTemplateResponseFlags.THIRD_TIME_BLOCK_PRESENT)) {
                thirdBlock = ProfileTemplateTimeBlockParser(getByteArray(4)).let {
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