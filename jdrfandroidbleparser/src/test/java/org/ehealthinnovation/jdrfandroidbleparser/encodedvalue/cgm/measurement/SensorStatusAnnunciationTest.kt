package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.measurement

import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class SensorStatusAnnunciationTest : BaseTest(){
    @Test
    fun testEach() {
        enumValues<SensorStatusAnnunciation>().forEach {
            val parseFlags = SensorStatusAnnunciation.parseFlags(it.bit)
            Assert.assertTrue(parseFlags.contains(it))
        }
    }

    @Test
    fun testCombinations() {
        val setFlags: EnumSet<SensorStatusAnnunciation> = EnumSet.noneOf(SensorStatusAnnunciation::class.java)
        var  currentMask = 0
        enumValues<SensorStatusAnnunciation>().forEach {
            setFlags.add(it)
            currentMask = currentMask.or(it.bit)
            val parseFlags = SensorStatusAnnunciation.parseFlags(currentMask)
            setFlags.forEach {
                Assert.assertTrue(parseFlags.contains(it))
            }
        }
    }
}