package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint.BolusValueSelectionValue
import org.junit.Assert
import org.junit.Test

class GetActiveBolusDeliveryTest : BaseTest(){
    /**
     * Data class to hold test vectors for this class
     */
    class TestVector(
            val bolusId:Int?,
            val bolusValueSelection:BolusValueSelectionValue?,
            val expectedComposedPacket: ByteArray,
            val expectedComposeResult: Boolean?
    )

    val testVectors = mutableMapOf<Int, TestVector>()

    init {
        /**
         * Normal composition
         */
        testVectors[1] = TestVector(
                bolusId = 0x1234,
                bolusValueSelection = BolusValueSelectionValue.DELIVERED,
                expectedComposedPacket = byteArrayOf(0x34, 0x12, 0x3c),
                expectedComposeResult = true
        )

        /**
         * Test Packet 2 composing with one null argument
         */
        testVectors[2] = TestVector(
                bolusId = null,
                bolusValueSelection = BolusValueSelectionValue.DELIVERED,
                expectedComposedPacket = ByteArray(0),
                expectedComposeResult = false
        )


        /**
         * Test Packet 3 composing with all null argument
         */
        testVectors[3] = TestVector(
                bolusId = null,
                bolusValueSelection  = null,
                expectedComposedPacket = ByteArray(0),
                expectedComposeResult = false
        )
    }

    @Test
    fun testComposeResult(){
        System.out.printf("testComposeResult\n")
        var operandUndertest : GetActiveBolusDelivery
        var composeResult : Boolean
        for (testVector in testVectors) {
            System.out.printf("testing vector number ${testVector.key}\n")
            operandUndertest =  GetActiveBolusDelivery(mockBTCharacteristic(ByteArray(0)))
            operandUndertest.bolusId = testVector.value.bolusId
            operandUndertest.bolusValueSelection = testVector.value.bolusValueSelection
            composeResult =  operandUndertest.compose()
            Assert.assertEquals(testVector.value.expectedComposeResult, composeResult)
            Assert.assertEquals(true, testVector.value.expectedComposedPacket.contentEquals(operandUndertest.rawData))
        }
    }


}