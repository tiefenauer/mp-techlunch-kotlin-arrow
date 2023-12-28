package org.example

import arrow.core.left
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right
import kotlin.math.ceil

object NumberParseMonadElvis{
    fun toInt(string: String) =
        string.toIntOrNull()?.right()
            ?: string.toDoubleOrNull()?.toRoundedInt()
            ?: string.replace(',', '.').toDoubleOrNull()?.toRoundedInt()
            ?: ParseError.NotANumber.left()

    fun Double.toRoundedInt() = either {
        val double = this@toRoundedInt
        ensure(double > Int.MIN_VALUE) { ParseError.ValueTooLow }
        ensure(double < Int.MAX_VALUE) { ParseError.ValueTooHigh }
        ceil(double).toInt()
    }
}
