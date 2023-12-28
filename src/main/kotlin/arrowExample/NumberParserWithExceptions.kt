package arrowExample

import kotlin.math.ceil

/**
 * Improved approach implementing a function that will throw an Exception to indicate that something went wrong
 */
object NumberParserWithExceptions {

    /**
     * Converts a string-representation of a number to an int.
     * Fractional values will always be rounded up.
     * Both '.' and ',' are accepted as separators for the fractional part
     * @throws IllegalArgumentException if the string-representation of the int is not within the interval [Int.MIN_VALUE, Int.MAX_VALUE]
     * @throws NumberFormatException if the string is not a valid string-representation of a number
     */
    fun toInt(string: String) = try {
        string.toInt()
    } catch (e: NumberFormatException) {
        val double = try {
            string.toDouble()
        } catch (e: NumberFormatException) {
            string.replace(',', '.').toDouble()
        }
        val roundedValue = ceil(double)
        if (roundedValue > Int.MAX_VALUE) {
            throw IllegalArgumentException("$string is invalid: string value cannot be greater than ${Int.MAX_VALUE}")
        }
        if (roundedValue < Int.MIN_VALUE) {
            throw IllegalArgumentException("$string is invalid: string value cannot be less than ${Int.MIN_VALUE}")
        }
        roundedValue.toInt()
    }
}