package org.ehealthinnovation.jdrfandroidbleparser.idd.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic

class IddStatusReaderControlPoint(characteristic: BluetoothGattCharacteristic?, hasCrc:Boolean = false, hasE2eCounter : Boolean = false ) :
        BaseCharacteristic(characteristic, GattCharacteristic.IDD_STATUS_READER_CONTROL_POINT.assigned, hasCrc = hasCrc, hasE2eCounter = hasE2eCounter) {
    override val tag = IddStatusReaderControlPoint::class.java.canonicalName as String

    var
    override fun parse(c: BluetoothGattCharacteristic, hasE2eCounter: Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
