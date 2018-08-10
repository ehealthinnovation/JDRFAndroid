package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.feature.Flags
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class GlucoseFeatureCharacteristicTest : BaseTest() {

    @Test
    fun testTag() {
        val glucoseFeatureCharacteristic = GlucoseFeatureCharacteristic(null)
        Assert.assertEquals(GlucoseFeatureCharacteristic::class.java.canonicalName as String, glucoseFeatureCharacteristic.tag)
    }


    @Test
    fun testAssignedNumber() {
        val glucoseFeatureCharacteristic = GlucoseFeatureCharacteristic(null)
        Assert.assertEquals(GattCharacteristic.GLUCOSE_FEATURE.assigned, glucoseFeatureCharacteristic.uuid)
    }

    @Test
    fun testPartialParseFail() {
        // This is a garbage payload, with no readable data, in terms of successfully parsing Glucose Context
        val badPayload = byteArrayOf(Byte.MIN_VALUE)
        val glucoseFeatureCharacteristic = GlucoseFeatureCharacteristic(mockBTCharacteristic(badPayload))

        Assert.assertFalse(glucoseFeatureCharacteristic.successfulParsing)
    }

    @Test
    fun parseSuccess() {
        /**This is a general normal packet*/
        val testPacket: ByteArray = byteArrayOf(0x11, 0x01) //value representing 0b00010001 0b00000001
        val glucoseFeatureCharacteristic = GlucoseFeatureCharacteristic(mockBTCharacteristic(testPacket))
        Assert.assertEquals(true, glucoseFeatureCharacteristic.successfulParsing)

        //verify the correct flags are set
        Assert.assertEquals(true, glucoseFeatureCharacteristic.isFeatureSupported(Flags.LOW_BATTERY_DETECTION_DURING_MEASUREMENT_SUPPORTED))
        Assert.assertEquals(true, glucoseFeatureCharacteristic.isFeatureSupported(Flags.SENSOR_STRIP_TYPE_ERROR_DETECTION_SUPPORTED))
        Assert.assertEquals(true, glucoseFeatureCharacteristic.isFeatureSupported(Flags.GENERAL_DEVICE_FAULT_SUPPORTED))
        Assert.assertEquals(false, glucoseFeatureCharacteristic.isFeatureSupported(Flags.SENSOR_MALFUNCTION_DETECTION_SUPPORTED))
        Assert.assertEquals(false, glucoseFeatureCharacteristic.isFeatureSupported(Flags.SENSOR_SAMPLE_SIZE_SUPPORTED))
        Assert.assertEquals(false, glucoseFeatureCharacteristic.isFeatureSupported(Flags.SENSOR_STRIP_INSERTION_ERROR_DETECTION_SUPPORTED))
        Assert.assertEquals(false, glucoseFeatureCharacteristic.isFeatureSupported(Flags.SENSOR_RESULT_HIGH_LOW_DETECTION_SUPPORTED))
    }

    @Test
    fun isFeatureSupported() {

        //This test if the [isFeatureSupported] function returns the correct Boolean Value.

        for (flag in Flags.values()) {
            val flagValue = flag.bit
            val testPacket: ByteArray = byteArrayOf((flagValue and 0x00FF).toByte(), ((flagValue and 0xFF00) shr 8).toByte())

            var glucoseFeatureCharacteristic = GlucoseFeatureCharacteristic(mockBTCharacteristic(testPacket))

            Assert.assertEquals(true, glucoseFeatureCharacteristic.successfulParsing)
            //check correct flags are set and supported
            for (otherFlag in Flags.values()) {
                if (otherFlag != flag) {
                    Assert.assertEquals(false, glucoseFeatureCharacteristic.isFeatureSupported(otherFlag))
                } else {
                    Assert.assertEquals(true, glucoseFeatureCharacteristic.isFeatureSupported(otherFlag))
                }
            }
        }
    }

}