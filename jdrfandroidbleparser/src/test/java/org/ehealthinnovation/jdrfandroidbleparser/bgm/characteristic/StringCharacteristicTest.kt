package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic

import android.bluetooth.BluetoothGattCharacteristic
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.common.StringCharacteristic
import org.junit.Assert
import org.junit.Test
import kotlin.jvm.java

class StringCharacteristicTest : BaseTest() {

    private val gattIdentifier = 0x6D69
    private val valueString = "markiantorno"
    private val valueBytes = byteArrayOf(23, 6, 83)

    @Test
    fun getTAG() {
        val stringCharacteristic = StringCharacteristic(null, gattIdentifier)
        Assert.assertEquals(StringCharacteristic::class.java.canonicalName,
                stringCharacteristic.tag)
    }

    @Test
    fun testValueString() {
        val stringCharacteristic = StringCharacteristic(null, gattIdentifier)
        val testString = "picard"
        stringCharacteristic.valueString = testString
        Assert.assertEquals(testString, stringCharacteristic.valueString)
    }

    @Test
    fun parseNullCharacteristic() {
        val stringCharacteristic = StringCharacteristic(null, gattIdentifier)
        Assert.assertFalse(stringCharacteristic.successfulParsing)
    }

    @Test
    fun parseValidCharacteristicWithNullOrOutOfRangeValue() {
        val mockCharacteristic = mock<BluetoothGattCharacteristic> {
            val nullString: String? = null
            on { getStringValue(0) } doReturn nullString
        }
        val stringCharacteristic = StringCharacteristic(mockCharacteristic, gattIdentifier)
        Assert.assertFalse(stringCharacteristic.successfulParsing)
    }

    @Test
    fun parseValidCharacteristic() {
        val mockCharacteristic = mock<BluetoothGattCharacteristic> {
            on { getStringValue(0) } doReturn valueString
            on { getValue() } doReturn valueBytes
        }
        val stringCharacteristic = StringCharacteristic(mockCharacteristic, gattIdentifier)

        Assert.assertTrue(stringCharacteristic.successfulParsing)
        Assert.assertEquals(valueString, stringCharacteristic.valueString)
        Assert.assertEquals(gattIdentifier, stringCharacteristic.uuid)
        Assert.assertArrayEquals(valueBytes, stringCharacteristic.rawData)
    }
}