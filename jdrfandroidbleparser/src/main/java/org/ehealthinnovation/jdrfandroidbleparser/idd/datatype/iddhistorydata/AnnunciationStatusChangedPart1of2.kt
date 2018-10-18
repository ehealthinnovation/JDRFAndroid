package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.annunciationstatus.AnnunciationStatusValues
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.annunciationstatus.AnnunciationType
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.BolusType
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.AnnunciationStatusChangedPart1Flags
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import java.util.*

class AnnunciationStatusChangedPart1of2 : BaseOperandParser {

    override val tag = AnnunciationStatusChangedPart1of2::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var flags: EnumSet<AnnunciationStatusChangedPart1Flags>? = null
    var annunciationInstanceID: Int? = null
    var annunciationType: AnnunciationType? = null
    var annunciationStatusValues: AnnunciationStatusValues? = null
    var auxInfo1: Int? = null
    var auxInfo2: Int? = null

    override fun parse(): Boolean {
        flags = AnnunciationStatusChangedPart1Flags.parseFlags(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        annunciationInstanceID = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
        annunciationType = AnnunciationType.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16))
        annunciationStatusValues = AnnunciationStatusValues.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        flags?.let { flags ->
            if (flags.contains(AnnunciationStatusChangedPart1Flags.AUXINFO1_PRESENT)) {
                auxInfo1 = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
            }
            if (flags.contains(AnnunciationStatusChangedPart1Flags.AUXINFO2_PRESENT)) {
                auxInfo2 = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
            }

        }
        return true

    }
}