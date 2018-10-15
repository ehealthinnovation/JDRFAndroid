package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddcommandcontrolpointoperand

import android.bluetooth.BluetoothGattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.BolusType
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandParser
import org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.BaseOperandWriter

class Bolus: BaseOperandWriter {

    override val tag = Bolus::class.java.canonicalName as String

    constructor(): super()

    constructor(c: BluetoothGattCharacteristic) : super(c)

    var bolueType: BolusType? = null
    var bolusFastAmount: Float?= null
    var bolusExtendedAmount: Float?= null
    var bolusDuration: Int?= null

    override fun compose(): Boolean {
        if(!hasValidArguments()){
            return false
        }

        bolueType?.let { setIntValue(it.key, BluetoothGattCharacteristic.FORMAT_UINT8) }
        //todo  make sure the right exponent is used
        bolusFastAmount?.let { setFloatValue((it*10).toInt(), -1, BluetoothGattCharacteristic.FORMAT_SFLOAT) }
        bolusExtendedAmount?.let { setFloatValue((it*10).toInt(), -1, BluetoothGattCharacteristic.FORMAT_SFLOAT) }
        bolusDuration?.let { setIntValue(it, BluetoothGattCharacteristic.FORMAT_UINT16) }
        return true
    }

    override fun hasValidArguments(): Boolean = (bolueType!=null && bolusFastAmount!=null && bolusExtendedAmount!=null && bolusDuration!=null)
}