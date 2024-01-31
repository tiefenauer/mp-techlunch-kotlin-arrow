package arrowExample

import arrow.core.getOrElse

fun main() {
    println("Calculating the meaning of life using Nullability")
    // since there's no information about what went wrong, we default to 0, even though in this concrete example it would not be needed!
    var meaningOfLife = NumberParserWithNullability.toReciprocal("42.0") ?: 0
    println("the meaning of life is: $meaningOfLife")

    println("Calculating the meaning of life using Exceptions")
    meaningOfLife = try {
        NumberParserWithExceptions.toReciprocal("42.0")
    } catch (e: Throwable) { // no real error handling but better than nothing (I wouldn't even be forced to implement any error handling!)
        println("An error occurred: $e")
        0 // again, default to zero
    }
    println("the meaning of life is: $meaningOfLife")

    println("Calculating the meaning of life Kotlin's Result<Int>")
    meaningOfLife = NumberParserWithResult.toReciprocal("42.0").getOrElse {
        println("An error occurred: $it")
        0
    }
    println("the meaning of life is: $meaningOfLife")

    println("Calculating the meaning of life Arrow's Either<ParseError, Int>")
    meaningOfLife = NumberParseMonad.toReciprocal("42.0").getOrElse {
        println("An error occurred: $it") // note that it is typed!
        0
    }
    println("the meaning of life is: $meaningOfLife")
}