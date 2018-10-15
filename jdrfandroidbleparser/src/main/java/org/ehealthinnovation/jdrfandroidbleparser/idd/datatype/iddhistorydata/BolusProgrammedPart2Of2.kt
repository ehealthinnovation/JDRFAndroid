package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.sessionstarttime.DstOffset
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.SetBolusFlags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.RecordingReason
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint.BolusActivationType
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand.BolusParser
import org.ehealthinnovation.jdrfandroidbleparser.utility.BluetoothDateTime
import java.util.*

class BolusProgrammedPart2Of2 : BaseOperandParser {

    override val tag = BolusProgrammedPart2Of2::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var flags: EnumSet<SetBolusFlags>? = null
    var bolusDelayTime: Int? = null
    var bolusTemplateNumber: Int? = null
    var bolusActivationType: BolusActivationType? = null


    override fun parse(): Boolean {
        flags = SetBolusFlags.parseFlags(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        flags?.let { flags ->
            if (flags.contains(SetBolusFlags.BOLUS_DELAY_TIME_PRESENT)) {
                bolusDelayTime = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16)
            }
            if (flags.contains(SetBolusFlags.BOLUS_TEMPLATE_NUMBER_PRESENT)) {
                bolusTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
            }
            if (flags.contains(SetBolusFlags.BOLUS_ACTIVATION_TYPE_PRESENT)) {
                bolusActivationType = BolusActivationType.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
            }
        }
        return true
    }
}