package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint

enum class CounterValueSelection constructor(val key: Int) {


    REMAINING(0x0F),
    ELAPSED(0x33);


    companion object {
        private val map = CounterValueSelection.values().associateBy(CounterValueSelection::key)
        fun fromKey(type: Int) = map[type]
    }

}