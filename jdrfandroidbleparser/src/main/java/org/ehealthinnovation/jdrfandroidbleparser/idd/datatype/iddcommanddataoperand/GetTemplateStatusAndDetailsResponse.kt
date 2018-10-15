package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommanddataoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commanddata.TemplateType
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.historyevent.ProfileTemplateTypeValue
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import java.lang.NullPointerException

class GetTemplateStatusAndDetailsResponse : BaseOperandParser {

    override val tag = GetTemplateStatusAndDetailsResponse::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic) : super(rawData, c)

    var templateType: TemplateType? = null
    var startingTemplateNumber: Int? = null
    var numberOfTemplates: Int? = null
    var maxNumberOfSupportedTimeBlocks: Int? = null
    var configurableAndConfiguredFlags: MutableList<ConfigurableAndConfiguredFlag> = mutableListOf()

    override fun parse(): Boolean {
        templateType = TemplateType.fromKey(getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        startingTemplateNumber = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        numberOfTemplates = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        maxNumberOfSupportedTimeBlocks = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        var numberOfFlagOctetExpected: Int = 0
        numberOfTemplates?.let {
            numberOfFlagOctetExpected = (it shr 2) + 1
        }
        var flagOctetHolder: MutableList<Int> = mutableListOf()
        for (index in 0 until numberOfFlagOctetExpected) {
            flagOctetHolder.add(index, getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8))
        }

        var targetOctectIndex: Int = 0
        var targetBitPairIndex: Int = 0
        var flagPairBuffer: ConfigurableAndConfiguredFlag
        numberOfTemplates?.let {
            for (index in 0 until numberOfTemplates!!) {
                targetOctectIndex = index shr 2
                targetBitPairIndex = index.rem(4)
                //todo double check the bit pair location
                flagPairBuffer = ConfigurableAndConfiguredFlag(
                        configurable = (flagOctetHolder[targetOctectIndex].and(0x01 shl targetBitPairIndex) != 0),
                        configured = (flagOctetHolder[targetOctectIndex].and(0x02 shl targetBitPairIndex)) != 0
                )
                configurableAndConfiguredFlags.add(index, flagPairBuffer)
            }
        }
        return true
    }


}