package org.ehealthinnovation.jdrfandroidbleparser.utility

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import kotlin.jvm.internal.Ref

class CrcHelperTest {
    /**
     * Test Packet 1
     * Null package
     */
    val testByteArray1 : ByteArray = byteArrayOf(0x00, 0x00)
    val expectedCrc1 : Short = 0xf0b8.toShort();

    /**
     * Test Pakcet 2
     * Three bytes
     */
    val testByteArray2 : ByteArray = byteArrayOf(0x01, 0x00, 0x00)
    val expectedCrc2 : Short = 0x63ef;

    /**
     * Test Packet 3
     * A Glucose Measurement
     */
    val testByteArray3 : ByteArray = byteArrayOf(0x13, 0x01, 0x00, 0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x35, 0x0e, 0x00, 0x00, 0x54, 0xb0.toByte(), 0x19)
    val expectedCrc3 : Short = 0x8368.toShort();

    /**
     * Test Packet 4
     * A Glucose Measurement
     */
    val testByteArray4 : ByteArray = byteArrayOf(0x03, 0x02, 0x00, 0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x36, 0x12, 0x10, 0x00, 0x60, 0xb0.toByte(), 0x11.toByte())
    val expectedCrc4 : Short = 0xa45f.toShort();

    /**
     * Test Packet 5
     * A Glucose Measurement
     */
    val testByteArray5 : ByteArray = byteArrayOf(0x03, 0x03, 0x00, 0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x36, 0x2e, 0x00, 0x00, 0x75, 0xb0.toByte(), 0x12.toByte())
    val expectedCrc5 : Short = 0x1662.toShort();

    /**
     * Test Packet 6
     * Test verifier for two null bytes
     */
    val testByteArray6 : ByteArray = byteArrayOf(0x00, 0x00, 0xb8.toByte(), 0xf0.toByte())
    val expectedResult6 : Boolean = true

    /**
     * Test Packet 7
     * Test verifier for three null bytes
     */
    val testByteArray7 : ByteArray = byteArrayOf(0x01, 0x00, 0x00, 0xef.toByte(), 0x63)
    val expectedResult7 : Boolean = true

    /**
     * Test Packet 8
     * Test verifier for glucose measurement packet
     */
    val testByteArray8 : ByteArray = byteArrayOf(0x13, 0x01, 0x00, 0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x35, 0x0e, 0x00, 0x00, 0x54, 0xb0.toByte(), 0x19.toByte(), 0x68, 0x83.toByte())
    val expectedResult8 : Boolean = true

    /**
     * Test Packet 9
     * Test verifier for glucose measurement packet
     */
    val testByteArray9 : ByteArray = byteArrayOf(0x03, 0x02, 0x00, 0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x36, 0x12, 0x10, 0x00, 0x60, 0xb0.toByte(), 0x11.toByte(), 0x5f, 0xa4.toByte())
    val expectedResult9 : Boolean = true

    /**
     * Test Packet 10
     * Test verifier for glucose measurement packet
     */
    val testByteArray10 : ByteArray = byteArrayOf(0x03, 0x03, 0x00, 0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x36, 0x2e, 0x00, 0x00, 0x75, 0xb0.toByte(), 0x12.toByte(), 0x62, 0x16)
    val expectedResult10 : Boolean = true

    /**
     * Test Packet 11
     * Test Verifier for three bytes, negative case
     */
    val testByteArray11 : ByteArray = byteArrayOf(0x01, 0x00, 0x00, 0xef.toByte(), 0x64)
    val expectedResult11 : Boolean = false

    /**
     * Test Packet 12
     * Test Verifier for glucose measurement negative case
     */
    val testByteArray12 : ByteArray = byteArrayOf(0x13, 0x01, 0x00, 0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x35, 0x0e, 0x00, 0x00, 0x54, 0xb0.toByte(), 0x19.toByte(), 0x68, 0x82.toByte())
    val expectedResult12 : Boolean = false

    /**
     * Test Packet 13
     * Test Verifier for glucose measurement negative case
     */
    val testByteArray13 : ByteArray = byteArrayOf(0x03, 0x02, 0x00, 0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x36, 0x12, 0x10, 0x00, 0x60, 0xb0.toByte(), 0x11.toByte(), 0x5f, 0xa5.toByte())
    val expectedResult13 : Boolean = false;

    /**
     * Test Pakcet 14
     * Test Verifier for glucose measurement negative case
     */
    val testByteArray14 : ByteArray = byteArrayOf(0x03, 0x03, 0x00, 0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x36, 0x2e, 0x00, 0x00, 0x75, 0xb0.toByte(), 0x12.toByte(), 0x62, 0x17)
    val expectedResult14 : Boolean  = false;

    @Test
    fun testTag() {
        Assert.assertEquals(CrcHelper::class.java.canonicalName as String, CrcHelper.tag)
    }

    @Test
    fun testGeneratingCrcCode(){
        Assert.assertEquals(expectedCrc1, CrcHelper.calculateCcittCrc16(testByteArray1, testByteArray1.size))
        Assert.assertEquals(expectedCrc2, CrcHelper.calculateCcittCrc16(testByteArray2, testByteArray2.size))
        Assert.assertEquals(expectedCrc3, CrcHelper.calculateCcittCrc16(testByteArray3, testByteArray3.size))
        Assert.assertEquals(expectedCrc4, CrcHelper.calculateCcittCrc16(testByteArray4, testByteArray4.size))
        Assert.assertEquals(expectedCrc5, CrcHelper.calculateCcittCrc16(testByteArray5, testByteArray5.size))
    }

    @Test
    fun testVerifyCrcAppendedPackets(){
        Assert.assertEquals(expectedResult6, CrcHelper.testCcittCrc16(testByteArray6, testByteArray6.size))
        Assert.assertEquals(expectedResult7, CrcHelper.testCcittCrc16(testByteArray7, testByteArray7.size))
        Assert.assertEquals(expectedResult8, CrcHelper.testCcittCrc16(testByteArray8, testByteArray8.size))
        Assert.assertEquals(expectedResult9, CrcHelper.testCcittCrc16(testByteArray9, testByteArray9.size))
        Assert.assertEquals(expectedResult10, CrcHelper.testCcittCrc16(testByteArray10, testByteArray10.size))
        Assert.assertEquals(expectedResult11, CrcHelper.testCcittCrc16(testByteArray11, testByteArray11.size))
        Assert.assertEquals(expectedResult12, CrcHelper.testCcittCrc16(testByteArray12, testByteArray12.size))
        Assert.assertEquals(expectedResult13, CrcHelper.testCcittCrc16(testByteArray13, testByteArray13.size))
        Assert.assertEquals(expectedResult14, CrcHelper.testCcittCrc16(testByteArray14, testByteArray14.size))
    }
}