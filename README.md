# MP Techlunch: Kotlin Arrow
This repository contains the code samples from the presentation and the presentation itself. The code samples are working code that can be executed by running the tests.

* Presentation [Kotlin_Arrow_TechLunch.pdf](Kotlin_Arrow_TechLunch.pdf)
* Code samples:
  * Implementation using nullable types: [NumberParserWithNullability.kt](src%2Fmain%2Fkotlin%2FarrowExample%2FNumberParserWithNullability.kt)
  * Implementation using non-nullable types and throwing exceptions: [NumberParserWithExceptions.kt](src%2Fmain%2Fkotlin%2FarrowExample%2FNumberParserWithExceptions.kt)
  * Implementation using Kotlin's `Result<T>` monad: [NumberParserWithResult.kt](src%2Fmain%2Fkotlin%2FarrowExample%2FNumberParserWithResult.kt)
  * Implementation using Kotlin's `runCatching` monad: [NumberParserWithRunCatching.kt](src%2Fmain%2Fkotlin%2FarrowExample%2FNumberParserWithRunCatching.kt)
  * Implementation using Arrow's `Either<A, B>` monad: [NumberParseMonad.kt](src%2Fmain%2Fkotlin%2FarrowExample%2FNumberParseMonad.kt)
  * Implementation using Arrow's `Either<A, B>` monad (alternative): [NumberParseMonadNested.kt](src%2Fmain%2Fkotlin%2FarrowExample%2FNumberParseMonadNested.kt)
  * Implementation using Arrow's `Raise`: [NumberParserMonadRaise.kt](src%2Fmain%2Fkotlin%2FarrowExample%2FNumberParserMonadRaise.kt)