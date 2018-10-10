package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.BolusValueSelectionValue


/**
 * If the Get Active Bolus Delivery Op Code is written to the IDD Status Reader Control Point with
 *an Operand containing a Bolus ID and a Bolus Value Selection field, the Server shall look up
 *the corresponding Active Bolus and indicate the IDD Status Reader Control Point with a
 *Get Active Bolus Delivery Response Op Code and an Active Bolus Delivery record.
 */
class GetActiveBolusDelivery : BaseOperandWriter {

    override val tag = GetActiveBolusDelivery ::class.java.canonicalName as String

    /**
     * The Bolus ID field represents a unique identifier as a uint16 data type created by the
     * Server Application for a programmed bolus.
     */
    var bolusId: Int? = null

    /**
     * A type of possible bolus delivery value
     */
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