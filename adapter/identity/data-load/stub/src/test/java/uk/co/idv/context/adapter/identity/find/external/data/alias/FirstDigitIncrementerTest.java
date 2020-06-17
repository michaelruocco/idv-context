package uk.co.idv.context.adapter.identity.find.external.data.alias;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static uk.co.idv.context.adapter.identity.find.external.data.alias.FirstDigitIncrementer.incrementFirstDigit;

class FirstDigitIncrementerTest {

    @Test
    void shouldIncrementFirstDigitInInputString() {
        String input = "ABC199";

        String incremented = incrementFirstDigit(input);

        assertThat(incremented).isEqualTo("ABC299");
    }

    @Test
    void shouldIncrement9To0() {
        String input = "ABC999";

        String incremented = incrementFirstDigit(input);

        assertThat(incremented).isEqualTo("ABC099");
    }

    @Test
    void shouldThrowExceptionIfInputStringDoesNotContainDigit() {
        String input = "ABC";

        Throwable error = catchThrowable(() -> incrementFirstDigit(input));

        assertThat(error)
                .isInstanceOf(NoDigitsFoundException.class)
                .hasMessage(input);
    }

}
