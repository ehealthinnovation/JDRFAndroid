package org.ehealthinnovation.jdrfandroidbleparser.cgm.characteristic

import android.hardware.Sensor
import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.measurement.SensorStatusAnnunciation
import org.ehealthinnovation.jdrfandroidbleparser.utility.CrcHelper
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class CgmStatusCharacteristicTest : BaseTest() {
    /**
     * Test packet 1
     * Normal test case without crc
     */
    private val testPacket1: ByteArray = byteArrayOf(0x00,0x01,0x01,0x01,0x01)
    private val expectedParseResult1: Boolean = true
    private val expectedTimeOffset1 : Int = 256
    private val expectedFlagSet1 : EnumSet<SensorStatusAnnunciation> = EnumSet.of(
            SensorStatusAnnunciation.SESSION_STOPPED,
            SensorStatusAnnunciation.SENSOR_RESULT_LOWER_THAN_THE_PATIENT_LOW_LEVEL,
            SensorStatusAnnunciation.TIME_SYNCHRONIZATION_BETWEEN_SENSOR_AND_COLLECTOR_REQUIRED
    )

    /**
     * Test packet 2
     * Normal failed test case without crc
     */
    private val testPacket2: ByteArray = byteArrayOf(0x00,0x01,0x01,0x01)
    private val expectedParseResult2: Boolean = false

    /**
     * Test packet 3
     * Normal test case (successful) with crc
     */
    private val testPacket3: ByteArray = byteArrayOf(0x00,0x01,0x01,0x01,0x01, 0xbe.toByte(), 0x7e)
    private val expectedParseResult3: Boolean = true
    private val expectedTimeOffset3 : Int = 256
    private val expectedFlagSet3 : EnumSet<SensorStatusAnnunciation> = EnumSet.of(
            SensorStatusAnnunciation.SESSION_STOPPED,
            SensorStatusAnnunciation.SENSOR_RESULT_LOWER_THAN_THE_PATIENT_LOW_LEVEL,
            SensorStatusAnnunciation.TIME_SYNCHRONIZATION_BETWEEN_SENSOR_AND_COLLECTOR_REQUIRED
    )

    /**
     * Test packet 4
     * Normalfaild test case with CRC
     */
    private val testPacket4: ByteArray = byteArrayOf(0x00,0x01,0x01,0x01,0x01, 0xbe.toByte(), 0x7d)
    private val expectedParseResult4: Boolean = false

    @Test
    fun testParseResult(){
        Assert.assertEquals(expectedParseResult1, CgmStatusCharacteristic(mockBTCharacteristic(testPacket1)).successfulParsing)
        Assert.assertEquals(expectedParseResult2, CgmStatusCharacteristic(mockBTCharacteristic(testPacket2)).successfulParsing)
        Assert.assertEquals(expectedParseResult3, CgmStatusCharacteristic(mockBTCharacteristic(testPacket3), true).successfulParsing)
        Assert.assertEquals(expectedParseResult4, CgmStatusCharacteristic(mockBTCharacteristic(testPacket4), true).successfulParsing)
    }

    @Test
    fun testTimeOffset(){
        Assert.assertEquals(expectedTimeOffset1, CgmStatusCharacteristic(mockBTCharacteristic(testPacket1)).timeOffset)
        Assert.assertEquals(expectedTimeOffset3, CgmStatusCharacteristic(mockBTCharacteristic(testPacket3)).timeOffset)

    }

    @Test
    fun testStatusFlag(){
        Assert.assertEquals(expectedFlagSet1, CgmStatusCharacteristic(mockBTCharacteristic(testPacket1)).cgmStatus)
        Assert.assertEquals(expectedFlagSet3, CgmStatusCharacteristic(mockBTCharacteristic(testPacket3)).cgmStatus)
    }


}