import org.assertj.core.api.Assertions
import org.example.NumberParserWithRunCatching
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class NumberParserWithRunCatchingTest {
    @Test
    fun `should parse an int value`() {
        Assertions.assertThat(NumberParserWithRunCatching.toInt("42")).isEqualTo(42)
    }

    @Test
    fun `should parse a double value`() {
        Assertions.assertThat(NumberParserWithRunCatching.toInt("42.0")).isEqualTo(42)
    }

    @Test
    fun `should accept a comma as decimal point`() {
        Assertions.assertThat(NumberParserWithRunCatching.toInt("42,0")).isEqualTo(42)
    }

    @ParameterizedTest
    @ValueSource(strings = ["42.4", "42.5", "42.6"])
    fun `should always round a double value up`(str: String) {
        Assertions.assertThat(NumberParserWithRunCatching.toInt(str)).isEqualTo(43)
    }

    @Test
    fun `should throw an exception if int value is too high`() {
        // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/to-int.html
        val invalidValue = Int.MAX_VALUE.toLong() + 1
        val thrown = org.junit.jupiter.api.assertThrows<IllegalArgumentException> { NumberParserWithRunCatching.toInt("$invalidValue") }
        Assertions.assertThat(thrown.message).contains(Int.MAX_VALUE.toString())
        Assertions.assertThat(thrown.message).contains(invalidValue.toString())
    }

    @Test
    fun `should throw an exception if int value is too low`() {
        // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/to-int.html
        val invalidValue = Int.MIN_VALUE.toLong() - 1
        val thrown = org.junit.jupiter.api.assertThrows<IllegalArgumentException> { NumberParserWithRunCatching.toInt("$invalidValue") }
        Assertions.assertThat(thrown.message).contains(Int.MIN_VALUE.toString())
        Assertions.assertThat(thrown.message).contains(invalidValue.toString())
    }

    @Test
    fun `should throw an exception if the decimal point is neither dot nor comma`() {
        org.junit.jupiter.api.assertThrows<NumberFormatException> { NumberParserWithRunCatching.toInt("42;0") }
    }

    @Test
    fun `should throw an exception if the passed value is not a number`() {
        org.junit.jupiter.api.assertThrows<NumberFormatException> { NumberParserWithRunCatching.toInt("42.a") }
    }
}