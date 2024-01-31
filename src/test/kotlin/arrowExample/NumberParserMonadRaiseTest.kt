package arrowExample

import arrow.core.left
import arrow.core.right
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class NumberParserMonadRaiseTest {

    @Test
    fun `should parse an int value`() {
        val result = NumberParserMonadRaise.toReciprocal("42")
        val expectedResult = 1 / 42.toDouble()
        assertThat(result).isEqualTo(expectedResult.right())
    }

    @Test
    fun `should parse a double value`() {
        val result = NumberParserMonadRaise.toReciprocal("42.0")
        val expectedResult = 1 / 42.toDouble()
        assertThat(result).isEqualTo(expectedResult.right())
    }

    @Test
    fun `should accept a comma as decimal point`() {
        val result = NumberParserMonadRaise.toReciprocal("42,0")
        val expectedResult = 1 / 42.toDouble()
        assertThat(result).isEqualTo(expectedResult.right())
    }

    @ParameterizedTest
    @ValueSource(strings = ["42.4", "42.5", "42.6"])
    fun `should always round a double value up`(str: String) {
        val result = NumberParserMonadRaise.toReciprocal(str)
        val expectedResult = 1 / 43.toDouble()
        assertThat(result).isEqualTo(expectedResult.right())
    }

    @Test
    fun `should be ValueIsZero if int value is '0'`() {
        val result = NumberParserMonadRaise.toReciprocal("0")
        assertThat(result).isEqualTo(NumberParserMonadRaise.ParseError.ValueIsZero.left())
    }

    @Test
    fun `should be ValueTooHigh if int value is too high`() {
        val result = NumberParserMonadRaise.toReciprocal("${Int.MAX_VALUE.toLong() + 1}")
        assertThat(result).isEqualTo(NumberParserMonadRaise.ParseError.ValueTooHigh.left())
    }

    @Test
    fun `should be ValueTooLow if int value is too low`() {
        val result = NumberParserMonadRaise.toReciprocal("${Int.MIN_VALUE.toLong() - 1}")
        assertThat(result).isEqualTo(NumberParserMonadRaise.ParseError.ValueTooLow.left())
    }

    @Test
    fun `should be NotANumber if the decimal point is neither dot nor comma`() {
        val result = NumberParserMonadRaise.toReciprocal("42;0")
        assertThat(result).isEqualTo(NumberParserMonadRaise.ParseError.NotANumber.left())
    }

    @Test
    fun `should be NotANumber if the passed value is not a number`() {
        val result = NumberParserMonadRaise.toReciprocal("42.a")
        assertThat(result).isEqualTo(NumberParserMonadRaise.ParseError.NotANumber.left())
    }
}