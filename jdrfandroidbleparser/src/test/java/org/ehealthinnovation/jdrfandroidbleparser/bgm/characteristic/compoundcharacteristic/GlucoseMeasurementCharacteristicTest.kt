package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic

import android.bluetooth.BluetoothGattCharacteristic
import com.nhaarman.mockito_kotlin.mock
import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.bgmmeasurement.Flags
import org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.bgmmeasurement.SampleLocation
import org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.bgmmeasurement.Type
import org.ehealthinnovation.jdrfandroidbleparser.bgm.utility.BluetoothDateTime
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.Units
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.util.*
import kotlin.reflect.jvm.internal.impl.types.checker.TypeIntersector

class GlucoseMeasurementCharacteristicTest : BaseTest() {

    /**
     * Test Packet 1
     */
    private val testPacket1 = byteArrayOf(0x13, 0x01, 0x00, 0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x35, 0x0e, 0x00, 0x00, 0x54, 0xb0.toByte(), 0x19.toByte());
    private val sequenceNumber1 = 1
    private val date1 = BluetoothDateTime(2018, 6, 6, 14, 53, 14)
    private val concentrationValue1 = 0.00084f
    private val sampleLocation1 = SampleLocation.FINGER
    private val sampleType1 = Type.ISF
    private val unit1 = Units.KILOGRAM_PER_LITRE
    private val expectedDate1 = Date(118, 5, 6, 14, 53, 14)


    /**
     * Test packet 2
     */
    private val testPacket2 = byteArrayOf(0x03, 0x02, 0x00, 0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x36, 0x12, 0x10, 0x00, 0x60, 0xb0.toByte(), 0x11.toByte())
    private val sequenceNumber2 = 2
    private val date2 = BluetoothDateTime(2018, 6, 6, 14, 54, 18, 0)
    private val concentrationValue3 = 0.00117f
    private val sampleLocation2 = SampleLocation.FINGER
    private val sampleType2 = Type.CAPILLARY_WHOLE_BLOOD
    private val unit2 = Units.KILOGRAM_PER_LITRE
    private val expectedDate2 = Date(118, 5, 6, 15, 10, 18)

    /**
     * Test packet 3
     */
    private val testPacket3 = byteArrayOf(0x03, 0x03, 0x00, 0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x36, 0x2e, 0x00, 0x00, 0x75, 0xb0.toByte(), 0x12.toByte())
    private val sequenceNumber3 = 3
    private val date3 = BluetoothDateTime(2018, 6, 6, 14, 54, 46, 0)
    private val concentrationValue2 = 0.00096f
    private val sampleLocation3 = SampleLocation.FINGER
    private val sampleType3 = Type.CAPILLARY_PLASMA
    private val unit3 = Units.KILOGRAM_PER_LITRE
    private val expectedDate3 = Date(118, 5, 6, 14, 54, 46)

    /**
     * Test packet 4
     */
    private val testPacket4 = byteArrayOf(0x03, 0x04, 0x00, 0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x39, 0x04, 0x00, 0x00, 0x3f, 0xb0.toByte(), 0x11.toByte())
    private val sequenceNumber4 = 4
    private val date4 = BluetoothDateTime(2018, 6, 6, 14, 57, 4, 0)
    private val concentrationValue4 = 0.00063f
    private val sampleLocation4 = SampleLocation.FINGER
    private val sampleType4 = Type.CAPILLARY_WHOLE_BLOOD
    private val unit4 = Units.KILOGRAM_PER_LITRE
    private val expectedDate4 = Date(118, 5, 6, 14, 57, 4)

    private lateinit var glucoseMeasurementCharacteristic1: GlucoseMeasurementCharacteristic
    private lateinit var glucoseMeasurementCharacteristic2: GlucoseMeasurementCharacteristic
    private lateinit var glucoseMeasurementCharacteristic3: GlucoseMeasurementCharacteristic
    private lateinit var glucoseMeasurementCharacteristic4: GlucoseMeasurementCharacteristic

    private lateinit var payload: ByteArray
    val ERROR_ACCEPTABLE = 1e-4

    @Before
    fun init() {
        glucoseMeasurementCharacteristic1 = GlucoseMeasurementCharacteristic(mockBTCharacteristic(testPacket1))
        glucoseMeasurementCharacteristic2 = GlucoseMeasurementCharacteristic(mockBTCharacteristic(testPacket2))
        glucoseMeasurementCharacteristic3 = GlucoseMeasurementCharacteristic(mockBTCharacteristic(testPacket3))
        glucoseMeasurementCharacteristic4 = GlucoseMeasurementCharacteristic(mockBTCharacteristic(testPacket4))

    }

    @Test
    fun testTag() {
        val glucoseMeasurement = GlucoseMeasurementCharacteristic(null)
        Assert.assertEquals(GlucoseMeasurementCharacteristic::class.java.canonicalName as String, glucoseMeasurement.tag)
    }

    @Test
    fun testAssignedNumber() {
        val glucoseMeasurement = GlucoseMeasurementCharacteristic(null)
        Assert.assertEquals(GattCharacteristic.GLUCOSE_MEASUREMENT.assigned, glucoseMeasurement.uuid)
    }

