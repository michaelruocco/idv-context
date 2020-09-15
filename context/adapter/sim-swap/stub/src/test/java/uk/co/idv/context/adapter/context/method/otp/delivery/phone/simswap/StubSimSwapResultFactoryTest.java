package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class StubSimSwapResultFactoryTest {

    private static final Instant NOW = Instant.parse("2020-09-13T20:01:01.001Z");

    private final SimSwapConfig config = mock(SimSwapConfig.class);
    private final Clock clock = Clock.fixed(NOW, ZoneId.systemDefault());

    private final StubSimSwapResultFactory factory = StubSimSwapResultFactory.builder()
            .clock(clock)
            .build();

    @Test
    void shouldReturnFailureResultIfNumberEndsWith9() {
        OtpPhoneNumber number = givenNumberWithLastDigit(9);

        SimSwapResult result = factory.build(number, config);

        assertThat(result)
                .hasFieldOrPropertyWithValue("status", "failure")
                .hasFieldOrPropertyWithValue("config", config)
                .hasFieldOrPropertyWithValue("lastSwapped", null);
    }

    @Test
    void shouldReturnUnknownResultIfNumberEndsWith8() {
        OtpPhoneNumber number = givenNumberWithLastDigit(8);

        SimSwapResult result = factory.build(number, config);

        assertThat(result)
                .hasFieldOrPropertyWithValue("status", "unknown")
                .hasFieldOrPropertyWithValue("config", config)
                .hasFieldOrPropertyWithValue("lastSwapped", null);
    }

    @Test
    void shouldReturnUnknownResultIfNumberEndsWith7() {
        OtpPhoneNumber number = givenNumberWithLastDigit(7);

        SimSwapResult result = factory.build(number, config);

        assertThat(result)
                .hasFieldOrPropertyWithValue("status", "timeout")
                .hasFieldOrPropertyWithValue("config", config)
                .hasFieldOrPropertyWithValue("lastSwapped", null);
    }

    @Test
    void shouldReturnSuccessResultWithLastSwapped5DaysAgoIfNumberEndsWith6() {
        OtpPhoneNumber number = givenNumberWithLastDigit(6);

        SimSwapResult result = factory.build(number, config);

        Instant expectedLastSwapped = NOW.minus(Duration.ofDays(5));
        assertThat(result)
                .hasFieldOrPropertyWithValue("status", "success")
                .hasFieldOrPropertyWithValue("config", config)
                .hasFieldOrPropertyWithValue("lastSwapped", expectedLastSwapped);
    }

    @Test
    void shouldReturnSuccessResultIfNumberEndsWith5() {
        OtpPhoneNumber number = givenNumberWithLastDigit(5);

        SimSwapResult result = factory.build(number, config);

        assertThat(result)
                .hasFieldOrPropertyWithValue("status", "success")
                .hasFieldOrPropertyWithValue("config", config)
                .hasFieldOrPropertyWithValue("lastSwapped", null);
    }

    private OtpPhoneNumber givenNumberWithLastDigit(int lastDigit) {
        OtpPhoneNumber number = mock(OtpPhoneNumber.class);
        given(number.getLastDigit()).willReturn(lastDigit);
        return number;
    }

}
