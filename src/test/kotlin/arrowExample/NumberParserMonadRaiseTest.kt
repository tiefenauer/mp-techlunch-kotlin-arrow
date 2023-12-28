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
        val either = NumberParserMonadRaise.toInt("42")
        assertThat(either).isEqualTo(42.right())
    }

    @Test
    fun `should parse a double value`() {
        val either = NumberParserMonadRaise.toInt("42.0")
        assertThat(either).isEqualTo(42.right())
    }

    @Test
    fun `should accept a comma as decimal point`() {
        val either = NumberParserMonadRaise.toInt("42,0")
        assertThat(either).isEqualTo(42.right())
    }

    @ParameterizedTest
    @ValueSource(strings = ["42.4", "42.5", "42.6"])
    fun `should always round a double value up`(str: String) {
        val either = NumberParserMonadRaise.toInt(str)
        assertThat(either).isEqualTo(43.right())
    }

    @Test
    fun `should throw an exception if int value is too high`() {
        val either = NumberParserMonadRaise.toInt("${Int.MAX_VALUE.toLong() + 1}")
        assertThat(either).isEqualTo(NumberParserMonadRaise.ParseError.ValueTooHigh.left())
    }

    @Test
    fun `should throw an exception if int value is too low`() {
        val either = NumberParserMonadRaise.toInt("${Int.MIN_VALUE.toLong() - 1}")
        assertThat(either).isEqualTo(NumberParserMonadRaise.ParseError.ValueTooLow.left())
    }

    @Test
    fun `should throw an exception if the decimal point is neither dot nor comma`() {
        val either = NumberParserMonadRaise.toInt("42;0")
        assertThat(either).isEqualTo(NumberParserMonadRaise.ParseError.NotANumber.left())
    }

    @Test
    fun `should throw an exception if the passed value is not a number`() {
        val either = NumberParserMonadRaise.toInt("42.a")
        assertThat(either).isEqualTo(NumberParserMonadRaise.ParseError.NotANumber.left())
    }
}