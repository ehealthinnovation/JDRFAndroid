package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgmmeasurement

import junit.framework.Assert
import org.ehealthinnovation.jdrfandroidbleparser.BaseTest
import org.ehealthinnovation.jdrfandroidbleparser.bgm.encodedvalue.bgmmeasurement.SensorStatusAnnunciation
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class SensorStatusAnnunciationTest : BaseTest(){
    @Test
    fun testEach() {
        enumValues<SensorStatusAnnunciation>().forEach {
            val parseSensorStatusAnnunciation = SensorStatusAnnunciation.parseFlags(it.bit)
            Assert.assertTrue(parseSensorStatusAnnunciation.contains(it))
        }
    }

    @Test
    fun testCombinations() {
        val setSensorStatusAnnunciation: EnumSet<SensorStatusAnnunciation> = EnumSet.noneOf(SensorStatusAnnunciation::class.java)
        var currentMask = 0
        enumValues<SensorStatusAnnunciation>().forEach {
            setSensorStatusAnnunciation.add(it)
            currentMask = currentMask.or(it.bit)
            val parseSensorStatusAnnunciation = SensorStatusAnnunciation.parseFlags(currentMask)
            setSensorStatusAnnunciation.forEach {
                Assert.assertTrue(parseSensorStatusAnnunciation.contains(it))
            }
        }
    }
}