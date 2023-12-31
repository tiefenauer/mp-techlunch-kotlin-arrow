import arrow.core.left
import arrow.core.right
import org.assertj.core.api.Assertions.assertThat
import org.example.NumberParseMonadNested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


class NumberParserMonadNestedTest {

    @Test
    fun `should parse an int value`() {
        val either = NumberParseMonadNested.toInt("42")
        assertThat(either).isEqualTo(42.right())
    }

    @Test
    fun `should parse a double value`() {
        val either = NumberParseMonadNested.toInt("42.0")
        assertThat(either).isEqualTo(42.right())
    }

    @Test
    fun `should accept a comma as decimal point`() {
        val either = NumberParseMonadNested.toInt("42,0")
        assertThat(either).isEqualTo(42.right())
    }

    @ParameterizedTest
    @ValueSource(strings = ["42.4", "42.5", "42.6"])
    fun `should always round a double value up`(str: String) {
        val either = NumberParseMonadNested.toInt(str)
        assertThat(either).isEqualTo(43.right())
    }

    @Test
    fun `should throw an exception if int value is too high`() {
        val either = NumberParseMonadNested.toInt("${Int.MAX_VALUE.toLong() + 1}")
        assertThat(either).isEqualTo(NumberParseMonadNested.ParseError.ValueTooHigh.left())
    }

    @Test
    fun `should throw an exception if int value is too low`() {
        val either = NumberParseMonadNested.toInt("${Int.MIN_VALUE.toLong() - 1}")
        assertThat(either).isEqualTo(NumberParseMonadNested.ParseError.ValueTooLow.left())
    }

    @Test
    fun `should throw an exception if the decimal point is neither dot nor comma`() {
        val either = NumberParseMonadNested.toInt("42;0")
        assertThat(either).isEqualTo(NumberParseMonadNested.ParseError.NotANumber.left())
    }

    @Test
    fun `should throw an exception if the passed value is not a number`() {
        val either = NumberParseMonadNested.toInt("42.a")
        assertThat(either).isEqualTo(NumberParseMonadNested.ParseError.NotANumber.left())
    }
}