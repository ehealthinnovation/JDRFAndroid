package org.ehealthinnovation.jdrfandroidbleparser.cgm.characteristic

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.measurement.Flags
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.measurement.SensorStatusAnnunciation
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class CgmMeasurementCharacteristicTest : BaseTest(){

    /**
     * Test Packet 1
     * Concentration Only Packet
     */
    private val testPacket1: ByteArray = byteArrayOf(0x06, 0x00, 0x62, 0xF1.toByte(), 0x12, 0x34)
    private val expectedParseResult1: Boolean = true
    private val expectedConcentration1: Float = 35.4.toFloat()
    private val expectedTrend1 = null
    private val expectedQuality1 = null
    private val expectedSensorStatusAnnunciation1: EnumSet<SensorStatusAnnunciation> = EnumSet.noneOf(SensorStatusAnnunciation::class.java)
    private val expectedFlags1: EnumSet<Flags> = EnumSet.noneOf(Flags::class.java)
    private val expectedTimeOffset1 = 13330
    private val expectedSize1 = 6

    /**
     * Test Packet 2
     * Has Status Octet in the annunciation
     */
    private val testPacket2: ByteArray = byteArrayOf(0x06, 0x00, 0x62, 0xF1.toByte(), 0x12, 0x34)
    private val expectedParseResult2: Boolean = true
    private val expectedConcentration2: Float = 35.4.toFloat()
    private val expectedTrend2 = null
    private val expectedQuality2 = null
    private val expectedSensorStatusAnnunciation2: EnumSet<SensorStatusAnnunciation> = EnumSet.noneOf(SensorStatusAnnunciation::class.java)
    private val expectedFlags2: EnumSet<Flags> = EnumSet.noneOf(Flags::class.java)
    private val expectedTimeOffset2 = 13330
    private val expectedSize2 = 6

    /**
     * Test Packet 3
     *
     * Has Calibration Octet in the annunciation
     *
     */
    private val testPacket3: ByteArray = byteArrayOf(0x07, 0x40.toByte(), 0x62, 0xF1.toByte(), 0x12, 0x34, 0x01)
    private val expectedParseResult3: Boolean = true
    private val expectedConcentration3: Float = 35.4.toFloat()
    private val expectedTrend3 = null
    private val expectedQuality3 = null
    private val expectedSensorStatusAnnunciation3: EnumSet<SensorStatusAnnunciation> = EnumSet.of(
            SensorStatusAnnunciation.TIME_SYNCHRONIZATION_BETWEEN_SENSOR_AND_COLLECTOR_REQUIRED)
    private val expectedFlags3: EnumSet<Flags> = EnumSet.of(
            Flags.SENSOR_STATUS_ANNUNCIATION_FIELD_CALTEMP_OCTET_PRESENT)
    private val expectedTimeOffset3 = 13330
    private val expectedSize3 = 7

    /**
     * Test Packet 4
     * Has Warning Octet in the annunciation
     */
    private val testPacket4: ByteArray = byteArrayOf(0x07, 0x20.toByte(), 0x62, 0xF1.toByte(), 0x12, 0x34, 0x01)
    private val expectedParseResult4: Boolean = true
    private val expectedConcentration4: Float = 35.4.toFloat()
    private val expectedTrend4 = null
    private val expectedQuality4 = null
    private val expectedSensorStatusAnnunciation4: EnumSet<SensorStatusAnnunciation> = EnumSet.of(SensorStatusAnnunciation.SENSOR_RESULT_LOWER_THAN_THE_PATIENT_LOW_LEVEL)
    private val expectedFlags4: EnumSet<Flags> = EnumSet.of(Flags.SENSOR_STATUS_ANNUNCIATION_FIELD_WARNING_OCTET_PRESENT)
    private val expectedTimeOffset4 = 13330
    private val expectedSize4 = 7

    /**
     * Test Packet 5
     * Has Warning Octet and calibration in the annunciation
     */
    private val testPacket5: ByteArray = byteArrayOf(0x08, 0x60.toByte(), 0x62, 0xF1.toByte(), 0x12, 0x34, 0x01, 0x01)
    private val expectedParseResult5: Boolean = true
    private val expectedConcentration5: Float = 35.4.toFloat()
    private val expectedTrend5 = null
    private val expectedQuality5 = null
    private val expectedSensorStatusAnnunciation5: EnumSet<SensorStatusAnnunciation> = EnumSet.of(
            SensorStatusAnnunciation.SENSOR_RESULT_LOWER_THAN_THE_PATIENT_LOW_LEVEL,
            SensorStatusAnnunciation.TIME_SYNCHRONIZATION_BETWEEN_SENSOR_AND_COLLECTOR_REQUIRED)
    private val expectedFlags5: EnumSet<Flags> = EnumSet.of(
            Flags.SENSOR_STATUS_ANNUNCIATION_FIELD_CALTEMP_OCTET_PRESENT,
            Flags.SENSOR_STATUS_ANNUNCIATION_FIELD_WARNING_OCTET_PRESENT)
    private val expectedTimeOffset5 = 13330
    private val expectedSize5 = 8

    /**
     * Test Packet 6
     * Nagative Concentration, a erroneous case, but it wont report error because it is not the parser
     * responsibility to validate values.
     */
    private val testPacket6: ByteArray = byteArrayOf(0x08, 0x60.toByte(), 0xDD.toByte(), 0xFF.toByte(), 0x12, 0x34, 0x01, 0x01)
    private val expectedParseResult6: Boolean = true
    private val expectedConcentration6: Float = -3.5.toFloat()
    private val expectedTrend6 = null
    private val expectedQuality6 = null
    private val expectedSensorStatusAnnunciation6: EnumSet<SensorStatusAnnunciation> = EnumSet.of(
            SensorStatusAnnunciation.SENSOR_RESULT_LOWER_THAN_THE_PATIENT_LOW_LEVEL,
            SensorStatusAnnunciation.TIME_SYNCHRONIZATION_BETWEEN_SENSOR_AND_COLLECTOR_REQUIRED)
    private val expectedFlags6: EnumSet<Flags> = EnumSet.of(
            Flags.SENSOR_STATUS_ANNUNCIATION_FIELD_CALTEMP_OCTET_PRESENT,
            Flags.SENSOR_STATUS_ANNUNCIATION_FIELD_WARNING_OCTET_PRESENT)
    private val expectedTimeOffset6 = 13330
    private val expectedSize6 = 8

    /**
     * Test Packet 7
     * Trend present
     */
    private val testPacket7: ByteArray = byteArrayOf(0x0A, 0x61.toByte(), 0x62.toByte(), 0xF1.toByte(), 0x12, 0x34, 0x01, 0x01, 0xF3.toByte(), 0xFF.toByte())
    private val expectedParseResult7: Boolean = true
    private val expectedConcentration7: Float = 35.4.toFloat()
    private val expectedTrend7 = -1.3.toFloat()
    private val expectedQuality7 = null
    private val expectedSensorStatusAnnunciation7: EnumSet<SensorStatusAnnunciation> = EnumSet.of(
            SensorStatusAnnunciation.SENSOR_RESULT_LOWER_THAN_THE_PATIENT_LOW_LEVEL,
            SensorStatusAnnunciation.TIME_SYNCHRONIZATION_BETWEEN_SENSOR_AND_COLLECTOR_REQUIRED)
    private val expectedFlags7: EnumSet<Flags> = EnumSet.of(
            Flags.SENSOR_STATUS_ANNUNCIATION_FIELD_CALTEMP_OCTET_PRESENT,
            Flags.SENSOR_STATUS_ANNUNCIATION_FIELD_WARNING_OCTET_PRESENT,
            Flags.CGM_TREND_INFORMATION_PRESENT)
    private val expectedTimeOffset7 = 13330
    private val expectedSize7 = 10


    /**
     * Test Packet 8
     * Quality present
     */
    private val testPacket8: ByteArray = byteArrayOf(0x0A, 0x62.toByte(), 0x62.toByte(), 0xF1.toByte(), 0x12, 0x34, 0x01, 0x01, 0xe7.toByte(), 0xF3.toByte())
    private val expectedParseResult8: Boolean = true
    private val expectedConcentration8: Float = 35.4.toFloat()
    private val expectedTrend8 = null
    private val expectedQuality8 = 99.9.toFloat()
    private val expectedSensorStatusAnnunciation8: EnumSet<SensorStatusAnnunciation> = EnumSet.of(
            SensorStatusAnnunciation.SENSOR_RESULT_LOWER_THAN_THE_PATIENT_LOW_LEVEL,
            SensorStatusAnnunciation.TIME_SYNCHRONIZATION_BETWEEN_SENSOR_AND_COLLECTOR_REQUIRED)
    private val expectedFlags8: EnumSet<Flags> = EnumSet.of(
            Flags.CGM_QUALITY_PRESENT,
            Flags.SENSOR_STATUS_ANNUNCIATION_FIELD_CALTEMP_OCTET_PRESENT,
            Flags.SENSOR_STATUS_ANNUNCIATION_FIELD_WARNING_OCTET_PRESENT)
    private val expectedTimeOffset8 = 13330
    private val expectedSize8 = 10

    @Test
    fun testParseSuccessfully() {
        Assert.assertEquals(expectedParseResult1, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket1)).successfulParsing)
        Assert.assertEquals(expectedParseResult2, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket2)).successfulParsing)
        Assert.assertEquals(expectedParseResult3, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket3)).successfulParsing)
        Assert.assertEquals(expectedParseResult4, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket4)).successfulParsing)
        Assert.assertEquals(expectedParseResult5, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket5)).successfulParsing)
        Assert.assertEquals(expectedParseResult6, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket6)).successfulParsing)
        Assert.assertEquals(expectedParseResult7, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket7)).successfulParsing)
        Assert.assertEquals(expectedParseResult8, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket8)).successfulParsing)
    }

    @Test
    fun testParsingConcentrationField() {
        Assert.assertEquals(expectedConcentration1, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket1)).cgmGlucoseConcentration)
        Assert.assertEquals(expectedConcentration2, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket2)).cgmGlucoseConcentration)
        Assert.assertEquals(expectedConcentration3, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket3)).cgmGlucoseConcentration)
        Assert.assertEquals(expectedConcentration4, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket4)).cgmGlucoseConcentration)
        Assert.assertEquals(expectedConcentration5, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket5)).cgmGlucoseConcentration)
        Assert.assertEquals(expectedConcentration6, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket6)).cgmGlucoseConcentration)
        Assert.assertEquals(expectedConcentration7, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket7)).cgmGlucoseConcentration)
        Assert.assertEquals(expectedConcentration8, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket8)).cgmGlucoseConcentration)
    }

    @Test
    fun testParsingTrendInformation() {
        Assert.assertEquals(expectedTrend1, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket1)).cgmTrendInformation)
        Assert.assertEquals(expectedTrend2, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket2)).cgmTrendInformation)
        Assert.assertEquals(expectedTrend3, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket3)).cgmTrendInformation)
        Assert.assertEquals(expectedTrend4, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket4)).cgmTrendInformation)
        Assert.assertEquals(expectedTrend5, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket5)).cgmTrendInformation)
        Assert.assertEquals(expectedTrend6, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket6)).cgmTrendInformation)
        Assert.assertEquals(expectedTrend7, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket7)).cgmTrendInformation)
        Assert.assertEquals(expectedTrend8, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket8)).cgmTrendInformation)
    }

    @Test
    fun testParsingQualityField() {
        Assert.assertEquals(expectedQuality1, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket1)).cgmQuality)
        Assert.assertEquals(expectedQuality2, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket2)).cgmQuality)
        Assert.assertEquals(expectedQuality3, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket3)).cgmQuality)
        Assert.assertEquals(expectedQuality4, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket4)).cgmQuality)
        Assert.assertEquals(expectedQuality5, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket5)).cgmQuality)
        Assert.assertEquals(expectedQuality6, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket6)).cgmQuality)
        Assert.assertEquals(expectedQuality7, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket7)).cgmQuality)
        Assert.assertEquals(expectedQuality8, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket8)).cgmQuality)
    }

    @Test
    fun testParsingStatusAnnunciationFlags() {
        Assert.assertEquals(expectedSensorStatusAnnunciation1, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket1)).sensorStatusAnnunciation)
        Assert.assertEquals(expectedSensorStatusAnnunciation2, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket2)).sensorStatusAnnunciation)
        Assert.assertEquals(expectedSensorStatusAnnunciation3, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket3)).sensorStatusAnnunciation)
        Assert.assertEquals(expectedSensorStatusAnnunciation4, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket4)).sensorStatusAnnunciation)
        Assert.assertEquals(expectedSensorStatusAnnunciation5, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket5)).sensorStatusAnnunciation)
        Assert.assertEquals(expectedSensorStatusAnnunciation6, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket6)).sensorStatusAnnunciation)
        Assert.assertEquals(expectedSensorStatusAnnunciation7, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket7)).sensorStatusAnnunciation)
        Assert.assertEquals(expectedSensorStatusAnnunciation8, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket8)).sensorStatusAnnunciation)
    }

    @Test
    fun testParsingFlags() {
        Assert.assertEquals(expectedFlags1, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket1)).flags)
        Assert.assertEquals(expectedFlags2, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket2)).flags)
        Assert.assertEquals(expectedFlags3, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket3)).flags)
        Assert.assertEquals(expectedFlags4, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket4)).flags)
        Assert.assertEquals(expectedFlags5, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket5)).flags)
        Assert.assertEquals(expectedFlags6, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket6)).flags)
        Assert.assertEquals(expectedFlags7, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket7)).flags)
        Assert.assertEquals(expectedFlags8, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket8)).flags)
    }

    @Test
    fun testParsingSize() {
        Assert.assertEquals(expectedSize1, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket1)).size)
        Assert.assertEquals(expectedSize2, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket2)).size)
        Assert.assertEquals(expectedSize3, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket3)).size)
        Assert.assertEquals(expectedSize4, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket4)).size)
        Assert.assertEquals(expectedSize5, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket5)).size)
        Assert.assertEquals(expectedSize6, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket6)).size)
        Assert.assertEquals(expectedSize7, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket7)).size)
        Assert.assertEquals(expectedSize8, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket8)).size)
    }

    @Test
    fun testParsingOffset() {
        Assert.assertEquals(expectedTimeOffset1, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket1)).timeOffset)
        Assert.assertEquals(expectedTimeOffset2, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket2)).timeOffset)
        Assert.assertEquals(expectedTimeOffset3, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket3)).timeOffset)
        Assert.assertEquals(expectedTimeOffset4, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket4)).timeOffset)
        Assert.assertEquals(expectedTimeOffset5, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket5)).timeOffset)
        Assert.assertEquals(expectedTimeOffset6, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket6)).timeOffset)
        Assert.assertEquals(expectedTimeOffset7, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket7)).timeOffset)
        Assert.assertEquals(expectedTimeOffset8, CgmMeasurementCharacteristic(mockBTCharacteristic(testPacket8)).timeOffset)
    }

}