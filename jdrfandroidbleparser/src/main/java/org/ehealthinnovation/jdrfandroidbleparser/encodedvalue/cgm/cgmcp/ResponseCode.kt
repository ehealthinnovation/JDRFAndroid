package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.cgmcp

enum class ResponseCode constructor(val key: Int) {
    RESERVED_FOR_FUTURE_USE(0),
    SUCCESS(1),
    OP_CODE_NOT_SUPPORTED(2),
    INVALID_OPERAND(3),
    PROCEDURE_NOT_COMPLETED(4),
    PARAMETER_OUT_OF_RANGE(5);

    companion object {
        private val map = ResponseCode.values().associateBy (ResponseCode::key)
        fun fromKey(key: Int) = map[key]
    }
}