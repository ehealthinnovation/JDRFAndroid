package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.WriteISFProfileTemplateFlags
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter
import java.util.*

class WriteISFProfileTemplate : BaseOperandWriter {

    override val tag = WriteISFProfileTemplate::class.java.canonicalName as String

    constructor() : super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var flags: EnumSet<WriteISFProfileTemplateFlags>? = null
    var isfProfileTemplateNumber: Int? = null
    var firstTimeBlockNumberIndex: Int? = null
    var firstBlock: ProfileTemplateTimeBlock? = null
    var secondBlock: ProfileTemplateTimeBlock? = null
    var thirdBlock: ProfileTemplateTimeBlock? = null


    override fun compose(): Boolean {
        if (!hasValidArguments()) {
            return false
        }

        flags?.let { setIntValue(WriteISFProfileTemplateFlags.composeFlags(it), BluetoothGattCharacteristic.FORMAT_UINT8) }
        isfProfileTemplateNumber?.let { setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT8) }
        firstTimeBlockNumberIndex?.let { setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT8) }
        firstBlock?.let {
            if (it.compose()) {
                setByteArray(it.rawData)
            }
        }
        flags?.let {
            if (it.contains(WriteISFProfileTemplateFlags.SECOND_TIME_BLOCK_PRESENT)) {
                secondBlock?.let {
                    if (it.compose()) {
                        setByteArray(it.rawData)
                    }
                }

            }

            if (it.contains(WriteISFProfileTemplateFlags.THIRD_TIME_BLOCK_PRESENT)) {
                thirdBlock?.let {
                    if (it.compose()) {
                        setByteArray(it.rawData)
                    }
                }

            }
        }
        return true
    }


    override fun hasValidArguments(): Boolean {
        if (flags == null || isfProfileTemplateNumber == null || firstTimeBlockNumberIndex == null
                || firstBlock == null || !firstBlock!!.hasValidArguments()) {
            return false
        }
        flags?.let {
            if (it.contains(WriteISFProfileTemplateFlags.SECOND_TIME_BLOCK_PRESENT)) {
                if (secondBlock == null) {
                    return false
                }
            }

            if (it.contains(WriteISFProfileTemplateFlags.THIRD_TIME_BLOCK_PRESENT)) {
                if (thirdBlock == null) {
                    return false
                }
            }
        }

        return true
    }
}