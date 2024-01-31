package arrowExample

import arrow.core.*
import arrow.core.raise.either
import arrow.core.raise.ensure
import kotlin.math.ceil

object NumberParseMonad {
    sealed class ParseError {
        data object NotANumber : ParseError()
        data object ValueIsZero : ParseError()
        data object ValueTooHigh : ParseError()
        data object ValueTooLow : ParseError()
    }

    /**
     * Converts a string-representation of a number to its reciprocal double value.
     */
    fun toReciprocal(string: String) = string.parseWithDot()
        .recover { string.parseWithComma().bind() }
        .flatMap { it.toRoundedDouble() }
        .flatMap { it.reciprocal() }

    private fun String.parseWithDot() = Either.catch { toDouble() }.mapLeft { ParseError.NotANumber }

    private fun String.parseWithComma() = Either.catch { replace(',', '.').toDouble() }.mapLeft { ParseError.NotANumber }

    private fun Double.toRoundedDouble() = either {
        val double = this@toRoundedDouble
        ensure(double > Int.MIN_VALUE) { ParseError.ValueTooLow }
        ensure(double < Int.MAX_VALUE) { ParseError.ValueTooHigh }
        ceil(double)
    }

    private fun Double.reciprocal() = if (this == 0.0) ParseError.ValueIsZero.left() else (1 / this).right()

    /**
     * this is the same as [toReciprocal], but deconstructed for understandability
     */
    @Suppress("unused")
    fun toReciprocalDeconstructed(string: String): Either<ParseError, Double> {
        val intOrNull: Int? = string.toIntOrNull() // let's try to parse the String as an int
        if (intOrNull != null) { // if successful (=not null)...
            if (intOrNull == 0) { // ... check if the value is zero
                return ParseError.ValueIsZero.left()
            }
            return (1 / intOrNull.toDouble()).right() //  we turn it into an Either (Right<Int>) and return it as-is
        }

        var doubleOrNull = string.toDoubleOrNull() // let's try and parse it as a double

        if (doubleOrNull == null) { // too bad, but maybe the input used the comma as decimal point?
            doubleOrNull = string.replace(',', '.').toDoubleOrNull() // let's replace the comma and try again
        }

        if (doubleOrNull != null) { // great success!
            // now check the value is inside the interval
            if (doubleOrNull > Int.MAX_VALUE) {
                return ParseError.ValueTooHigh.left() // value is too high
            }
            if (doubleOrNull < Int.MIN_VALUE) {
                return ParseError.ValueTooLow.left() // value is too high
            }
            val roundedDouble = ceil(doubleOrNull)
            return (1 / roundedDouble).right() // all good, now round it up, convert to an int and return the result
        }
        return ParseError.NotANumber.left() // this is the ultimate fallback: the string did not contain one of the accepted formats
    }

}
