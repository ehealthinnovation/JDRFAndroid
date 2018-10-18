package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.PrimingDoneFlags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.ReasonOfTerminationValue
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import java.util.*

class PrimingDone : BaseOperandParser {

    override val tag = PrimingDone::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var flags: EnumSet<PrimingDoneFlags>? = null
    var deliveredAmount: Float? = null
    var reasonOfTermination: ReasonOfTerminationValue? = null
    var annunciationInstanceID: Int? = null

    override fun parse(): Boolean {
        flags = PrimingDoneFlags.parseFlags(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        deliveredAmount = getNextFloat(BluetoothGattCharacteristic.FORMAT_SFLOAT)
        reasonOfTermination = ReasonOfTerminationValue.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        flags?.let {
            if (it.contains(PrimingDoneFlags.ANNUNCIATION_INSTANCE_ID_PRESENT)) {
                annunciationInstanceID = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
            }
        }
        return true
    }
}