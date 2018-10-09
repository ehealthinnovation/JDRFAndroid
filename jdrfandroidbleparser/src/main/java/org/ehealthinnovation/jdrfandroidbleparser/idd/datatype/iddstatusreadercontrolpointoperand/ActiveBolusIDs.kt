package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic

/**
 * This class parses and holds the operand of Get Active Bolus IDs Response
 * The Operand of the Get Active Bolus IDs Response Op Code is comprised of a Number of Active
 * Boluses field and Bolus ID fields of up to seven currently Active Boluses. The Bolus ID fields
 * represent unique identifiers of the currently Active Boluses that have been created by the Server
 * application at the programming of those boluses.
 */
class ActiveBolusIDs: BaseOperandParser{

    override val tag = ActiveBolusIDs::class.java.canonicalName as String

    constructor(rawData: ByteArray) : super(rawData)

    constructor(rawData: ByteArray, c: BluetoothGattCharacteristic): super(rawData, c)

    var numberOfActiveBoluses: Int? = null
    var bolusIDs: MutableList<Int> =  ArrayList<Int>()

    override fun parse(): Boolean {
        var errorFreeParsing = false
        numberOfActiveBoluses = getNextInt(BluetoothGattCharacteristic.FORMAT_UINT8)
        numberOfActiveBoluses?.let { numberOfActiveBoluses ->
            bolusIDs = mutableListOf()

            if (numberOfActiveBoluses == 0) {
                errorFreeParsing = true
                return errorFreeParsing
            }

            for (index in 0 until numberOfActiveBoluses) {
                bolusIDs.add(index, getNextInt(BluetoothGattCharacteristic.FORMAT_UINT16))
            }

            errorFreeParsing = true
        }
        return errorFreeParsing
    }

}

