package org.ehealthinnovation.jdrfandroidbleparser

import android.bluetooth.BluetoothGattCharacteristic
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.mock
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.FormatType
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.FormatType.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mockito
import java.lang.UnsupportedOperationException
import java.nio.charset.Charset
import java.util.*
import kotlin.jvm.java

open class BaseTest {

    private lateinit var testPayload: ByteArray
    private var mockPayload: ByteArray = ByteArray(0)
    private lateinit var mockBTCharacteristic: BluetoothGattCharacteristic
    private lateinit var stringByteArray: ByteArray
    private lateinit var testString: String

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)


    protected fun mockBTCharacteristic(payload2: ByteArray): BluetoothGattCharacteristic {
        if(payload2.isNotEmpty()){
         mockPayload = payload2
        }else{
            mockPayload = ByteArray(0)
        }

        return mock {
            on { getIntValue(any(Int::class.java), any(Int::class.java)) } doAnswer {
                val formatType: Int = it.getArgument(0)
                when (FormatType.fromType(formatType)) {
                    FORMAT_UINT32, FORMAT_SINT32 -> {
                        throw UnsupportedOperationException("No support for 32 bit reads yet...")
                    }
                    FORMAT_SINT8, FORMAT_UINT8, FORMAT_SINT16, FORMAT_UINT16 -> {
                        val offset: Int = it.getArgument(1)
                        FormatType.fromType(formatType)?.let {
                            if( mockPayload.size <= offset+it.length()-1){
                                throw IllegalArgumentException("Trying to access non-existing byte")
                            }
                            val reversedArray = mockPayload.sliceArray(offset..(offset + (it.length() - 1)))
                            if (it.signed) {
                                unsignedToSigned(unsignedBytesToInt(reversedArray), reversedArray.size * 8)
                            } else {
                                unsignedBytesToInt(reversedArray)
                            }
                        } ?: throw IllegalArgumentException("Bad format type test data.")
                    }
                    else -> {
                        throw IllegalArgumentException("Passed float type format to getIntValue()")
                    }
                }
            }
            on { getFloatValue(any(Int::class.java), any(Int::class.java)) } doAnswer {
                val formatType: Int = it.getArgument(0)
                when (FormatType.fromType(formatType)) {
                    FORMAT_FLOAT, FORMAT_SFLOAT -> {
                        val offset: Int = it.getArgument(1)
                        FormatType.fromType(formatType)?.let {
                            val reversedArray = mockPayload.sliceArray(offset..(offset + (it.length() - 1)))
                            when (it) {
                                FORMAT_FLOAT -> bytesToFloat(reversedArray[0], reversedArray[1], reversedArray[2], reversedArray[3])
                                FORMAT_SFLOAT -> bytesToFloat(reversedArray[0], reversedArray[1])
                                else -> throw IllegalArgumentException("Byte array must have length of 2 or 4 to convert to float.")
                            }
                        } ?: throw IllegalArgumentException("Bad format type test data.")
                    }
                    else -> {
                        throw IllegalArgumentException("Passed float type format to getIntValue()")
                    }
                }
            }
            on { getStringValue(any(Int::class.java)) } doAnswer {
                val offset: Int = it.getArgument(0)
                mockPayload.sliceArray(offset..(offset + (mockPayload.size - 1))).reversedArray().toString(Charset.defaultCharset())
            }
            on { value } doAnswer {mockPayload}
            on {setValue(any(Int::class.java), any(Int::class.java), any(Int::class.java))} doAnswer {
                val value: Int = it.getArgument(0)
                val formatType : Int = it.getArgument(1)
                val offset: Int = it.getArgument(2)

                when (FormatType.fromType(formatType)) {
                    FORMAT_UINT8, FORMAT_UINT16-> {
                        setPayloadValueInt(value, formatType, offset)
                    }
                    else->{
                        throw IllegalArgumentException("Unknown type")
                    }
                }
            }
            on {setValue(any(Int::class.java), any(Int::class.java), any(Int::class.java), any(Int::class.java))} doAnswer {
                val mantissa: Int = it.getArgument(0)
                val exponent: Int = it.getArgument(1)
                val formatType : Int = it.getArgument(2)
                val offset: Int = it.getArgument(3)

                when (FormatType.fromType(formatType)) {
                    FORMAT_SFLOAT->{
                        setPayloadValueFloat(mantissa, exponent, formatType, offset)
                    }
                    else->{
                        throw IllegalArgumentException("Unknown type")
                    }
                }

            }
        }
    }

    @Before
    fun setUp() {
    }

    @Test
    fun testMockUINT8() {
        /*
         * Data is little endian formatted. So the LSO (least significant octet is loaded in first,
         * followed by the second least significant octet, and so on.
         *
         * In this case, we want the payload to be 0x0123456789ABCDEF
         */
        testPayload = byteArrayOf(0xef.toByte(), 0xcd.toByte(), 0xab.toByte(),
                0x89.toByte(), 0x67.toByte(), 0x45.toByte(), 0x23.toByte(), 0x01.toByte())
        mockBTCharacteristic = mockBTCharacteristic(testPayload)
        for ((index, value) in testPayload.withIndex()) {
            Assert.assertEquals(String.format("%02X", value).toInt(radix = 16), mockBTCharacteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, index))
        }
    }

    @Test
    fun testMockSINT8() {
        /*
         * Data is little endian formatted. So the LSO (least significant octet is loaded in first,
         * followed by the second least significant octet, and so on.
         *
         * In this case, we want the payload to be 0x0123456789ABCDEF
         */
        testPayload = byteArrayOf(0xef.toByte(), 0xcd.toByte(), 0xab.toByte(),
                0x89.toByte(), 0x67.toByte(), 0x45.toByte(), 0x23.toByte(), 0x01.toByte())
        mockBTCharacteristic = mockBTCharacteristic(testPayload)
        for ((index, value) in testPayload.withIndex()) {
            Assert.assertEquals(unsignedToSigned(String.format("%02X", value).toInt(radix = 16), FORMAT_SINT8.bits()),
                    mockBTCharacteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_SINT8, index))
        }
    }

    @Test
    fun testMockUINT16() {
        /*
         * Data is little endian formatted. So the LSO (least significant octet is loaded in first,
         * followed by the second least significant octet, and so on.
         *
         * In this case, we want the payload to be 0x0123456789ABCDEF
         */
        testPayload = byteArrayOf(0xef.toByte(), 0xcd.toByte(), 0xab.toByte(),
                0x89.toByte(), 0x67.toByte(), 0x45.toByte(), 0x23.toByte(), 0x01.toByte())
        mockBTCharacteristic = mockBTCharacteristic(testPayload)
        var index = 0
        while (index < testPayload.size) {
            Assert.assertEquals(testPayload.sliceArray(index..(index + FORMAT_UINT16.length() - 1)).reversedArray().toHex()!!.toInt(radix = 16),
                    mockBTCharacteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT16, index))
            index += FORMAT_UINT16.length()
        }
    }

    @Test
    fun testMockSINT16() {
        /*
         * Data is little endian formatted. So the LSO (least significant octet is loaded in first,
         * followed by the second least significant octet, and so on.
         *
         * In this case, we want the payload to be 0x0123456789ABCDEF
         */
        testPayload = byteArrayOf(0xef.toByte(), 0xcd.toByte(), 0xab.toByte(),
                0x89.toByte(), 0x67.toByte(), 0x45.toByte(), 0x23.toByte(), 0x01.toByte())
        mockBTCharacteristic = mockBTCharacteristic(testPayload)
        var index = 0
        while (index < testPayload.size) {
            Assert.assertEquals(unsignedToSigned(testPayload.sliceArray(index..(index + FORMAT_SINT16.length() - 1)).reversedArray().toHex()!!.toInt(radix = 16), FORMAT_SINT16.bits()),
                    mockBTCharacteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_SINT16, index))
            index += FORMAT_UINT16.length()
        }
    }

    @Test
    fun testMockFLOAT() {
        /*
         * Data is little endian formatted. So the LSO (least significant octet is loaded in first,
         * followed by the second least significant octet, and so on.
         *
         * In this case, we want the payload to be 0x0123456789ABCDEF
         */
        testPayload = byteArrayOf(0xef.toByte(), 0xcd.toByte(), 0xab.toByte(),
                0x89.toByte(), 0x67.toByte(), 0x45.toByte(), 0x23.toByte(), 0x01.toByte())
        mockBTCharacteristic = mockBTCharacteristic(testPayload)
        var index = 0
        while (index < testPayload.size) {
            val sliced = testPayload.sliceArray(index..(index + FORMAT_FLOAT.length() - 1))
            Assert.assertEquals(bytesToFloat(sliced[0], sliced[1], sliced[2], sliced[3]),
                    mockBTCharacteristic.getFloatValue(BluetoothGattCharacteristic.FORMAT_FLOAT, index))
            index += FORMAT_FLOAT.length()
        }
    }

    @Test
    fun testMockSFLOAT() {
        /*
         * Data is little endian formatted. So the LSO (least significant octet is loaded in first,
         * followed by the second least significant octet, and so on.
         *
         * In this case, we want the payload to be 0x0123456789ABCDEF
         */
        testPayload = byteArrayOf(0xef.toByte(), 0xcd.toByte(), 0xab.toByte(),
                0x89.toByte(), 0x67.toByte(), 0x45.toByte(), 0x23.toByte(), 0x01.toByte())
        mockBTCharacteristic = mockBTCharacteristic(testPayload)
        var index = 0
        while (index < testPayload.size) {
            val sliced = testPayload.sliceArray(index..(index + FORMAT_SFLOAT.length() - 1))
            Assert.assertEquals(bytesToFloat(sliced[0], sliced[1]),
                    mockBTCharacteristic.getFloatValue(BluetoothGattCharacteristic.FORMAT_SFLOAT, index))
            index += FORMAT_SFLOAT.length()
        }
    }

    @Test
    fun testMockString() {
        stringByteArray = byteArrayOf(0x6f.toByte(), 0x6e.toByte(), 0x72.toByte(), 0x6f.toByte(),
                0x74.toByte(), 0x6e.toByte(), 0x61.toByte(), 0x49.toByte(), 0x20.toByte(),
                0x6b.toByte(), 0x72.toByte(), 0x61.toByte(), 0x4d.toByte()) // Mark Iantorno
        testString = "Mark Iantorno"
        mockBTCharacteristic = mockBTCharacteristic(stringByteArray)
        Assert.assertEquals(testString, mockBTCharacteristic.getStringValue(0))
    }

    @Test
    fun testSettingUint8(){
        mockPayload = ByteArray(1)
        val expectedPayload: ByteArray = byteArrayOf(0x69)
        val valueToSet:Byte = 0x69
        mockBTCharacteristic = mockBTCharacteristic(ByteArray(0))
        mockBTCharacteristic.setValue(valueToSet.toInt(), FORMAT_UINT8.formatType, 0)
        Assert.assertTrue(mockPayload.contentEquals(expectedPayload))
    }

    @Test
    fun testSettingUint16(){
        mockPayload = ByteArray(2)
        val expectedPayload: ByteArray = byteArrayOf(0x69,0x38)
        val valueToSet:Int = 0x3869
        mockBTCharacteristic = mockBTCharacteristic(ByteArray(0))
        mockBTCharacteristic.setValue(valueToSet, FORMAT_UINT16.formatType, 0)
        Assert.assertTrue(mockPayload.contentEquals(expectedPayload))
    }

    companion object {

        /**
         * Convert a [ByteArray] to a hexadecimal [String]
         */
        fun ByteArray?.toHex() = this?.joinToString(separator = "") {
            it.toInt().and(0xff).toString(16).padStart(2, '0')
        }

        /**
         * Convert a signed [Byte] to an unsigned [Int].
         */
        fun Byte.toPositiveInt() = toInt() and 0xFF

        /**
         * Convert a [ByteArray] to an unsigned [Int].
         */
        private fun unsignedBytesToInt(array: ByteArray): Int {
            var total = 0
            array.forEachIndexed { index, byte ->
                total += (byte.toPositiveInt() shl (8 * index))
            }
            return total
        }

        /**
         * Convert signed bytes to a 16-bit short float value.
         */
        private fun bytesToFloat(b0: Byte, b1: Byte): Float {
            val mantissa = unsignedToSigned(b0.toPositiveInt() + (b1.toPositiveInt() and 0x0F shl 8), 12)
            val exponent = unsignedToSigned(b1.toPositiveInt() shr 4, 4)
            return (mantissa * Math.pow(10.0, exponent.toDouble())).toFloat()
        }

        /**
         * Convert signed bytes to a 32-bit short float value.
         */
        private fun bytesToFloat(b0: Byte, b1: Byte, b2: Byte, b3: Byte): Float {
            val mantissa = unsignedToSigned(b0.toPositiveInt()
                    + (b1.toPositiveInt() shl 8)
                    + (b2.toPositiveInt() shl 16), 24)
            return (mantissa * Math.pow(10.0, b3.toDouble())).toFloat()
        }

        /**
         * Convert an unsigned integer value to a two's-complement encoded
         * signed value.
         */
        private fun unsignedToSigned(unsigned: Int, size: Int): Int {
            var unsigned = unsigned
            if (unsigned and (1 shl size - 1) != 0) {
                unsigned = -1 * ((1 shl size - 1) - (unsigned and (1 shl size - 1) - 1))
            }
            return unsigned
        }
    }

    @get:Rule
    val watchman = object : TestWatcher() {
        override fun succeeded(d: Description?) {
            super.succeeded(d)
            println(ok())
        }
    }

    var OK = "ICAgICAgICAgICAgLi0tLS0tLS0tLl8KICAgICAgICAgICAoYC0tJyAgICAgICBgLS4KICAgICAgICAgICAgYC5fX19fX18g" +
            "ICAgICBgLgogICAgICAgICBfX19fX19fX19fX2BfXyAgICAgXAogICAgICAsLScgICAgICAgICAgIGAtLlwgICAgIHwKICAgI" +
            "CAvLyAgICAgICAgICAgICAgICBcfCAgICB8XAogICAgKGAgIC4nfn5+fn4tLS1cICAgICBcJyAgIHwgfAogICAgIGAtJyAgIC" +
            "AgICAgICAgKSAgICAgXCAgIHwgfAogICAgICAgICwtLS0tLS0tLS0nIC0gLS4gIGAgIC4gJwogICAgICAsJyAgICAgICAgICA" +
            "gICBgJWBcYCAgICAgfAogICAgIC8gICAgICAgICAgICAgICAgICAgICAgXCAgfAogICAgLyAgICAgXC0tLS0tLiAgICAgICAg" +
            "IFwgICAgYAogICAvfCAgLF8vICAgICAgJy0uXyAgICAgICAgICAgIHwKICAoLScgIC8gICAgICAgICAgIC8gICAgICAgICAgI" +
            "CBgCiAgLGAtLTwgICAgICAgICAgIHwgICAgICAgIFwgICAgIFwKICBcIHwgIFwgICAgICAgICAvJSUgICAgICAgICAgICAgYF" +
            "wKICAgfC8gICBcX19fXy0tLSctLWAlICAgICAgICBcICAgICBcCiAgIHwgICAgJyAgICAgICAgICAgYCAgICAgICAgICAgICA" +
            "gIFwKICAgfAogICAgYC0tLl9fCiAgICAgICAgICBgLS0tLl9fX19fX18KICAgICAgICAgICAgICAgICAgICAgIGAuCiAgICAg" +
            "ICAgICAgICAgICAgICAgICAgIFwgICAgICAgICAgICAg"

    fun ok(): String {
        return Base64.getDecoder().decode(OK).toString(Charsets.UTF_8)
    }

    fun setPayloadValueInt(value:Int, formatType:Int, offset:Int):Boolean{

        val newArraySize = getFormatTypeSize(formatType)+offset
        if(newArraySize>mockPayload.size){
            mockPayload = mockPayload.copyOf(newArraySize)
        }
        when(FormatType.fromType(formatType)){
            FORMAT_UINT8->{
                mockPayload.set(offset, value.toByte())
                return true
            }
            FORMAT_UINT16->{
                mockPayload.set(offset, value.toByte())
                mockPayload.set(offset+1, (value shr 8).toByte())
                return true
            }
            else ->{
                throw IllegalArgumentException("Currently we does not support this integer type")
            }
        }
        return false
    }

    fun setPayloadValueFloat(mantissa:Int, exponent: Int, formatType:Int, offset:Int):Boolean{

        val newArraySize = getFormatTypeSize(formatType)+offset
        if(newArraySize>mockPayload.size){
            mockPayload = mockPayload.copyOf(newArraySize)
        }
        when(FormatType.fromType(formatType)){
            FORMAT_SFLOAT->{
                var lsb = mantissa and 0x000F
                var msb = (exponent and 0b00001111) + (mantissa and 0x007F)
                mockPayload.set(offset, lsb.toByte())
                mockPayload.set(offset+1, msb.toByte())
                return true
            }
            else ->{
                throw IllegalArgumentException("Currently we does not support this integer type")
            }
        }
        return false
    }

    /**
     * Get the size of format type
     */
    private fun getFormatTypeSize(formatType:Int):Int{
        return  formatType and 0x0f
    }


}