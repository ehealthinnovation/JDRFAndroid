package org.ehealthinnovation.jdrfandroidbleparser.idd.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.feature.Flags
import java.util.*

class IddFeatureCharacteristic(characteristic: BluetoothGattCharacteristic?, hasCrc: Boolean = false, hasE2eCounter: Boolean) :
        BaseCharacteristic(characteristic, GattCharacteristic.IDD_FEATURES.assigned, hasCrc = hasCrc, hasE2eCounter = hasE2eCounter){
    override val tag = IddFeatureCharacteristic::class.java.canonicalName as String

    /**
     * E2E-CRC storage for this particular characteristic
     */
    var e2eCrc: Int? = null

    /**
     * E2E-Counter
     */
    var e2eCounter: Int? = null

    /**
     * The Insulin Concentration field represents the concentration of insulin in the reservoir
     * information to identify the insulin concentration of the Insulin Delivery Device. The unit
     * of insulin concentration is international unit per milliliter (IU/mL). For example, U100
     * designates an insulin concentration of 100 international unit per milliliter of insulin
     * solution
     */
    var insulinConcentration: Float? = null

    /**
     * If the corresponding feature is supported, the Supported Feature bit shall be set to 1,
     * otherwise it shall be set to 0. This applies to the following features:
     */
    var flags: EnumSet<Flags>? = null


    override fun parse(c: BluetoothGattCharacteristic, hasE2eCounter: Boolean): Boolean {
        var errorFreeParsing = false
        var flagValue : Int = 0
        e2eCrc = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
        e2eCounter = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)
        insulinConcentration = getNextFloatValue(c, BluetoothGattCharacteristic.FORMAT_SFLOAT)
        //The feature flag consist of 24 bit, which is not a standard data field length.
        flagValue = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)
        flagValue += getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8) shl 8
        flagValue += getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8) shl 16

         Flags.parseFlags(flagValue).let {
             flags = it
            if(it.contains(Flags.E2E_PROTECTION_SUPPORTED)){
                if(!testCrc(rawData)){
                    throw Exception("CRC Fails")
                }
            }else{
                if(e2eCounter != 0 || e2eCrc != 0xFFFF){
                    return errorFreeParsing
                }
            }
        }
        errorFreeParsing = true

        return errorFreeParsing
    }

    /**
     * Query if a feature is supported as described in the packet
     * @param queryFeature the glucose meter feature to be queried
     * @return true if the [queryFeature] is supported, false otherwise
     * This function returns false even when the packet is not parsed successfully.
     * Before using this value, make sure [successfulParsing] is true.
     */
    fun isFeatureSupported(queryFeature: Flags): Boolean {
        return (flags?.contains(queryFeature) == true)
    }
}
