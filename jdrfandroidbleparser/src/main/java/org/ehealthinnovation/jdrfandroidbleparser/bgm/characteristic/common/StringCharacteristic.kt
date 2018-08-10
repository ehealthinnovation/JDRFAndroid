package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.common

import android.bluetooth.BluetoothGattCharacteristic
import kotlin.jvm.java

/**
 * Many base Bluetooth Characteristics only have one [String] value associated with them. These
 * [BaseCharacteristic] classes can just extend this class and not worry about implementation.
 *
 * Created by miantorno on 2018-06-19.
 */
open class StringCharacteristic(characteristic: BluetoothGattCharacteristic?, uuid: Int) :
        BaseCharacteristic(characteristic, uuid) {
    override val tag = StringCharacteristic::class.java.canonicalName as String
    var valueString: String? = null

    override fun parse(c: BluetoothGattCharacteristic): Boolean {
        valueString = getNextStringValue(c)
        return valueString != null
    }
}