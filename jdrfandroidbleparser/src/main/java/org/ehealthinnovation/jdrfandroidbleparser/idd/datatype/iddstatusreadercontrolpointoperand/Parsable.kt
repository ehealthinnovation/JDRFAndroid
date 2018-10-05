package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype.iddstatusreadercontrolpointoperand

/**
 * Parsable define the basic sets of operations that a Bluetooth Characteristic parsing required.
 * The implementation of the methods are up specific class.
 * @author Harry Qiu
 * @since 0.0.1
 */
interface Parsable {
    /**
     * Get the next integer from the data buffer.`
     * @param formatType the bluetooth data type of the integer to be extracted.
     * @return the extracted integer
     */
    fun getNextInt(formatType: Int) : Int

    /**
     * Get the next floating point number from the data buffer.
     * @param formatType the Bluetooth data type of the floating point value to be extracted.
     * @return the extracted floating point value
     */
    fun getNextFloat(formatType: Int) : Float

    /**
     * Get the next string from the data buffer.
     * @param length the length of the string to be extract
     * @return the extracted string
     */
    fun getNextString(length: Int) : String
}