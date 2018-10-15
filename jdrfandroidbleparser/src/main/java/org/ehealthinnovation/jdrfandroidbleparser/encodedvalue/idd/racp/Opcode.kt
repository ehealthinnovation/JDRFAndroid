package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.racp

enum class Opcode constructor(val key: Int) {

    RESERVED_FOR_FUTURE_USE(0),
    REPORT_STORED_RECORDS(0x33),
    DELETE_STORED_RECORDS(0x3C),
    ABORT_OPERATION(0x55),
    REPORT_NUMBER_OF_STORED_RECORDS(0x5A),
    NUMBER_OF_STORED_RECORDS_RESPONSE(0x66),
    RESPONSE_CODE(0x0F);

    companion object {
        private val map = Opcode.values().associateBy(Opcode::key)
        fun fromKey(type: Int) = map[type]
    }

}