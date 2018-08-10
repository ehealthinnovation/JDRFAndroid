package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT16
import android.bluetooth.BluetoothGattCharacteristic.FORMAT_UINT8
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.junit.Assert
import org.junit.Test
import kotlin.jvm.java

/**
 * Created by miantorno on 2018-06-26.
 */
class SystemIdCharacteristicTest : BaseTest() {

    private val systemIdAssignedNumber: Int = 0x2A23

    private val mockManufacturerIDFirst16: Int = 0xBCDE
    private val mockManufacturerIDSecond16: Int = 0xFE9A
    private val mockManufacturerIDLast8: Int = 0xFF
    private val mockManufacturerID: Long = 1099488214238

    private val mockOuiFirst16: Int = 0x3456
    private val mockOuiLast8: Int = 0x12
    private val mockOui: Long = 1193046

    private val nullVal: Int? = null

    /*
     * Example:
     * If the system ID is based of a Bluetooth Device Address with a Company Identifier (OUI) is
     * 0x123456 and the Company Assigned Identifier is 0x9ABCDE, then the System Identifier is
     * required to be 0x123456FFFE9ABCDE.
     */
    private val systemCharacteristicPayload = byteArrayOf(0xde.toByte(), 0xbc.toByte(), 0x9a.toByte(),
            0xfe.toByte(), 0xff.toByte(), 0x56.toByte(), 0x34.toByte(), 0x12.toByte())

    @Test
    fun testTag() {
        var testTagCharacteristic = SystemIdCharacteristic(null)
        Assert.assertEquals(SystemIdCharacteristic::class.java.canonicalName, testTagCharacteristic.tag)
    }

    @Test
    fun testAssignedNumber() {
        var testTagCharacteristic = SystemIdCharacteristic(null)
        Assert.assertEquals(systemIdAssignedNumber, testTagCharacteristic.uuid)
    }

    @Test
    fun testPartialParseFail() {
        val mockNullIDFirst16SourceCharacteristic = mock<BluetoothGattCharacteristic> {
            on { getIntValue(FORMAT_UINT16, 0) } doReturn nullVal
            on { getIntValue(FORMAT_UINT16, 2) } doReturn mockManufacturerIDSecond16
            on { getIntValue(FORMAT_UINT8, 4) } doReturn mockManufacturerIDLast8
            on { getIntValue(FORMAT_UINT16, 5) } doReturn mockOuiFirst16
            on { getIntValue(FORMAT_UINT8, 7) } doReturn mockOuiLast8
        }
        val nullIDFirst16 = SystemIdCharacteristic(mockNullIDFirst16SourceCharacteristic)
        Assert.assertFalse(nullIDFirst16.successfulParsing)

        val mockNullIdSecond16SourceCharacteristic = mock<BluetoothGattCharacteristic> {
            on { getIntValue(FORMAT_UINT16, 0) } doReturn mockManufacturerIDFirst16
            on { getIntValue(FORMAT_UINT16, 2) } doReturn nullVal
            on { getIntValue(FORMAT_UINT8, 4) } doReturn mockManufacturerIDLast8
            on { getIntValue(FORMAT_UINT16, 5) } doReturn mockOuiFirst16
            on { getIntValue(FORMAT_UINT8, 7) } doReturn mockOuiLast8
        }
        val nullIDsecond16 = SystemIdCharacteristic(mockNullIdSecond16SourceCharacteristic)
        Assert.assertFalse(nullIDsecond16.successfulParsing)

        val mockNullIDLast8SourceCharacteristic = mock<BluetoothGattCharacteristic> {
            on { getIntValue(FORMAT_UINT16, 0) } doReturn mockManufacturerIDFirst16
            on { getIntValue(FORMAT_UINT16, 2) } doReturn mockManufacturerIDSecond16
            on { getIntValue(FORMAT_UINT8, 4) } doReturn nullVal
            on { getIntValue(FORMAT_UINT16, 5) } doReturn mockOuiFirst16
            on { getIntValue(FORMAT_UINT8, 7) } doReturn mockOuiLast8
        }
        val nullIDLast8 = SystemIdCharacteristic(mockNullIDLast8SourceCharacteristic)
        Assert.assertFalse(nullIDLast8.successfulParsing)

        val mockNullOuiFisrt16SourceCharacteristic = mock<BluetoothGattCharacteristic> {
            on { getIntValue(FORMAT_UINT16, 0) } doReturn mockManufacturerIDFirst16
            on { getIntValue(FORMAT_UINT16, 2) } doReturn mockManufacturerIDSecond16
            on { getIntValue(FORMAT_UINT8, 4) } doReturn mockManufacturerIDLast8
            on { getIntValue(FORMAT_UINT16, 5) } doReturn nullVal
            on { getIntValue(FORMAT_UINT8, 7) } doReturn mockOuiLast8
        }
        val nullOuiFisrt16 = SystemIdCharacteristic(mockNullOuiFisrt16SourceCharacteristic)
        Assert.assertFalse(nullOuiFisrt16.successfulParsing)

        val mockNullOuiLast8SourceCharacteristic = mock<BluetoothGattCharacteristic> {
            on { getIntValue(FORMAT_UINT16, 0) } doReturn mockManufacturerIDFirst16
            on { getIntValue(FORMAT_UINT16, 2) } doReturn mockManufacturerIDSecond16
            on { getIntValue(FORMAT_UINT8, 4) } doReturn mockManufacturerIDLast8
            on { getIntValue(FORMAT_UINT16, 5) } doReturn mockOuiFirst16
            on { getIntValue(FORMAT_UINT8, 7) } doReturn nullVal
        }
        val nullOuiLast8 = SystemIdCharacteristic(mockNullOuiLast8SourceCharacteristic)
        Assert.assertFalse(nullOuiLast8.successfulParsing)
    }

    @Test
    fun getManufacturerIdentifier() {
        var validSystemIdCharacteristic = SystemIdCharacteristic(mockBTCharacteristic(systemCharacteristicPayload))
        Assert.assertEquals(mockManufacturerID, validSystemIdCharacteristic.manufacturerIdentifier)

        validSystemIdCharacteristic.manufacturerIdentifier = mockManufacturerID + 1
        Assert.assertEquals(mockManufacturerID + 1, validSystemIdCharacteristic.manufacturerIdentifier!!)
    }

    @Test
    fun getOui() {
        var validSystemIdCharacteristic = SystemIdCharacteristic(mockBTCharacteristic(systemCharacteristicPayload))
        Assert.assertEquals(mockOui, validSystemIdCharacteristic.oui)

        validSystemIdCharacteristic.oui = mockOui + 1
        Assert.assertEquals(mockOui + 1, validSystemIdCharacteristic.oui!!)
    }

    @Test
    fun testSuccessfulParse() {
        var validSystemIdCharacteristic = SystemIdCharacteristic(mockBTCharacteristic(systemCharacteristicPayload))
        Assert.assertTrue(validSystemIdCharacteristic.successfulParsing)
    }
}