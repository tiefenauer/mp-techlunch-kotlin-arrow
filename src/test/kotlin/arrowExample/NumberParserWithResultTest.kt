package arrowExample

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class NumberParserWithResultTest {
    @Test
    fun `should parse an int value`() {
        val result = NumberParserWithResult.toReciprocal("42")
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(1 / 42.toDouble())
    }

    @Test
    fun `should parse a double value`() {
        val result = NumberParserWithResult.toReciprocal("42.0")
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(1 / 42.toDouble())
    }

    @Test
    fun `should accept a comma as decimal point`() {
        val result = NumberParserWithResult.toReciprocal("42,0")
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(1 / 42.toDouble())
    }

    @ParameterizedTest
    @ValueSource(strings = ["42.4", "42.5", "42.6"])
    fun `should always round a double value up`(str: String) {
        val result = NumberParserWithResult.toReciprocal(str)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(1 / 43.toDouble())
    }

    @Test
    fun `should be failure if int value is too high`() {
        val invalidValue = Int.MAX_VALUE.toLong() + 1
        val result = NumberParserWithResult.toReciprocal("$invalidValue")
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).contains(Int.MAX_VALUE.toString())
        assertThat(result.exceptionOrNull()?.message).contains(invalidValue.toString())
    }

    @Test
    fun `should be failure if int value is too low`() {
        val invalidValue = Int.MIN_VALUE.toLong() - 1
        val result = NumberParserWithResult.toReciprocal("$invalidValue")
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).contains(Int.MIN_VALUE.toString())
        assertThat(result.exceptionOrNull()?.message).contains(invalidValue.toString())
    }

    @Test
    fun `should be failure if the decimal point is neither dot nor comma`() {
        val result = NumberParserWithResult.toReciprocal("42;0")
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(NumberFormatException::class.java)
    }

    @Test
    fun `should be failure if the passed value is not a number`() {
        val result = NumberParserWithResult.toReciprocal("42.a")
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(NumberFormatException::class.java)
    }
}