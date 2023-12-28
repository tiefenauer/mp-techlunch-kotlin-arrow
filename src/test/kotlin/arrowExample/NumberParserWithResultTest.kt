package arrowExample

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class NumberParserWithResultTest {
    @Test
    fun `should parse an int value`() {
        val result = NumberParserWithResult.toInt("42")
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(42)
    }

    @Test
    fun `should parse a double value`() {
        val result = NumberParserWithResult.toInt("42.0")
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(42)
    }

    @Test
    fun `should accept a comma as decimal point`() {
        val result = NumberParserWithResult.toInt("42,0")
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(42)
    }

    @ParameterizedTest
    @ValueSource(strings = ["42.4", "42.5", "42.6"])
    fun `should always round a double value up`(str: String) {
        val result = NumberParserWithResult.toInt(str)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(43)
    }

    @Test
    fun `should throw an exception if int value is too high`() {
        val invalidValue = Int.MAX_VALUE.toLong() + 1
        val result = NumberParserWithResult.toInt("$invalidValue")
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).contains(Int.MAX_VALUE.toString())
        assertThat(result.exceptionOrNull()?.message).contains(invalidValue.toString())
    }

    @Test
    fun `should throw an exception if int value is too low`() {
        val invalidValue = Int.MIN_VALUE.toLong() - 1
        val result = NumberParserWithResult.toInt("$invalidValue")
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).contains(Int.MIN_VALUE.toString())
        assertThat(result.exceptionOrNull()?.message).contains(invalidValue.toString())
    }

    @Test
    fun `should throw an exception if the decimal point is neither dot nor comma`() {
        val result = NumberParserWithResult.toInt("42;0")
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(NumberFormatException::class.java)
    }

    @Test
    fun `should throw an exception if the passed value is not a number`() {
        val result = NumberParserWithResult.toInt("42.a")
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(NumberFormatException::class.java)
    }
}