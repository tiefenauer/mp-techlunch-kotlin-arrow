import org.assertj.core.api.Assertions.assertThat
import org.example.NumberParserWithNullability
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


class NumberParserWithNullabilityTest {

    @Test
    fun `should parse an int value`() {
        assertThat(NumberParserWithNullability.toInt("42")).isEqualTo(42)
    }

    @Test
    fun `should parse a double value`() {
        assertThat(NumberParserWithNullability.toInt("42.0")).isEqualTo(42)
    }

    @Test
    fun `should accept a comma as decimal point`() {
        assertThat(NumberParserWithNullability.toInt("42,0")).isEqualTo(42)
    }

    @ParameterizedTest
    @ValueSource(strings = ["42.4", "42.5", "42.6"])
    fun `should always round a double value up`(str: String) {
        assertThat(NumberParserWithNullability.toInt(str)).isEqualTo(43)
    }

    @Test
    fun `should throw an exception if int value is too high`() {
        // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/to-int.html
        val invalidValue = Int.MAX_VALUE.toLong() + 1
        assertThat(NumberParserWithNullability.toInt("$invalidValue")).isNull()
    }

    @Test
    fun `should return null if int value is too low`() {
        // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/to-int.html
        val invalidValue = Int.MIN_VALUE.toLong() - 1
        assertThat(NumberParserWithNullability.toInt("$invalidValue")).isNull()
    }

    @Test
    fun `should return null the decimal point is neither dot nor comma`() {
        assertThat(NumberParserWithNullability.toInt("42;0")).isNull()
    }

    @Test
    fun `should return null if the passed value is not a number`() {
        assertThat(NumberParserWithNullability.toInt("42.a")).isNull()
    }
}