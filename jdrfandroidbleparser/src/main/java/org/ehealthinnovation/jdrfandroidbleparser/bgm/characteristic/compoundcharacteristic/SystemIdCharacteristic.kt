package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import kotlin.jvm.java

/**
 * The SYSTEM ID characteristic consists of a structure with two fields. The first field are the
 * LSOs and the second field contains the MSOs. This is a 64-bit structure which consists of a
 * 40-bit manufacturer-defined identifier concatenated with a 24 bit unique Organizationally Unique
 * Identifier (OUI). The OUI is issued by the IEEE Registration Authority
 * (http://standards.ieee.org/regauth/index.html) and is required to be used in accordance with
 * IEEE Standard 802-2001.6 while the least significant 40 bits are manufacturer defined.
 *
 * If System ID generated based on a Bluetooth Device Address, it is required to be done as follows.
 * System ID and the Bluetooth Device Address have a very similar structure: a Bluetooth Device
 * Address is 48 bits in length and consists of a 24 bit Company Assigned Identifier (manufacturer
 * defined identifier) concatenated with a 24 bit Company Identifier (OUI). In order to encapsulate
 * a Bluetooth Device Address as System ID, the Company Identifier is concatenated with 0xFFFE
 * followed by the Company Assigned Identifier of the Bluetooth Address. For more guidelines
 * related to EUI-64, refer to http://standards.ieee.org/develop/regauth/tut/eui64.pdf.
 *
 * Created by miantorno on 2018-06-25.
 */
class SystemIdCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        BaseCharacteristic(characteristic, GattCharacteristic.SYSTEM_ID.assigned) {
    override val tag = SystemIdCharacteristic::class.java.canonicalName as String

    // Company Assigned Identifier
    var manufacturerIdentifier: Long? = null
    // Bluetooth Device Address with a Company Identifier (OUI)
    var oui: Long? = null

    override fun parse(c: BluetoothGattCharacteristic): Boolean {
        manufacturerIdentifier = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16).toLong() +
                (getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16).toLong() shl 16) +
                (getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8).toLong() shl 32)
        oui = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16).toLong() +
                (getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8).toLong() shl 16)
        return true
    }
}