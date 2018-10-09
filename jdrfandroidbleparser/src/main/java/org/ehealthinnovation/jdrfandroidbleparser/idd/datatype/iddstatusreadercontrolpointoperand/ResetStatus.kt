package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statuschanged.Flags
import java.util.*

/**
 * This class parses and holds the result of a reset status operand
 */
class ResetStatus : BaseOperandParser{

    override val tag = ResetStatus::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic):super(rawData, c)

    /**
     * The Operand of the Reset Status Op Code is comprised of a Flags field of the IDD Status
     * Changed characteristic. A specific status can be reset by setting the corresponding bit to 1
     * (=True). If a bit is set to 0 (= False), the corresponding status will be retained.
     */
    var flags : EnumSet<Flags>? = null

    override fun parse(): Boolean {
        flags = Flags.parseFlags(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16))
        return true
    }
}