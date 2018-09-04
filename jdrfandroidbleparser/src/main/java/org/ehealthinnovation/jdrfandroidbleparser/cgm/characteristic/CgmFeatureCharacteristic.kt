package org.ehealthinnovation.jdrfandroidbleparser.cgm.characteristic

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.feature.CgmSampleLocation
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.feature.CgmType
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.feature.Flags
import java.lang.reflect.Type
import java.util.*

/**
 * The CGM Feature characteristic is used to describe the supported features of the Server.
 * When read, the CGM Feature characteristic returns a value that is used by a Client to determine
 * the supported features of the Server. Additionally, the CGM Feature contains the CGM Type-Sample
 * Location field: This field is the combination of the Type field and the Sample Location field and
 * is static for a CGM sensor.
 * https://www.bluetooth.com/specifications/gatt/viewer?attributeXmlFile=org.bluetooth.characteristic.cgm_feature.xml
 */
class CgmFeatureCharacteristic(characteristic: BluetoothGattCharacteristic?) :BaseCharacteristic(characteristic, GattCharacteristic.CGM_FEATURE.assigned, false) {

    override val tag = CgmFeatureCharacteristic::class.java.canonicalName as String

    /**
     * The set of feature flags contained in the binary packet.
     * Use [isFeatureSupported] to query if a device supports a certain feature.
     */
    private var flags: EnumSet<Flags>? = null

    /**
     * The location and type of the cgm sample.
     */
    var cgmType : CgmType? = null
    var cgmSampleLocation : CgmSampleLocation? = null

    /**
     * The parsing of this characteristic consists of two passes. The pass determine if CRC is present
     * The second pass verify that the CRC is right if it is present.
     */
    override fun parse(c: BluetoothGattCharacteristic): Boolean {
        var errorFreeParse = false
        var flagValue : Int = 0
        //The feature flag consist of 24 bit, which is not a standard data field length.
        flagValue = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)
        flagValue += getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8) shl 8
        flagValue += getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8) shl 16
        Flags.parseFlags(flagValue).let {
            if(it.contains(Flags.E2E_CRC_SUPPORTED)){
                if(!testCrc(rawData)){
                    throw Exception("CRC Fails")
                }
            }else{
                flags = it
            }
        }

        val temporalSampleTypeHolder = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)
        cgmType = CgmType.fromKey(temporalSampleTypeHolder and 0x0F)
        cgmSampleLocation = CgmSampleLocation.fromKey((temporalSampleTypeHolder and 0xF0) shr 4)

        errorFreeParse = true
        return errorFreeParse
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