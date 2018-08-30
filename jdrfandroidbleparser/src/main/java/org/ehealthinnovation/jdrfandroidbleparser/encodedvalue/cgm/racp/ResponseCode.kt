package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.bgm.racp

enum class ResponseCode constructor(val key: Int) {
    RESERVED_FOR_FUTURE_USE(0),
    SUCCESS(1),
    OP_CODE_NOT_SUPPORTED(2),
    INVALID_OPERATOR(3),
    OPERATOR_NOT_SUPPORTED(4),
    INVALID_OPERAND(5),
    NO_RECORDS_FOUND(6),
    ABORT_UNSUCCESSFUL(7),
    PROCEDURE_NOT_COMPLETED(8),
    OPERAND_NOT_SUPPORTED(9);

    companion object {
        private val map = ResponseCode.values().associateBy(ResponseCode::key)
        fun fromKey(type: Int) = map[type]
    }

}