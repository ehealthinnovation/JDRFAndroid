package org.ehealthinnovation.jdrfandroidbleparser.encodedvalue

enum class FormatType constructor(val formatType: Int, val signed: Boolean) {

    /**
     * Characteristic value format type uint8
     */
    FORMAT_UINT8(0x11, false),

    /**
     * Characteristic value format type uint16
     */
    FORMAT_UINT16(0x12, false),

    /**
     * Characteristic value format type uint32
     */
    FORMAT_UINT32(0x14, false),

    /**
     * Characteristic value format type sint8
     */
    FORMAT_SINT8(0x21, true),

    /**
     * Characteristic value format type sint16
     */
    FORMAT_SINT16(0x22, true),

    /**
     * Characteristic value format type sint32
     */
    FORMAT_SINT32(0x24, true),

    /**
     * Characteristic value format type sfloat (16-bit float)
     */
    FORMAT_SFLOAT(0x32, true),

    /**
     * Characteristic value format type float (32-bit float)
     */
    FORMAT_FLOAT(0x34, true);

    /**
     * Returns the size of a give value type in bytes.
     */
    fun length(): Int {
        return formatType and 0xF
    }

    /**
     * Returns the size of a give value type in bits.
     */
    fun bits(): Int {
        return (formatType and 0xF) * 8
    }

    companion object {
        private val map = FormatType.values().associateBy(FormatType::formatType)
        fun fromType(type: Int) = map[type]
    }
}