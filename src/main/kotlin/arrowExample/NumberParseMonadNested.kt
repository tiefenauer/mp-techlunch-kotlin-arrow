package arrowExample

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import kotlin.math.ceil

/**
 * Example of using Arrow's Either-Monad
 */
object NumberParseMonadNested {

    sealed class ParseError {
        data object NotANumber : ParseError()
        data object ValueTooHigh : ParseError()
        data object ValueTooLow : ParseError()
    }

    /**
     * Converts a string-representation of a number to an int.
     * Fractional values will always be rounded up.
     * Both '.' and ',' are accepted as separators for the fractional part
     */
    fun toInt(string: String) = when (val intEither = Either.catch { string.toInt() }) {
        is Either.Right -> intEither
        else -> when (val doubleEither1 = Either.catch { string.toDouble() }) {
            is Either.Right -> doubleEither1.toRoundedInt()
            else -> when (val doubleEither2 = Either.catch { string.replace(',', '.').toDouble() }) {
                is Either.Right -> doubleEither2.toRoundedInt()
                else -> ParseError.NotANumber.left()
            }
        }
    }

    private fun Either<ParseError, Double>.toRoundedInt() = flatMap { it.checkAndRound() }.map { it.toInt() }

    private fun Double.checkAndRound() = when {
        this > Int.MAX_VALUE -> ParseError.ValueTooHigh.left()
        this < Int.MIN_VALUE -> ParseError.ValueTooLow.left()
        else -> ceil(this).right()
    }
}
