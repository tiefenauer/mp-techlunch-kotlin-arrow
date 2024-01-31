package arrowExample

import arrow.core.flatMap
import kotlin.math.ceil

/**
 * Kotlin-idiomatic approach implementing a function that will return an instance of Result<Int> to wrap the result (or the failure)
 */
object NumberParserWithResult {

    /**
     * Converts a string-representation of a number to its reciprocal double value.
     * Fractional values will always be rounded up.
     * Both '.' and ',' are accepted as separators for the fractional part
     */
    fun toReciprocal(string: String) = runCatching { string.toInt() }
        .map { it.toDouble() }
        .map { 1 / it }
        .recoverCatching {
            runCatching { string.toDouble() }
                .recoverCatching { string.replace(',', '.').toDouble() }
                .flatMap { runCatching { it.checkValue(string) } }
                .map { 1 / ceil(it) }
                .getOrThrow()
        }

    private fun Double.checkValue(str: String) = when {
        this > Int.MAX_VALUE -> throw IllegalArgumentException("$str is invalid: string value cannot be greater than ${Int.MAX_VALUE}")
        this < Int.MIN_VALUE -> throw IllegalArgumentException("$str is invalid: string value cannot be less than ${Int.MIN_VALUE}")
        else -> this
    }
}