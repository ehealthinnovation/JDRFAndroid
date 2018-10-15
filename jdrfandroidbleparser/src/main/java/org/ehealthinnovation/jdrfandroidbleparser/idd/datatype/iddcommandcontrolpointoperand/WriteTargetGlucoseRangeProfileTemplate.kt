package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.WriteTargetGlucoseRangeProfileTemplateFlags
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter
import java.util.*

class WriteTargetGlucoseRangeProfileTemplate : BaseOperandWriter {

    override val tag = WriteTargetGlucoseRangeProfileTemplate::class.java.canonicalName as String

    constructor() : super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var flags: EnumSet<WriteTargetGlucoseRangeProfileTemplateFlags>? = null
    var targetGlucoseRangeProfileTemplateNumber: Int? = null
    var firstTimeBlockNumberIndex: Int? = null
    var firstBlock: RangeProfileTemplateBlock? = null
    var secondBlock: RangeProfileTemplateBlock? = null


    override fun compose(): Boolean {
        if (!hasValidArguments()) {
            return false
        }

        flags?.let { setIntValue(WriteTargetGlucoseRangeProfileTemplateFlags.composeFlags(it), BluetoothGattCharacteristic.FORMAT_UINT8) }
        targetGlucoseRangeProfileTemplateNumber?.let { setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT8) }
        firstTimeBlockNumberIndex?.let { setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT8) }
        firstBlock?.let {
            if (it.compose()) {
                setByteArray(it.rawData)
            }
        }
        flags?.let {
            if (it.contains(WriteTargetGlucoseRangeProfileTemplateFlags.SECOND_TIME_BLOCK_PRESENT)) {
                secondBlock?.let {
                    if (it.compose()) {
                        setByteArray(it.rawData)
                    }
                }

            }

        }
        return true
    }


    override fun hasValidArguments(): Boolean {
        if (flags == null || targetGlucoseRangeProfileTemplateNumber == null || firstTimeBlockNumberIndex == null
                || firstBlock == null || !firstBlock!!.hasValidArguments()) {
            return false
        }
        flags?.let {
            if (it.contains(WriteTargetGlucoseRangeProfileTemplateFlags.SECOND_TIME_BLOCK_PRESENT)) {
                if (secondBlock == null) {
                    return false
                }
            }
        }

        return true
    }
}