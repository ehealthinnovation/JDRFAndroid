package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.stringcharacteristics

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.common.StringCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic.*
import kotlin.jvm.java

/**
 * The value of this characteristic is a UTF-8 string representing the firmware revision for the
 * firmware within the device.
 *
 * Created by miantorno on 2018-06-22.
 */
class FirmwareRevisionStringCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        StringCharacteristic(characteristic, FIRMWARE_REVISION_STRING.assigned) {
    override val tag = FirmwareRevisionStringCharacteristic::class.java.canonicalName as String
}

/**
 * The value of this characteristic is a UTF-8 string representing the hardware revision for the
 * hardware within the device.
 *
 * Created by miantorno on 2018-06-22.
 */
class HardwareRevisionStringCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        StringCharacteristic(characteristic, HARDWARE_REVISION_STRING.assigned) {
    override val tag = HardwareRevisionStringCharacteristic::class.java.canonicalName as String
}

/**
 * The content of this characteristic is determined by the Authorizing Organization that provides
 * Certifications. Refer to 11073-20601 [1] or Continua Design Guidelines [2] for more information
 * on the format of this list.
 *
 * [1]	IEEE Std 11073-20601 ™- 2008 Health Informatics - Personal Health Device Communication -
 * Application Profile - Optimized Exchange Protocol - version 1.0 or later
 * [2]	Continua Design Guidelines - Continua Health Alliance; http://www.continuaalliance.org
 *
 * Created by miantorno on 2018-06-22.
 */
class IEEERegulatoryCertDataListCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        StringCharacteristic(characteristic, IEEE_11073_20601_REGULATORY_CERTIFICATION_DATA_LIST.assigned) {
    override val tag = IEEERegulatoryCertDataListCharacteristic::class.java.canonicalName as String
}

/**
 * The value of this characteristic is a UTF-8 string representing the name of the manufacturer of
 * the device.
 *
 * Created by miantorno on 2018-06-22.
 */
class ManufacturerNameStringCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        StringCharacteristic(characteristic, MANUFACTURER_NAME_STRING.assigned) {
    override val tag = ManufacturerNameStringCharacteristic::class.java.canonicalName as String
}

/**
 * The value of this characteristic is a UTF-8 string representing the model number assigned by the
 * device vendor.
 *
 * Created by miantorno on 2018-06-22.
 */
class ModelNumberStringCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        StringCharacteristic(characteristic, MODEL_NUMBER_STRING.assigned) {
    override val tag = ModelNumberStringCharacteristic::class.java.canonicalName as String
}

/**
 * The value of this characteristic is a variable-length UTF-8 string representing the serial
 * number for a particular instance of the device.
 *
 * Created by miantorno on 2018-06-22.
 */
class SerialNumberStringCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        StringCharacteristic(characteristic, SERIAL_NUMBER_STRING.assigned) {
    override val tag = SerialNumberStringCharacteristic::class.java.canonicalName as String
}

/**
 * The value of this characteristic is a UTF-8 string representing the software revision for the
 * software within the device.
 *
 * Created by miantorno on 2018-06-22.
 */
class SoftwareRevisionStringCharacteristic(characteristic: BluetoothGattCharacteristic?) :
        StringCharacteristic(characteristic, SOFTWARE_REVISION_STRING.assigned) {
    override val tag = SoftwareRevisionStringCharacteristic::class.java.canonicalName as String
}