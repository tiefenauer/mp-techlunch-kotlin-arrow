package arrowExample

import kotlin.math.ceil

/**
 * Naive approach implementing a function that will return null to indicate that something went wrong
 */
object NumberParserWithNullability {

    /**
     * Converts a string-representation of a number to its reciprocal double value.
     * Fractional values will always be rounded up.
     * Both '.' and ',' are accepted as separators for the fractional part
     */
    fun toReciprocal(string: String): Double? {
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
                if (roundedValue == 0.0) {
                    return null
                }
                roundedValue.toInt()
            }
        } catch (e: Exception) {
            return null
        }
        return 1 / result.toDouble()
    }
}