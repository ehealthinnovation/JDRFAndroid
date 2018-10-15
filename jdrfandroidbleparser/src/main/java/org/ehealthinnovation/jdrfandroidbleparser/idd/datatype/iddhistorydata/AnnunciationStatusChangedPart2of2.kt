package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.annunciationstatus.AnnunciationStatusValues
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.annunciationstatus.AnnunciationType
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.BolusType
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.AnnunciationStatusChangedPart1Flags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.AnnunciationStatusChangedPart2Flags
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import java.util.*

class AnnunciationStatusChangedPart2of2 : BaseOperandParser {

    override val tag = AnnunciationStatusChangedPart2of2::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var flags: EnumSet<AnnunciationStatusChangedPart2Flags>? = null
    var auxInfo3: Int? = null
    var auxInfo4: Int? = null
    var auxInfo5: Int? = null

    override fun parse(): Boolean {
        flags = AnnunciationStatusChangedPart2Flags.parseFlags(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        flags?.let { flags ->
            if (flags.contains(AnnunciationStatusChangedPart2Flags.AUXINFO3_PRESENT)) {
                auxInfo3 = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
            }
            if (flags.contains(AnnunciationStatusChangedPart2Flags.AUXINFO4_PRESENT)) {
                auxInfo4 = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
            }
            if (flags.contains(AnnunciationStatusChangedPart2Flags.AUXINFO5_PRESENT)) {
                auxInfo5 = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
            }

        }
        return true

    }
}