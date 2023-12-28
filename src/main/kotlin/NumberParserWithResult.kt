package org.example

import arrow.core.flatMap
import kotlin.math.ceil

object NumberParserWithResult {
    /**
     * Converts a string-representation of a number to an int.
     * Fractional values will always be rounded up.
     * Both '.' and ',' are accepted as separators for the fractional part
     * @throws IllegalArgumentException if the string-representation of the int is not within the interval [Int.MIN_VALUE, Int.MAX_VALUE]
     * @throws NumberFormatException if the string is not a valid string-representation of a number
     */
    fun toInt(string: String) = runCatching { string.toInt() }
        .recoverCatching {
            runCatching { string.toDouble() }
                .recoverCatching { string.replace(',', '.').toDouble() }
                .flatMap { runCatching { it.checkValue(string) } }
                .map { ceil(it).toInt() }
                .getOrThrow()
        }

    private fun Double.checkValue(str: String) = when {
        this > Int.MAX_VALUE -> throw IllegalArgumentException("$str is invalid: string value cannot be greater than ${Int.MAX_VALUE}")
        this < Int.MIN_VALUE -> throw IllegalArgumentException("$str is invalid: string value cannot be less than ${Int.MIN_VALUE}")
        else -> this
    }
}