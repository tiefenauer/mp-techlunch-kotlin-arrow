package arrowExample

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class NumberParserWithNullabilityTest {

    @Test
    fun `should parse an int value`() {
        assertThat(NumberParserWithNullability.toReciprocal("42")).isEqualTo(1 / 42.toDouble())
    }

    @Test
    fun `should parse a double value`() {
        assertThat(NumberParserWithNullability.toReciprocal("42.0")).isEqualTo(1 / 42.toDouble())
    }

    @Test
    fun `should accept a comma as decimal point`() {
        assertThat(NumberParserWithNullability.toReciprocal("42,0")).isEqualTo(1 / 42.toDouble())
    }

    @ParameterizedTest
    @ValueSource(strings = ["42.4", "42.5", "42.6"])
    fun `should always round a double value up`(str: String) {
        assertThat(NumberParserWithNullability.toReciprocal(str)).isEqualTo(1 / 43.toDouble())
    }

    @Test
    fun `should throw an exception if int value is too high`() {
        // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/to-int.html
        val invalidValue = Int.MAX_VALUE.toLong() + 1
        assertThat(NumberParserWithNullability.toReciprocal("$invalidValue")).isNull()
    }

    @Test
    fun `should return null if int value is too low`() {
        // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/to-int.html
        val invalidValue = Int.MIN_VALUE.toLong() - 1
        assertThat(NumberParserWithNullability.toReciprocal("$invalidValue")).isNull()
    }

    @Test
    fun `should return null the decimal point is neither dot nor comma`() {
        assertThat(NumberParserWithNullability.toReciprocal("42;0")).isNull()
    }

    @Test
    fun `should return null if the passed value is not a number`() {
        assertThat(NumberParserWithNullability.toReciprocal("42.a")).isNull()
    }
}