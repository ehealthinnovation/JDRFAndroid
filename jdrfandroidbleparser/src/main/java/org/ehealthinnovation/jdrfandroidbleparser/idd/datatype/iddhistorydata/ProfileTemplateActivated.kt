package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddhistorydata

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.sessionstarttime.DstOffset
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.ProfileTemplateTypeValue
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.RecordingReason
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.utility.BluetoothDateTime

class ProfileTemplateActivated: BaseOperandParser{

    override val tag =ProfileTemplateActivated::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic):super(rawData, c)

    var profileTemplateType: ProfileTemplateTypeValue?= null
    var oldProfileTemplateNumber: Int?= null
    var newProfileTemplateNumber: Int? = null

    override fun parse(): Boolean {
        profileTemplateType = ProfileTemplateTypeValue.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        oldProfileTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        newProfileTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        return true
    }
}