package org.ehealthinnovation.jdrfandroidbleparser.cgm.characteristic

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.sessionstarttime.DstOffset
import org.ehealthinnovation.jdrfandroidbleparser.utility.BluetoothDateTime
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class CgmSessionStartTimeTest: BaseTest(){
    /**
     * Packet 1
     * Standard time, no dst no crc
     */
    private val testPacket1 = byteArrayOf(0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x35, 0x0e, 0x00, 0x00)
    private val expectedParseResult1 = true
    private val expectedTime1 = BluetoothDateTime(2018,6,6,14,53,14)
    private val expectedDst1 = DstOffset.STANDARD_TIME
    private val expectedTimeZone1 = 0

    /**
     * Packet 2
     * Maximum negative timezone , no dst no Crc
     */
    private val testPacket2 = byteArrayOf(0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x35, 0x0e, 0xD0.toByte(), 0x00)
    private val expectedParseResult2 = true
    private val expectedTime2 = BluetoothDateTime(2018,6,6,14,53,14)
    private val expectedDst2 = DstOffset.STANDARD_TIME
    private val expectedTimeZone2 = -48

    /**
     * Packet 3
     * Max positive timezone, no dst, no crc
     */
    private val testPacket3 = byteArrayOf(0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x35, 0x0e, 0x38.toByte(), 0x00)
    private val expectedParseResult3 = true
    private val expectedTime3 = BluetoothDateTime(2018,6,6,14,53,14)
    private val expectedDst3 = DstOffset.STANDARD_TIME
    private val expectedTimeZone3 = 56

    /**
     * Packet 4
     * standard unknown timezone, no dst, no crc
     */
    private val testPacket4 = byteArrayOf(0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x35, 0x0e, 0x80.toByte(), 0x00)
    private val expectedParseResult4 = true
    private val expectedTime4 = BluetoothDateTime(2018,6,6,14,53,14)
    private val expectedDst4 = DstOffset.STANDARD_TIME
    private val expectedTimeZone4 = -128

    /**
     * Packet 5
     * Standard Timezone, one hour dst, no crc
     */
    private val testPacket5 = byteArrayOf(0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x35, 0x0e, 0x00.toByte(), 0x04)
    private val expectedParseResult5 = true
    private val expectedTime5 = BluetoothDateTime(2018,6,6,14,53,14)
    private val expectedDst5 = DstOffset.DAYLIGHT_TIME
    private val expectedTimeZone5 = 0

    /**
     * Packet 6
     * Uknown Year, Standard Timezone, one hour dst, no crc
     */
    private val testPacket6 = byteArrayOf(0.toByte(), 0, 0x06, 0x06, 0x0e, 0x35, 0x0e, 0x00.toByte(), 0x04)
    private val expectedParseResult6 = true
    private val expectedTime6 = BluetoothDateTime(0,6,6,14,53,14)
    private val expectedDst6 = DstOffset.DAYLIGHT_TIME
    private val expectedTimeZone6 = 0

    /**
     * Packet 7
     * Unknown month, standard time, one hour dst, no crc
     */
    private val testPacket7 = byteArrayOf(0xe2.toByte(), 0x07, 0x00, 0x06, 0x0e, 0x35, 0x0e, 0x00.toByte(), 0x04)
    private val expectedParseResult7 = true
    private val expectedTime7 = BluetoothDateTime(2018,0,6,14,53,14)
    private val expectedDst7 = DstOffset.DAYLIGHT_TIME
    private val expectedTimeZone7 = 0

    /**
     * Packet 8
     * Unknown Day, standard time, one hour dst, no crc
     */

    private val testPacket8 = byteArrayOf(0xe2.toByte(), 0x07, 0x00, 0x06, 0x0e, 0x35, 0x0e, 0x00.toByte(), 0x04)
    private val expectedParseResult8 = true
    private val expectedTime8 = BluetoothDateTime(2018,0,6,14,53,14)
    private val expectedDst8 = DstOffset.DAYLIGHT_TIME
    private val expectedTimeZone8 = 0

    @Test
    fun testParseSuccessfully(){
        Assert.assertEquals(expectedParseResult1, CgmSessionStartTime(mockBTCharacteristic(testPacket1)).successfulParsing)
        Assert.assertEquals(expectedParseResult2, CgmSessionStartTime(mockBTCharacteristic(testPacket2)).successfulParsing)
        Assert.assertEquals(expectedParseResult3, CgmSessionStartTime(mockBTCharacteristic(testPacket3)).successfulParsing)
        Assert.assertEquals(expectedParseResult4, CgmSessionStartTime(mockBTCharacteristic(testPacket4)).successfulParsing)
        Assert.assertEquals(expectedParseResult5, CgmSessionStartTime(mockBTCharacteristic(testPacket5)).successfulParsing)
        Assert.assertEquals(expectedParseResult6, CgmSessionStartTime(mockBTCharacteristic(testPacket6)).successfulParsing)
        Assert.assertEquals(expectedParseResult7, CgmSessionStartTime(mockBTCharacteristic(testPacket7)).successfulParsing)
        Assert.assertEquals(expectedParseResult8, CgmSessionStartTime(mockBTCharacteristic(testPacket8)).successfulParsing)

    }

    @Test
    fun testParseDate(){
        Assert.assertEquals(expectedTime1, CgmSessionStartTime(mockBTCharacteristic(testPacket1)).sessionStartTime)
        Assert.assertEquals(expectedTime2, CgmSessionStartTime(mockBTCharacteristic(testPacket2)).sessionStartTime)
        Assert.assertEquals(expectedTime3, CgmSessionStartTime(mockBTCharacteristic(testPacket3)).sessionStartTime)
        Assert.assertEquals(expectedTime4, CgmSessionStartTime(mockBTCharacteristic(testPacket4)).sessionStartTime)
        Assert.assertEquals(expectedTime5, CgmSessionStartTime(mockBTCharacteristic(testPacket5)).sessionStartTime)
        Assert.assertEquals(expectedTime6, CgmSessionStartTime(mockBTCharacteristic(testPacket6)).sessionStartTime)
        Assert.assertEquals(expectedTime7, CgmSessionStartTime(mockBTCharacteristic(testPacket7)).sessionStartTime)
        Assert.assertEquals(expectedTime8, CgmSessionStartTime(mockBTCharacteristic(testPacket8)).sessionStartTime)
    }

    @Test
    fun testParseDst(){
        Assert.assertEquals(expectedDst1, CgmSessionStartTime(mockBTCharacteristic(testPacket1)).dstOffset)
        Assert.assertEquals(expectedDst2, CgmSessionStartTime(mockBTCharacteristic(testPacket2)).dstOffset)
        Assert.assertEquals(expectedDst3, CgmSessionStartTime(mockBTCharacteristic(testPacket3)).dstOffset)
        Assert.assertEquals(expectedDst4, CgmSessionStartTime(mockBTCharacteristic(testPacket4)).dstOffset)
        Assert.assertEquals(expectedDst5, CgmSessionStartTime(mockBTCharacteristic(testPacket5)).dstOffset)
        Assert.assertEquals(expectedDst6, CgmSessionStartTime(mockBTCharacteristic(testPacket6)).dstOffset)
        Assert.assertEquals(expectedDst7, CgmSessionStartTime(mockBTCharacteristic(testPacket7)).dstOffset)
        Assert.assertEquals(expectedDst8, CgmSessionStartTime(mockBTCharacteristic(testPacket8)).dstOffset)
    }

    @Test
    fun testParseTimeZone(){
        Assert.assertEquals(expectedTimeZone1, CgmSessionStartTime(mockBTCharacteristic(testPacket1)).timeZone)
        Assert.assertEquals(expectedTimeZone2, CgmSessionStartTime(mockBTCharacteristic(testPacket2)).timeZone)
        Assert.assertEquals(expectedTimeZone3, CgmSessionStartTime(mockBTCharacteristic(testPacket3)).timeZone)
        Assert.assertEquals(expectedTimeZone4, CgmSessionStartTime(mockBTCharacteristic(testPacket4)).timeZone)
        Assert.assertEquals(expectedTimeZone5, CgmSessionStartTime(mockBTCharacteristic(testPacket5)).timeZone)
        Assert.assertEquals(expectedTimeZone6, CgmSessionStartTime(mockBTCharacteristic(testPacket6)).timeZone)
        Assert.assertEquals(expectedTimeZone7, CgmSessionStartTime(mockBTCharacteristic(testPacket7)).timeZone)
        Assert.assertEquals(expectedTimeZone8, CgmSessionStartTime(mockBTCharacteristic(testPacket8)).timeZone)

    }
}