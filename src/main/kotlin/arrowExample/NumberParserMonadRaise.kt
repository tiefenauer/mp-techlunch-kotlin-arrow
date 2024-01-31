package arrowExample

import arrow.core.Either.Companion.catch
import arrow.core.flatMap
import arrow.core.left
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right
import kotlin.math.ceil

object NumberParserMonadRaise {
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
    fun toReciprocal(string: String) = either {
        catch { return@either string.toInt().toDouble() }
        catch { return@either string.toDouble().toRoundedInt().bind() }
        catch { return@either string.replace(',', '.').toDouble().toRoundedInt().bind() }
        raise(ParseError.NotANumber)
    }.flatMap { it.reciprocal() }

    private fun Double.reciprocal() = if (this == 0.0) ParseError.ValueIsZero.left() else (1 / this).right()

    private fun Double.toRoundedInt() = either {
        val double = this@toRoundedInt
        ensure(double > Int.MIN_VALUE) { ParseError.ValueTooLow }
        ensure(double < Int.MAX_VALUE) { ParseError.ValueTooHigh }
        ceil(double)
    }

}