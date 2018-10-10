package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.BolusValueSelectionValue

class GetActiveBolusDelivery : BaseOperandWriter {

    override val tag = GetActiveBolusDelivery ::class.java.canonicalName as String

    var bolusId: Int? = null
    var bolusValueSelection: BolusValueSelectionValue? = null

    constructor(): super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    override fun compose(): Boolean {
        var errorFreeComposing = false

        if (!hasValidArguments()){
            return errorFreeComposing
       }

        bolusId?.let {bolusId->
            setIntValue(bolusId, BluetoothGattCharacteristic.FORMAT_UINT16)
        }

        bolusValueSelection?.let { bolusValueSelectionValue ->
            setIntValue(bolusValueSelectionValue.key, BluetoothGattCharacteristic.FORMAT_UINT8)
        }

        errorFreeComposing = true
        System.out.printf("compose packet ${rawData.contentToString()}")

        return errorFreeComposing
    }

    override fun hasValidArguments(): Boolean {
        return !(bolusId == null || bolusValueSelection == null)
    }

}