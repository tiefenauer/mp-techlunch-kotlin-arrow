package org.example

import kotlin.math.ceil

object NumberParserWithNullability {
    /**
     * Converts a string-representation of a number to an int.
     * Fractional values will always be rounded up.
     * Both '.' and ',' are accepted as separators for the fractional part
     * @throws IllegalArgumentException if the string-representation of the int is not within the interval [Int.MIN_VALUE, Int.MAX_VALUE]
     * @throws NumberFormatException if the string is not a valid string-representation of a number
     */
    fun toInt(string: String): Int? {
        val result = try {
            try {
                string.toInt()
            } catch (e: NumberFormatException) {
                val double = try {
                    string.toDouble()
                } catch (e: NumberFormatException) {
                    string.replace(',', '.').toDouble()
                }
                val roundedValue = ceil(double)
                if (roundedValue > Int.MAX_VALUE) {
                    return null
                }
                if (roundedValue < Int.MIN_VALUE) {
                    return null
                }
                return roundedValue.toInt()
            }
        } catch (e: Exception) {
            return null
        }
        return result
    }
}