package arrowExample

import kotlin.math.ceil

object NumberParserWithRunCatching {
    /**
     * Converts a string-representation of a number to an int.
     * Fractional values will always be rounded up.
     * Both '.' and ',' are accepted as separators for the fractional part
     * @throws IllegalArgumentException if the string-representation of the int is not within the interval [Int.MIN_VALUE, Int.MAX_VALUE]
     * @throws NumberFormatException if the string is not a valid string-representation of a number
     */
    fun toInt(string: String): Int {
        val intResult = kotlin.runCatching { string.toInt() }
        if (intResult.isFailure) {
            var doubleResult = kotlin.runCatching { string.toDouble() }
            if (doubleResult.isFailure) {
                doubleResult = kotlin.runCatching { string.replace(',', '.').toDouble() }
            }
            val roundedValue = doubleResult.map { ceil(it) }.getOrThrow()
            if (roundedValue > Int.MAX_VALUE) {
                throw IllegalArgumentException("$string is invalid: string value cannot be greater than ${Int.MAX_VALUE}")
            }
            if (roundedValue < Int.MIN_VALUE) {
                throw IllegalArgumentException("$string is invalid: string value cannot be less than ${Int.MIN_VALUE}")
            }
            return roundedValue.toInt()
        }
        return intResult.getOrThrow()
    }
}