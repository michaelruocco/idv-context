package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("provideLastDigitsAndExpectedStatusAndLastSwapped")
    void shouldReturnSimSwapResultForLastDigitOfNumber(int lastDigit, String status, Instant lastSwapped) {
        OtpPhoneNumber number = givenNumberWithLastDigit(lastDigit);

        SimSwapResult result = factory.build(number, config);

        assertThat(result)
                .hasFieldOrPropertyWithValue("status", status)
                .hasFieldOrPropertyWithValue("config", config)
                .hasFieldOrPropertyWithValue("lastSwapped", lastSwapped);
    }

    private OtpPhoneNumber givenNumberWithLastDigit(int lastDigit) {
        OtpPhoneNumber number = mock(OtpPhoneNumber.class);
        given(number.getLastDigit()).willReturn(lastDigit);
        return number;
    }

    private static Stream<Arguments> provideLastDigitsAndExpectedStatusAndLastSwapped() {
        final String success = "success";
        return Stream.of(
                Arguments.of(9, "failure", null),
                Arguments.of(8, "unknown", null),
                Arguments.of(7, "timeout", null),
                Arguments.of(6, success, NOW.minus(Duration.ofDays(5))),
                Arguments.of(5, success, null),
                Arguments.of(4, success, null),
                Arguments.of(3, success, null),
                Arguments.of(2, success, null),
                Arguments.of(1, success, null),
                Arguments.of(0, success, null)
        );
    }

}
