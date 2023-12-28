package org.example

import arrow.core.*
import arrow.core.raise.catch
import arrow.core.raise.either
import arrow.core.raise.ensure
import kotlin.math.ceil

sealed class ParseError {
    data object NotANumber : ParseError()
    data object ValueTooHigh : ParseError()
    data object ValueTooLow : ParseError()
}

fun toInt(string: String) = either {
    attemptCatching { return@either string.toInt() }
    attemptCatching { return@either string.toDouble().toRoundedInt().bind() }
    attemptCatching { return@either string.replace(',', '.').toDouble().toRoundedInt().bind() }
    raise(ParseError.NotANumber)
}

fun Double.toRoundedInt() = either {
    val double = this@toRoundedInt
    ensure(double > Int.MIN_VALUE) { ParseError.ValueTooLow }
    ensure(double < Int.MAX_VALUE) { ParseError.ValueTooHigh }
    ceil(double).toInt()
}

inline fun attemptCatching(block: () -> Nothing): Throwable =
    catch(block, ::identity)