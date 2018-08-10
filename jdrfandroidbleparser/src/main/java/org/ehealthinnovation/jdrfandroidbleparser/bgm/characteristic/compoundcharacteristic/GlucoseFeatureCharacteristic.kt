package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.feature.Flags
import java.util.*

/**
 * The Glucose Feature characteristic is used to describe the supported features of the
 * Server. When read, the Glucose Feature characteristic returns a value that is used by
 * a Client to determine the supported features of the Server.
 *
 * https://www.bluetooth.com/specifications/gatt/viewer?attributeXmlFile=org.bluetooth.characteristic.glucose_feature.xml
 *
 * @author harryqiu
 */

class GlucoseFeatureCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        BaseCharacteristic(characteristic, GattCharacteristic.GLUCOSE_FEATURE.assigned) {

    override val tag = GlucoseFeatureCharacteristic::class.java.canonicalName as String

    /**
     * The set of feature flags contained in the binary packet.
     * Use [isFeatureSupported] to query if a device supports a certain feature.
     */
    private var flags: EnumSet<Flags>? = null

    override fun parse(c: BluetoothGattCharacteristic): Boolean {
        var errorFreeParse = false

        flags = Flags.parseFlags(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16))

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