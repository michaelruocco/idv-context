package uk.co.idv.identity.adapter.eligibility.external.data.alias;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class FirstDigitIncrementerTest {

    @Test
    void shouldIncrementFirstDigitInInputString() {
        String input = "ABC199";

        String incremented = FirstDigitIncrementer.incrementFirstDigit(input);

        assertThat(incremented).isEqualTo("ABC299");
    }

    @Test
    void shouldIncrement9To0() {
        String input = "ABC999";

        String incremented = FirstDigitIncrementer.incrementFirstDigit(input);

        assertThat(incremented).isEqualTo("ABC099");
    }

    @Test
    void shouldThrowExceptionIfInputStringDoesNotContainDigit() {
        String input = "ABC";

        Throwable error = catchThrowable(() -> FirstDigitIncrementer.incrementFirstDigit(input));

        assertThat(error)
                .isInstanceOf(NoDigitsFoundException.class)
                .hasMessage(input);
    }

}
