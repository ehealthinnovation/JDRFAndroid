package org.ehealthinnovation.jdrfandroidbleparser.idd.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.common.BaseCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.annunciationstatus.AnnunciationStatusValues
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.annunciationstatus.AnnunciationType
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.annunciationstatus.Flags
import java.util.*

class IddAnnunciationStatusCharacteristic(characteristic: BluetoothGattCharacteristic?, hasCrc: Boolean = false, hasE2eCounter: Boolean) :
        BaseCharacteristic(characteristic, GattCharacteristic.IDD_ANNUNCIATION_STATUS.assigned, hasCrc = hasCrc, hasE2eCounter = hasE2eCounter) {

    override val tag = IddAnnunciationStatusCharacteristic::class.java.canonicalName as String

    /**
     * The Flags field indicates the presence of the annunciation and optional AuxInfo fields in the
     * IDD Annunciation Status record.
     */
    var flags: EnumSet<Flags>? = null

    /**
     * If the Annunciation Present bit of the Flags field is set to 1, the Annunciation Instance ID
     * field shall be included in the IDD Annunciation Status characteristics.The Annunciation
     * Instance ID field is a unique identifier created by the Server application for all
     * annunciations that occur in the course of time.
     */
    var annunciationInstanceId: Int? = null

    /**
     * If the Annunciation Present bit of the Flags field is set to 1, the Annunciation Type field
     * shall be included in the IDD Annunciation Status characteristics. This Annunciation Type
     * field describes the kind of annunciation in the scope of the Server application.
     */
    var annunciationType: AnnunciationType? = null

    /**
     * If the Annunciation Present bit of the Flags field is set to 1, the Annunciation Status
     * field shall be included in the IDD Annunciation Status characteristics. This Annunciation
     * Status field represents the current status of an annunciation.
     */
    var annunciationStatus: AnnunciationStatusValues? = null

    /**
     * The AuxInfo fields describe details of an annunciation (e.g., to display additional
     * information to the user). The underlying data and data format of these fields are defined by
     * the Server Application. See Section 9 for different examples of how these fields could be
     * used by the manufacturer of an Insulin Delivery Device.
     */
    var auxInfo: MutableList<Int>? = null

    /**
     * E2E Counter
     */
    var e2eCounter: Int? = null


    override fun parse(c: BluetoothGattCharacteristic, hasE2eCounter: Boolean): Boolean {
        var errorFreeParse = false

        auxInfo = mutableListOf()

        flags = Flags.parseFlags(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))
        flags?.let { flags ->
            if (flags.size == 0) {
                errorFreeParse = true
            } else if (flags.contains(Flags.ANNUNCIATION_PRESENT)) {
                annunciationInstanceId = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16)
                annunciationType = AnnunciationType.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16))
                annunciationStatus = AnnunciationStatusValues.fromKey(getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8))

                if (flags.contains(Flags.AUXINFO1_PRESENT)) {
                    auxInfo?.let { it.add(0, getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16))}
                }

                if (flags.contains(Flags.AUXINFO2_PRESENT)) {
                    auxInfo?.let { it.add(1, getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16))}
                }

                if (flags.contains(Flags.AUXINFO3_PRESENT)) {
                    auxInfo?.let { it.add(2, getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16))}
                }

                if (flags.contains(Flags.AUXINFO4_PRESENT)) {
                    auxInfo?.let { it.add(3, getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16))}
                }

                if (flags.contains(Flags.AUXINFO5_PRESENT)) {
                    auxInfo?.let { it.add(4, getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT16))}
                }

                if (hasE2eCounter) {
                    e2eCounter = getNextIntValue(c, BluetoothGattCharacteristic.FORMAT_UINT8)
                }

                errorFreeParse = true
            }
        }
        return errorFreeParse
    }
}
