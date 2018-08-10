package org.ehealthinnovation.jdrfandroidbleparser.bgm.characteristic.compoundcharacteristic

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.GattCharacteristic
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.Units
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.contextmeasurement.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GlucoseMeasurementContextCharacteristicTest : BaseTest() {

    /*
     * Test Packet 1
     */
    private val testPacket1: ByteArray = byteArrayOf(0x84.toByte(), 0x01.toByte(), 0x00.toByte(), 0xbb.toByte(), 0x51.toByte())
    private val sequenceNumber1: Int = 1
    private val extendedFlag1: Int = 187
    private val tester1: Tester = Tester.SELF
    private val health1: Health = Health.NO_HEALTH_ISSUES

    /*
     * Test Packet 2
     */
    private val testPacket2 = byteArrayOf(0x97.toByte(), 0x06.toByte(), 0x00.toByte(), 0xD3.toByte(), 0x04.toByte(),
            0x72.toByte(), 0xD0.toByte(), 0x04.toByte(), 0x31.toByte(), 0x03.toByte(), 0x5D.toByte(), 0xA0.toByte())
    private val sequenceNumber2: Int = 6
    private val carbType2: CarbohydrateId = CarbohydrateId.SNACK
    private val carbAmount2: Float = 0.114f
    private val mealType2: Meal = Meal.CASUAL
    private val tester2: Tester = Tester.SELF
    private val health2: Health = Health.DURING_MENSES
    private val medId2: MedicationId = MedicationId.INTERMEDIATE_ACTING_INSULIN
    private val medicationUnit2: Units = Units.KILOGRAM
    private val medicationAmount2: Float = 9.3e-5f

    /*
     * Test Packet 3
     */
    private val testPacket3 = byteArrayOf(0xCE.toByte(), 0x07.toByte(), 0x00.toByte(), 0x69.toByte(), 0x03.toByte(),
            0x53.toByte(), 0xAD.toByte(), 0x08.toByte(), 0x07.toByte(), 0x07.toByte(), 0x00.toByte())
    private val sequenceNumber3: Int = 7
    private val extendedFlag3: Int = 105
    private val mealType3: Meal = Meal.FASTING
    private val tester3: Tester = Tester.LAB_TEST
    private val health3: Health = Health.NO_HEALTH_ISSUES
    private val exerciseIntesity3: Float = 0.07f
    private val exerciseDuration3: Int = 2221
    private val hba1c3: Float = 7f

    /*
     * Test Packet 4
     */
    private val testPacket4 = byteArrayOf(0xB6.toByte(), 0x09.toByte(), 0x00.toByte(), 0x07.toByte(), 0x05.toByte(),
            0x51.toByte(), 0x05.toByte(), 0x50.toByte(), 0xD0.toByte())
    private val sequenceNumber4: Int = 9
    private val extendedFlag4: Int = 7
    private val tester4: Tester = Tester.SELF
    private val health4: Health = Health.NO_HEALTH_ISSUES
    private val medId4: MedicationId = MedicationId.PRE_MIXED_INSULIN
    private val medicationUnit4: Units = Units.LITRE
    private val medicationAmount4: Float = 8.0e-2f

    /*
     * Test Packet 5
     */
    private val testPacket5 = byteArrayOf(0xD7.toByte(), 0x0A.toByte(), 0x00.toByte(), 0xE9.toByte(), 0x04.toByte(),
            0x75.toByte(), 0xD0.toByte(), 0x04.toByte(), 0x51.toByte(), 0x05.toByte(), 0x1D.toByte(), 0xA0.toByte(),
            0x07.toByte(), 0x00.toByte())
    private val sequenceNumber5: Int = 10
    private val extendedFlag5: Int = 233
    private val carbType5: CarbohydrateId = CarbohydrateId.SNACK
    private val carbAmount5: Float = 0.117f
    private val mealType5: Meal = Meal.CASUAL
    private val tester5: Tester = Tester.SELF
    private val health5: Health = Health.NO_HEALTH_ISSUES
    private val medId5: MedicationId = MedicationId.PRE_MIXED_INSULIN
    private val medicationUnit5: Units = Units.KILOGRAM
    private val medicationAmount5: Float = 2.9e-5f

    private lateinit var glucoseMeasurementContext1: GlucoseMeasurementContextCharacteristic
    private lateinit var glucoseMeasurementContext2: GlucoseMeasurementContextCharacteristic
    private lateinit var glucoseMeasurementContext3: GlucoseMeasurementContextCharacteristic
    private lateinit var glucoseMeasurementContext4: GlucoseMeasurementContextCharacteristic
    private lateinit var glucoseMeasurementContext5: GlucoseMeasurementContextCharacteristic

    @Before
    fun init() {
        glucoseMeasurementContext1 = GlucoseMeasurementContextCharacteristic(mockBTCharacteristic(testPacket1))
        glucoseMeasurementContext2 = GlucoseMeasurementContextCharacteristic(mockBTCharacteristic(testPacket2))
        glucoseMeasurementContext3 = GlucoseMeasurementContextCharacteristic(mockBTCharacteristic(testPacket3))
        glucoseMeasurementContext4 = GlucoseMeasurementContextCharacteristic(mockBTCharacteristic(testPacket4))
        glucoseMeasurementContext5 = GlucoseMeasurementContextCharacteristic(mockBTCharacteristic(testPacket5))
    }

    @Test
    fun testTag() {
        val glucoseMeasurementContext = GlucoseMeasurementContextCharacteristic(null)
        Assert.assertEquals(GlucoseMeasurementContextCharacteristic::class.java.canonicalName as String, glucoseMeasurementContext.tag)
    }

    @Test
    fun testAssignedNumber() {
        val glucoseMeasurementContext = GlucoseMeasurementContextCharacteristic(null)
        Assert.assertEquals(GattCharacteristic.GLUCOSE_MEASUREMENT_CONTEXT.assigned, glucoseMeasurementContext.uuid)
    }

    @Test
    fun testPartialParseFail() {
        // This is a garbage payload, with no readable data, in terms of successfully parsing Glucose Context
        val badPayload = byteArrayOf(Byte.MIN_VALUE, Byte.MAX_VALUE)
        val glucoseMeasurementContext = GlucoseMeasurementContextCharacteristic(mockBTCharacteristic(badPayload))
        Assert.assertFalse(glucoseMeasurementContext.successfulParsing)
    }

    @Test
    fun testParseSuccess() {
        Assert.assertTrue(glucoseMeasurementContext1.successfulParsing)
        Assert.assertTrue(glucoseMeasurementContext2.successfulParsing)
        Assert.assertTrue(glucoseMeasurementContext3.successfulParsing)
        Assert.assertTrue(glucoseMeasurementContext4.successfulParsing)
        Assert.assertTrue(glucoseMeasurementContext5.successfulParsing)
    }

    @Test
    fun testParseSequenceNumber() {
        Assert.assertEquals(sequenceNumber1, glucoseMeasurementContext1.sequenceNumber)
        Assert.assertEquals(sequenceNumber2, glucoseMeasurementContext2.sequenceNumber)
        Assert.assertEquals(sequenceNumber3, glucoseMeasurementContext3.sequenceNumber)
        Assert.assertEquals(sequenceNumber4, glucoseMeasurementContext4.sequenceNumber)
        Assert.assertEquals(sequenceNumber5, glucoseMeasurementContext5.sequenceNumber)
    }

    @Test
    fun testParseExtendedFlag() {
        Assert.assertEquals(extendedFlag1, glucoseMeasurementContext1.extendedFlag)
        Assert.assertEquals(extendedFlag3, glucoseMeasurementContext3.extendedFlag)
        Assert.assertEquals(extendedFlag4, glucoseMeasurementContext4.extendedFlag)
        Assert.assertEquals(extendedFlag5, glucoseMeasurementContext5.extendedFlag)
    }

    @Test
    fun testParseCarbohydrates() {
        Assert.assertEquals(carbType2, glucoseMeasurementContext2.carbohydrateId)
        Assert.assertEquals(carbAmount2, glucoseMeasurementContext2.carbohydrate)
        Assert.assertEquals(carbType5, glucoseMeasurementContext5.carbohydrateId)
        Assert.assertEquals(carbAmount5, glucoseMeasurementContext5.carbohydrate)
    }

    @Test
    fun testParseMeal() {
        Assert.assertEquals(mealType2, glucoseMeasurementContext2.meal)
        Assert.assertEquals(mealType3, glucoseMeasurementContext3.meal)
        Assert.assertEquals(mealType5, glucoseMeasurementContext5.meal)
    }

    @Test
    fun testParseTesterHealth() {
        Assert.assertEquals(tester1, glucoseMeasurementContext1.tester)
        Assert.assertEquals(tester2, glucoseMeasurementContext2.tester)
        Assert.assertEquals(tester3, glucoseMeasurementContext3.tester)
        Assert.assertEquals(tester4, glucoseMeasurementContext4.tester)
        Assert.assertEquals(tester5, glucoseMeasurementContext5.tester)

        Assert.assertEquals(health1, glucoseMeasurementContext1.health)
        Assert.assertEquals(health2, glucoseMeasurementContext2.health)
        Assert.assertEquals(health3, glucoseMeasurementContext3.health)
        Assert.assertEquals(health4, glucoseMeasurementContext4.health)
        Assert.assertEquals(health5, glucoseMeasurementContext5.health)
    }

    @Test
    fun testParseExerciseDurationAndIntensity() {
        Assert.assertEquals(exerciseDuration3, glucoseMeasurementContext3.exerciseDuration)
        Assert.assertEquals(exerciseIntesity3, glucoseMeasurementContext3.exerciseIntensity)
    }

    @Test
    fun testParseMedicationAndMedicationId() {
        Assert.assertEquals(medId2, glucoseMeasurementContext2.medicationId)
        Assert.assertEquals(medId4, glucoseMeasurementContext4.medicationId)
        Assert.assertEquals(medId5, glucoseMeasurementContext5.medicationId)

        Assert.assertEquals(medicationAmount2, glucoseMeasurementContext2.medicationAmount)
        Assert.assertEquals(medicationAmount4, glucoseMeasurementContext4.medicationAmount)
        Assert.assertEquals(medicationAmount5, glucoseMeasurementContext5.medicationAmount)
    }

    @Test
    fun testParseMedicationUnits() {
        Assert.assertEquals(medicationUnit2, glucoseMeasurementContext2.medicationUnit)
        Assert.assertEquals(medicationUnit4, glucoseMeasurementContext4.medicationUnit)
        Assert.assertEquals(medicationUnit5, glucoseMeasurementContext5.medicationUnit)
    }

    @Test
    fun testParseHbA1c() {
        Assert.assertEquals(hba1c3, glucoseMeasurementContext3.HbA1c)
    }
}