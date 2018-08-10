package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp

enum class Opcode constructor(val key: Int) {

    RESERVED_FOR_FUTURE_USE(0),
    REPORT_STORED_RECORDS(1),
    DELETE_STORED_RECORDS(2),
    ABORT_OPERATION(3),
    REPORT_NUMBER_OF_STORED_RECORDS(4),
    NUMBER_OF_STORED_RECORDS_RESPONSE(5),
    RESPONSE_CODE(6);

    companion object {
        private val map = Opcode.values().associateBy(Opcode::key)
        fun fromKey(type: Int) = map[type]
    }

}