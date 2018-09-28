package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.statusreadercontrolpoint

enum class ResponseCode constructor(val key: Int) {

    /**Normal response for successful procedure.  */
    SUCCESS(0x0F),
    /**Normal response if unsupported Op Code is received. */
    OP_CODE_NOT_SUPPORTED(0x70),
    /**Normal response if Operand received does not meet the requirements of the service. */
    INVALID_OPERAND(0x71),
    /**Normal response if unable to complete a procedure for any reason. */
    PROCEDURE_NOT_COMPLETED(0x72),
    /**Normal response if Operand received does not meet the range requirements of the service. */
    PARAMETER_OUT_OF_RANGE(0x73),
    /**Normal response if the procedure cannot be executed because it is not applicable in the current Server Application context. */
    PROCEDURE_NOT_APPLICABLE(0x74);

    companion object {
        private val map = ResponseCode.values().associateBy(ResponseCode::key)
        fun fromKey(type: Int) = map[type]
    }

}