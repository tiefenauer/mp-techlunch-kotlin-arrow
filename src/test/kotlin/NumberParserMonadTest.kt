import arrow.core.left
import arrow.core.right
import org.assertj.core.api.Assertions.assertThat
import org.example.ParseError
import org.example.toInt
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


class NumberParserMonadTest {

    @Test
    fun `should parse an int value`() {
        assertThat(toInt("42")).isEqualTo(42.right())
    }

    @Test
    fun `should parse a double value`() {
        assertThat(toInt("42.0")).isEqualTo(42.right())
    }

    @Test
    fun `should accept a comma as decimal point`() {
        assertThat(toInt("42,0")).isEqualTo(42.right())
    }

    @ParameterizedTest
    @ValueSource(strings = ["42.4", "42.5", "42.6"])
    fun `should always round a double value up`(str: String) {
        assertThat(toInt(str)).isEqualTo(43.right())
    }

    @Test
    fun `should throw an exception if int value is too high`() {
        // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/to-int.html
        val invalidValue = Int.MAX_VALUE.toLong() + 1
        assertThat(toInt("$invalidValue")).isEqualTo(ParseError.ValueTooHigh.left())
    }

    @Test
    fun `should throw an exception if int value is too low`() {
        // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/to-int.html
        val invalidValue = Int.MIN_VALUE.toLong() - 1
        assertThat(toInt("$invalidValue")).isEqualTo(ParseError.ValueTooLow.left())
    }

    @Test
    fun `should throw an exception if the decimal point is neither dot nor comma`() {
        assertThat(toInt("42;0")).isEqualTo(ParseError.NotANumber.left())
    }

    @Test
    fun `should throw an exception if the passed value is not a number`() {
        assertThat(toInt("42.a")).isEqualTo(ParseError.NotANumber.left())
    }
}