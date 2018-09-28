package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.idd.commandcontrolpoint

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
    PROCEDURE_NOT_APPLICABLE(0x74),
    /**Normal response if a transaction consisting of several procedures was not completed by the Server because the parameters provided by the Client to perform this transaction has been invalid or inconsistent.*/
    PLAUSIBILITY_CHECK_FAILED(0x75),
    /**Normal response if the maximum number of boluses of a specific type is reached when executing the Set Bolus procedure. The maximum available number of boluses depends on the implementation of Insulin Delivery Device and which boluses are already running. */
    MAXIMUM_BOLUS_NUMBER_REACHED(0x76);


    companion object {
        private val map = ResponseCode.values().associateBy(ResponseCode::key)
        fun fromKey(type: Int) = map[type]
    }

}