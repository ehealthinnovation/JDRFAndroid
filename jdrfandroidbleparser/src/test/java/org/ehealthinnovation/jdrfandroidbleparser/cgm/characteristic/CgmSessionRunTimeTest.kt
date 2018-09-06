package org.ehealthinnovation.jdrfandroidbleparser.cgm.characteristic

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class CgmSessionRunTimeTest : BaseTest(){
    /**
     * Test Packet 1
     * Normal case without CRC
     */
    private val testPacket1 = byteArrayOf(0x01, 0x02)
    private val hasCrc1 = false
    private val expectedParseResult1 = true
    private val expectedSessionRunTime1 = 0x0201

    /**
     * Test Packet 2
     * Normal case with CRC
     */
    private val testPacket2 = byteArrayOf(0x01, 0x02, 0x72, 0xca.toByte())
    private val hasCrc2 = true
    private val expectedParseResult2 = true
    private val expectedSessionRunTime2 = 0x0201


    /**
     * Test Packet 2
     * Normal case with failed CRC
     */
    private val testPacket3 = byteArrayOf(0x01, 0x02, 0x72, 0xcb.toByte())
    private val hasCrc3 = true
    private val expectedParseResult3 = false
    private val expectedSessionRunTime3 = null


    @Test
    fun testParseSuccessfully(){
        Assert.assertEquals(expectedParseResult1, CgmSessionRunTime(mockBTCharacteristic(testPacket1),hasCrc1).successfulParsing)
        Assert.assertEquals(expectedParseResult2, CgmSessionRunTime(mockBTCharacteristic(testPacket2),hasCrc2).successfulParsing)
        Assert.assertEquals(expectedParseResult3, CgmSessionRunTime(mockBTCharacteristic(testPacket3),hasCrc3).successfulParsing)
    }

    @Test
    fun testSessionRunTime(){
        Assert.assertEquals(expectedSessionRunTime1, CgmSessionRunTime(mockBTCharacteristic(testPacket1),hasCrc1).sessionRunTime)
        Assert.assertEquals(expectedSessionRunTime2, CgmSessionRunTime(mockBTCharacteristic(testPacket2),hasCrc2).sessionRunTime)
        Assert.assertEquals(expectedSessionRunTime3, CgmSessionRunTime(mockBTCharacteristic(testPacket3),hasCrc3).sessionRunTime)
    }

}