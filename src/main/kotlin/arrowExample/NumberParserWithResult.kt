package arrowExample

import arrow.core.flatMap
import kotlin.math.ceil

/**
 * Kotlin-idiomatic approach implementing a function that will return an instance of Result<Int> to wrap the result (or the failure)
 */
object NumberParserWithResult {

    /**
     * Converts a string-representation of a number to an int.
     * Fractional values will always be rounded up.
     * Both '.' and ',' are accepted as separators for the fractional part
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