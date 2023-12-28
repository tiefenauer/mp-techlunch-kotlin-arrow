package org.example

import arrow.core.Either.Companion.catch
import arrow.core.raise.either
import arrow.core.raise.ensure
import kotlin.math.ceil

object NumberParserMonadRaise {
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
    fun toInt(string: String) = either {
        catch { return@either string.toInt() }
        catch { return@either string.toDouble().toRoundedInt().bind() }
        catch { return@either string.replace(',', '.').toDouble().toRoundedInt().bind() }
        raise(ParseError.NotANumber)
    }

    private fun Double.toRoundedInt() = either {
        val double = this@toRoundedInt
        ensure(double > Int.MIN_VALUE) { ParseError.ValueTooLow }
        ensure(double < Int.MAX_VALUE) { ParseError.ValueTooHigh }
        ceil(double).toInt()
    }

}