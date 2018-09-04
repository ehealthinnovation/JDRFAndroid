package org.ehealthinnovation.jdrfandroidbleparser.cgm.characteristic

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic.GlucoseFeatureCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.feature.Flags
import org.junit.After
import org.junit.Assert
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class CgmFeatureCharacteristicTest : BaseTest(){

    /**
     * Test packet 1
     * A normal CGM Feature packet with no CRC
     */
    private val testPacket1 = byteArrayOf(0x)

    /**
     * Test Packet 2
     * A normal CGM feature packet with correct CRC
     */

    /**
     * Test Packet 3
     * A normal CGM feature packet with incorrect CRC
     */



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