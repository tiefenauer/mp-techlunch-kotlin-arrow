package arrowExample

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class NumberParserWithExceptionsTest {

    @Test
    fun `should parse an int value`() {
        assertThat(NumberParserWithExceptions.toReciprocal("42")).isEqualTo(1 / 42.toDouble())
    }

    @Test
    fun `should parse a double value`() {
        assertThat(NumberParserWithExceptions.toReciprocal("42.0")).isEqualTo(1 / 42.toDouble())
    }

    @Test
    fun `should accept a comma as decimal point`() {
        assertThat(NumberParserWithExceptions.toReciprocal("42,0")).isEqualTo(1 / 42.toDouble())
    }

    @ParameterizedTest
    @ValueSource(strings = ["42.4", "42.5", "42.6"])
    fun `should always round a double value up`(str: String) {
        assertThat(NumberParserWithExceptions.toReciprocal(str)).isEqualTo(1 / 43.toDouble())
    }

    @Test
    fun `should throw an exception if int value is too high`() {
        // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/to-int.html
        val invalidValue = Int.MAX_VALUE.toLong() + 1
        val thrown = assertThrows<IllegalArgumentException> { NumberParserWithExceptions.toReciprocal("$invalidValue") }
        assertThat(thrown.message).contains(Int.MAX_VALUE.toString())
        assertThat(thrown.message).contains(invalidValue.toString())
    }

    @Test
    fun `should throw an exception if int value is too low`() {
        // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/to-int.html
        val invalidValue = Int.MIN_VALUE.toLong() - 1
        val thrown = assertThrows<IllegalArgumentException> { NumberParserWithExceptions.toReciprocal("$invalidValue") }
        assertThat(thrown.message).contains(Int.MIN_VALUE.toString())
        assertThat(thrown.message).contains(invalidValue.toString())
    }

    @Test
    fun `should throw an exception if the decimal point is neither dot nor comma`() {
        assertThrows<NumberFormatException> { NumberParserWithExceptions.toReciprocal("42;0") }
    }

    @Test
    fun `should throw an exception if the passed value is not a number`() {
        assertThrows<NumberFormatException> { NumberParserWithExceptions.toReciprocal("42.a") }
    }
}