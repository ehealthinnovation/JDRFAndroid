package org.ehealthinnovation.jdrfandroidbleparser.cgm.characteristic

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic.GlucoseFeatureCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.feature.CgmSampleLocation
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.feature.CgmType
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.feature.Flags
import org.junit.After
import org.junit.Assert
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class CgmFeatureCharacteristicTest : BaseTest(){

    /**
     * Test packet 1
     * A normal CGM Feature packet with no CRC
     */
    private val testPacket1 = byteArrayOf(0b01010101,0b10101010.toByte(),0b10101010.toByte(), 0x52)
    private val expectedFlagSet1 = EnumSet.of(
            Flags.CALIBRATION_SUPPORTED,
            Flags.HYPO_ALERTS_SUPPORTED,
            Flags.RATE_OF_INCREASE_DECREASE_ALERTS_SUPPORTED,
            Flags.SENSOR_MALFUNCTION_DETECTION_SUPPORTED,
            Flags.LOW_BATTERY_DETECTION_SUPPORTED,
            Flags.GENERAL_DEVICE_FAULT_SUPPORTED,
            Flags.MULTIPLE_BOND_SUPPORTED,
            Flags.CGM_TREND_INFORMATION_SUPPORTED
    )

    private val expectedType1 = CgmType.CAPILLARY_PLASMA
    private val expectedSampleLocation1 = CgmSampleLocation.SUBCUTANEOUS_TISSUE

    /**
     * Test Packet 2
     * A normal CGM feature packet with correct CRC
     */
    private val testPacket2 = byteArrayOf(0b01010101,0b10111010.toByte(),0b10101010.toByte(), 0x52, 0x40.toByte(), 0x62.toByte())
    private val expectedFlagSet2 = EnumSet.of(
            Flags.CALIBRATION_SUPPORTED,
            Flags.HYPO_ALERTS_SUPPORTED,
            Flags.RATE_OF_INCREASE_DECREASE_ALERTS_SUPPORTED,
            Flags.SENSOR_MALFUNCTION_DETECTION_SUPPORTED,
            Flags.LOW_BATTERY_DETECTION_SUPPORTED,
            Flags.GENERAL_DEVICE_FAULT_SUPPORTED,
            Flags.MULTIPLE_BOND_SUPPORTED,
            Flags.E2E_CRC_SUPPORTED,
            Flags.CGM_TREND_INFORMATION_SUPPORTED
    )

    private val expectedType2 = CgmType.CAPILLARY_PLASMA
    private val expectedSampleLocation2 = CgmSampleLocation.SUBCUTANEOUS_TISSUE


    /**
     * Test Packet 3
     * A normal CGM feature packet with incorrect CRC
     */
    private val testPacket3 = byteArrayOf(0b01010101,0b10111010.toByte(),0b10101010.toByte(), 0x52, 0xd5.toByte(), 0xe6.toByte())
    private val expectedFlagSet3 = EnumSet.of(
            Flags.CALIBRATION_SUPPORTED,
            Flags.HYPO_ALERTS_SUPPORTED,
            Flags.RATE_OF_INCREASE_DECREASE_ALERTS_SUPPORTED,
            Flags.SENSOR_MALFUNCTION_DETECTION_SUPPORTED,
            Flags.LOW_BATTERY_DETECTION_SUPPORTED,
            Flags.GENERAL_DEVICE_FAULT_SUPPORTED,
            Flags.E2E_CRC_SUPPORTED,
            Flags.MULTIPLE_BOND_SUPPORTED,
            Flags.CGM_TREND_INFORMATION_SUPPORTED
    )

    private val expectedType3 = null
    private val expectedSampleLocation3 = null



    @Test
    fun testTag() {
        val cgmFeatureCharacteristic = CgmFeatureCharacteristic(null)
        Assert.assertEquals(CgmFeatureCharacteristic::class.java.canonicalName as String, cgmFeatureCharacteristic.tag)
    }

    @Test
    fun testAssignedNumber() {
        val cgmFeatureCharacteristic = CgmFeatureCharacteristic(null)
        Assert.assertEquals(GattCharacteristic.CGM_FEATURE.assigned, cgmFeatureCharacteristic.uuid)
    }

    @Test
    fun testPartialParseFail(){
        //This is a garbage payload, with no readable data.
        val badPayload = byteArrayOf(Byte.MIN_VALUE)
        val cgmFeatureCharacteristic = CgmFeatureCharacteristic(mockBTCharacteristic(badPayload))

        Assert.assertFalse(cgmFeatureCharacteristic.successfulParsing)
    }

    @Test
    fun parseSuccess(){
        Assert.assertTrue(CgmFeatureCharacteristic(mockBTCharacteristic(testPacket1)).successfulParsing)
        Assert.assertTrue(CgmFeatureCharacteristic(mockBTCharacteristic(testPacket2)).successfulParsing)
        Assert.assertFalse(CgmFeatureCharacteristic(mockBTCharacteristic(testPacket3)).successfulParsing)
    }

    @Test
    fun parseSampleLocationSuccessfully(){
        Assert.assertEquals(expectedSampleLocation1, CgmFeatureCharacteristic(mockBTCharacteristic(testPacket1)).cgmSampleLocation)
        Assert.assertEquals(expectedSampleLocation2, CgmFeatureCharacteristic(mockBTCharacteristic(testPacket2)).cgmSampleLocation)
        Assert.assertEquals(expectedSampleLocation3, CgmFeatureCharacteristic(mockBTCharacteristic(testPacket3)).cgmSampleLocation)

        Assert.assertEquals(expectedType1, CgmFeatureCharacteristic(mockBTCharacteristic(testPacket1)).cgmType)
        Assert.assertEquals(expectedType2, CgmFeatureCharacteristic(mockBTCharacteristic(testPacket2)).cgmType)
        Assert.assertEquals(expectedType3, CgmFeatureCharacteristic(mockBTCharacteristic(testPacket3)).cgmType)
    }

    @Test
    fun isFeatureSupported() {

        //This test if the [isFeatureSupported] function returns the correct Boolean Value.

        for (flag in Flags.values()) {
            val flagValue = flag.bit
            val testPacket: ByteArray = byteArrayOf((flagValue and 0x00FF).toByte(),
                    ((flagValue and 0x00FF00) shr 8).toByte(),
                    ((flagValue and 0xFF0000) shr 16).toByte(),
                    0x00
                    )

            var cgmFeatureCharacteristic = CgmFeatureCharacteristic(mockBTCharacteristic(testPacket))

            System.out.printf("flag values %04x . E2E flag value %04x\n", flagValue, Flags.E2E_CRC_SUPPORTED.bit)
            if(flagValue == Flags.E2E_CRC_SUPPORTED.bit) {
                //If the CRC bit is set, the parsing should fail as there is no CRC code attached to the raw packet
                Assert.assertEquals(false, cgmFeatureCharacteristic.successfulParsing)
            }else{
                Assert.assertEquals(true, cgmFeatureCharacteristic.successfulParsing)
            }
            //check correct flags are set and supported
            for (otherFlag in Flags.values()) {
                if (otherFlag != flag) {
                    Assert.assertEquals(false, cgmFeatureCharacteristic.isFeatureSupported(otherFlag))
                } else {
                    if(flagValue == Flags.E2E_CRC_SUPPORTED.bit) {
                        Assert.assertEquals(false, cgmFeatureCharacteristic.isFeatureSupported(otherFlag))
                    }else {
                        Assert.assertEquals(true, cgmFeatureCharacteristic.isFeatureSupported(otherFlag))
                    }
                }
            }
        }
    }

}