package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.common

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic

/**
 * Many base Bluetooth Characteristics not only have the requirement that they must decode a given
 * [BluetoothGattCharacteristic] to primitive data types, they must also be able to do the reverse
 * as well. These characteristics, must implement the [Composable] interface.
 * [BaseCharacteristic] classes can just extend this class and not worry about implementation.
 *
 * Created by miantorno on 2018-06-19.
 */
interface Composable {

    /**
     * Compose a given characteristic into the resulting [ByteArray].
     */
    @Throws(IllegalStateException::class)
    fun composeCharacteristic(): ByteArray

}