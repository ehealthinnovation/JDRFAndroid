package org.ehealthinnovation.jdrfandroidbleparser.idd.datatype

/**
 * Writable define the basic sets of operations that a Bluetooth Characteristic writing required.
 * The implementation of the methods are up specific class.
 * @author Harry Qiu
 * @since 0.0.1
 */
interface Writable {

    /**
     * Write the integer [value] as [formatType] into the buffer.
     * @return the number of bytes written to the buffer
     */
    fun setIntValue(value: Int, formatType: Int): Int

     /**
     * Write the [mantissa] and [exponent]as a Bluetooth floating point number according to
      * [formatType] into the buffer.
     * @return the number of bytes written to the buffer
     */
    fun setFloatValue(mantissa: Int, exponent: Int, formatType: Int):Int

    /**
     * Write the [value] as a Bluetooth displayable stream at into the buffer.
     * @return the number of bytes written to the buffer
      */
    fun setStringValue(value: String):Int
}