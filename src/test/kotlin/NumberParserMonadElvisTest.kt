import arrow.core.left
import arrow.core.right
import org.assertj.core.api.Assertions.assertThat
import org.example.NumberParseMonadElvis
import org.example.ParseError
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


class NumberParserMonadElvisTest {

    @Test
    fun `should parse an int value`() {
        val either = NumberParseMonadElvis.toInt("42")
        assertThat(either).isEqualTo(42.right())
    }

    @Test
    fun `should parse a double value`() {
        val either = NumberParseMonadElvis.toInt("42.0")
        assertThat(either).isEqualTo(42.right())
    }

    @Test
    fun `should accept a comma as decimal point`() {
        val either = NumberParseMonadElvis.toInt("42,0")
        assertThat(either).isEqualTo(42.right())
    }

    @ParameterizedTest
    @ValueSource(strings = ["42.4", "42.5", "42.6"])
    fun `should always round a double value up`(str: String) {
        val either = NumberParseMonadElvis.toInt(str)
        assertThat(either).isEqualTo(43.right())
    }

    @Test
    fun `should throw an exception if int value is too high`() {
        val either = NumberParseMonadElvis.toInt("${Int.MAX_VALUE.toLong() + 1}")
        assertThat(either).isEqualTo(ParseError.ValueTooHigh.left())
    }

    @Test
    fun `should throw an exception if int value is too low`() {
        val either = NumberParseMonadElvis.toInt("${Int.MIN_VALUE.toLong() - 1}")
        assertThat(either).isEqualTo(ParseError.ValueTooLow.left())
    }

    @Test
    fun `should throw an exception if the decimal point is neither dot nor comma`() {
        val either = NumberParseMonadElvis.toInt("42;0")
        assertThat(either).isEqualTo(ParseError.NotANumber.left())
    }

    @Test
    fun `should throw an exception if the passed value is not a number`() {
        val either = NumberParseMonadElvis.toInt("42.a")
        assertThat(either).isEqualTo(ParseError.NotANumber.left())
    }
}