    @Test
    fun testPartialParseFail() {
        // This is a garbage payload, with no readable data, in terms of successfully parsing Glucose
        val badPayload = byteArrayOf(Byte.MIN_VALUE, Byte.MAX_VALUE)
        val glucoseMeasurement = GlucoseMeasurementCharacteristic(mockBTCharacteristic(badPayload))
        Assert.assertFalse(glucoseMeasurement.successfulParsing)
    }


    @Test
    fun testParseSuccess() {
        Assert.assertTrue(glucoseMeasurementCharacteristic1.successfulParsing)
        Assert.assertTrue(glucoseMeasurementCharacteristic2.successfulParsing)
        Assert.assertTrue(glucoseMeasurementCharacteristic3.successfulParsing)
        Assert.assertTrue(glucoseMeasurementCharacteristic4.successfulParsing)
    }


    @Test
    fun testParseSequenceNumber() {
        Assert.assertEquals(sequenceNumber1, glucoseMeasurementCharacteristic1.sequenceNumber)
        Assert.assertEquals(sequenceNumber2, glucoseMeasurementCharacteristic2.sequenceNumber)
        Assert.assertEquals(sequenceNumber3, glucoseMeasurementCharacteristic3.sequenceNumber)
        Assert.assertEquals(sequenceNumber4, glucoseMeasurementCharacteristic4.sequenceNumber)
    }

    @Test
    fun testParseDate() {
        Assert.assertEquals(date1, glucoseMeasurementCharacteristic1.dateTime)
        Assert.assertEquals(date2, glucoseMeasurementCharacteristic2.dateTime)
        Assert.assertEquals(date3, glucoseMeasurementCharacteristic3.dateTime)
        Assert.assertEquals(date4, glucoseMeasurementCharacteristic4.dateTime)
    }

    @Test
    fun testParseConcentrationValue() {
        Assert.assertEquals(concentrationValue1, glucoseMeasurementCharacteristic1.concentration)
        Assert.assertEquals(concentrationValue2, glucoseMeasurementCharacteristic2.concentration)
        Assert.assertEquals(concentrationValue3, glucoseMeasurementCharacteristic3.concentration)
        Assert.assertEquals(concentrationValue4, glucoseMeasurementCharacteristic4.concentration)
    }

    @Test
    fun testParseSampleLocation() {
        Assert.assertEquals(sampleLocation1, glucoseMeasurementCharacteristic1.sampleLocation)
        Assert.assertEquals(sampleLocation2, glucoseMeasurementCharacteristic2.sampleLocation)
        Assert.assertEquals(sampleLocation3, glucoseMeasurementCharacteristic3.sampleLocation)
        Assert.assertEquals(sampleLocation4, glucoseMeasurementCharacteristic4.sampleLocation)
    }

    @Test
    fun testParseSampleType() {
        Assert.assertEquals(sampleType1, glucoseMeasurementCharacteristic1.fluidType)
        Assert.assertEquals(sampleType2, glucoseMeasurementCharacteristic2.fluidType)
        Assert.assertEquals(sampleType3, glucoseMeasurementCharacteristic3.fluidType)
        Assert.assertEquals(sampleType4, glucoseMeasurementCharacteristic4.fluidType)
    }

    @Test
    fun testParseUnit() {
        Assert.assertEquals(unit1, glucoseMeasurementCharacteristic1.unit)
        Assert.assertEquals(unit2, glucoseMeasurementCharacteristic2.unit)
        Assert.assertEquals(unit3, glucoseMeasurementCharacteristic3.unit)
        Assert.assertEquals(unit4, glucoseMeasurementCharacteristic4.unit)
    }

    //The following test the univer conversion accuracy
    @Test
    fun getConcentration() {
        val testRawPacket = byteArrayOf(0x03, 0x04, 0x00, 0xe2.toByte(), 0x07, 0x06, 0x06, 0x0e, 0x39, 0x04, 0x00, 0x00, 0x3f, 0xb0.toByte(), 0x11.toByte())

        val characteristics = mockBTCharacteristic(testRawPacket)
        val glucoseMeasureCharacteristic = GlucoseMeasurementCharacteristic(characteristics)
        //verify the glucose measurement value part
        assertEquals(true, (glucoseMeasureCharacteristic.concentration!! - 0.00117f) < ERROR_ACCEPTABLE)
        //converted 0.00117f to 117 mg/dL
        assertEquals(true, (glucoseMeasureCharacteristic.getConcentration(Units.MILLIGRAM_PER_DECILITRE)!! - 117f) < ERROR_ACCEPTABLE)

        //convert 117f mg/dL to 6.5 mmol/L
        assertEquals(true, (glucoseMeasureCharacteristic.getConcentration(Units.MILLIMOLE_PER_LITRE)!! - 6.5f) < ERROR_ACCEPTABLE)

        //convert 117f mg/dL to 0.0065mol/L
        assertEquals(true, (glucoseMeasureCharacteristic.getConcentration(Units.MOLE_PER_LITRE)!! - 0.0065f) < ERROR_ACCEPTABLE)

    }


}