package arrowExample

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right
import kotlin.math.ceil

object NumberParseMonad {
    sealed class ParseError {
        data object NotANumber : ParseError()
        data object ValueTooHigh : ParseError()
        data object ValueTooLow : ParseError()
    }

    fun toInt(string: String) =
        string.toIntOrNull()?.right()
            ?: string.toDoubleOrNull()?.toRoundedInt()
            ?: string.replace(',', '.').toDoubleOrNull()?.toRoundedInt()
            ?: ParseError.NotANumber.left()

    /**
     * this is the same as [toInt], but deconstructed for understandability
     */
    @Suppress("unused")
    fun toIntVerbose(string: String): Either<ParseError, Int> {
        val intOrNull: Int? = string.toIntOrNull() // let's try to parse the String as an int
        val intEither = intOrNull?.right() // if successful (=not null) we turn it into an Either (Right<Int>)
        if (intEither != null) {
            return intEither // return the either as-is
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
            return ceil(doubleOrNull).toInt().right() // all good, now round it up, convert to an int and return the result
        }
        return ParseError.NotANumber.left() // this is the ultimate fallback: the string did not contain one of the accepted formats
    }

    private fun Double.toRoundedInt() = either {
        val double = this@toRoundedInt
        ensure(double > Int.MIN_VALUE) { ParseError.ValueTooLow }
        ensure(double < Int.MAX_VALUE) { ParseError.ValueTooHigh }
        ceil(double).toInt()
    }
}
