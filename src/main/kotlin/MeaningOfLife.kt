package org.example

import arrow.core.Either

fun main() {
    println("Calculating the meaning of life using Nullability")
    // since there's no information about what went wrong, we default to 0, even though in this concrete example it would not be needed!
    var meaningOfLife = NumberParserWithNullability.toInt("42.0") ?: 0
    println("the meaning of life is: $meaningOfLife")

    println("Calculating the meaning of life using Exceptions")
    meaningOfLife = try {
        NumberParserWithExceptions.toInt("42.0")
    } catch (e: Throwable) { // no real error handling but better than nothin (I wouldn't even be forced to implement any error handling!)
        0 // again, default to zero
    }
    println("the meaning of life is: $meaningOfLife")

    println("Calculating the meaning of life Kotlin's Result<Int>")
    meaningOfLife = NumberParserWithResult.toInt("42.0")
        .recover { 0 }
        .getOrElse {
            println("An error occurred: $it")
            0
        }
    println("the meaning of life is: $meaningOfLife")

    println("Calculating the meaning of life Arrow's Either<ParseError, Int>")
    meaningOfLife = when (val either = NumberParseMonad.toInt("42.0")) {
        is Either.Right -> either.value
        is Either.Left -> {
            println("An error occurred: ${either.value}")
            0
        }
    }
    println("the meaning of life is: $meaningOfLife")
}