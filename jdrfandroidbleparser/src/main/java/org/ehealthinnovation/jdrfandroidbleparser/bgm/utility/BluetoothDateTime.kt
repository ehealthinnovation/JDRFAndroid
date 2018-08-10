package org.ehealthinnovation.jdrfandroidbleparser.bgm.utility

import java.time.Year
import java.util.*

data class BluetoothDateTime(val _year: Int = 0,
                             val _month: Int = 0,
                             val _day: Int = 0,
                             val _hour: Int = 0,
                             val _min: Int = 0,
                             val _sec: Int = 0,
                             val _offset: Int = 0) {
    var year = _year
    var month = _month
    var day = _day
    var hour = _hour
    var min = _min
    var sec = _sec
    var offset = _offset

}