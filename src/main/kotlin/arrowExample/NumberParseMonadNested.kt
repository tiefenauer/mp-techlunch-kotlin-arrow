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
        data object ValueIsZero : NumberParseMonad.ParseError()
        data object ValueTooHigh : ParseError()
        data object ValueTooLow : ParseError()
    }

    /**
     * Converts a string-representation of a number to its reciprocal double value.
     * Fractional values will always be rounded up.
     * Both '.' and ',' are accepted as separators for the fractional part
     */
    fun toReciprocal(string: String) = when (val intEither = Either.catch { string.toInt() }) {
        is Either.Right -> intEither.flatMap { it.toDouble().reciprocal() }
        else -> when (val doubleEither1 = Either.catch { string.toDouble() }) {
            is Either.Right -> doubleEither1.toRoundedDouble()
            else -> when (val doubleEither2 = Either.catch { string.replace(',', '.').toDouble() }) {
                is Either.Right -> doubleEither2.toRoundedDouble()
                else -> ParseError.NotANumber.left()
            }
        }
    }

    private fun Double.reciprocal() = if (this == 0.0) ParseError.ValueIsZero.left() else (1 / this).right()

    private fun Either<ParseError, Double>.toRoundedDouble() = flatMap { it.checkAndRound() }.flatMap { it.reciprocal() }

    private fun Double.checkAndRound() = when {
        this > Int.MAX_VALUE -> ParseError.ValueTooHigh.left()
        this < Int.MIN_VALUE -> ParseError.ValueTooLow.left()
        else -> ceil(this).right()
    }
}
