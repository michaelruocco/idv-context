package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
    void shouldPopulateConfigOnResult() {
        OtpPhoneNumber number = givenNumberWithLastDigit(1);

        SimSwapResult result = factory.build(number, config);

        assertThat(result.getConfig()).isEqualTo(config);
    }

    @Test
    void shouldPopulateLastSwappedDateIfLastDigitIs6() {
        OtpPhoneNumber number = givenNumberWithLastDigit(6);

        SimSwapResult result = factory.build(number, config);

        assertThat(result.getLastSwapped()).contains(NOW.minus(Duration.ofDays(5)));
    }

    @ParameterizedTest(name = "should return empty last swapped date for last digit {0}")
    @CsvSource({"9","8","7","5","4","3","2","1","0"})
    void shouldReturnEmptyLastSwappedDateIfLastDigitIsNot6(int lastDigit) {
        OtpPhoneNumber number = givenNumberWithLastDigit(lastDigit);

        SimSwapResult result = factory.build(number, config);

        assertThat(result.getLastSwapped()).isEmpty();
    }

    @ParameterizedTest(name = "should return status {1} for number with last digit {0}")
    @CsvSource({
            "9,failure",
            "8,unknown",
            "7,timeout",
            "6,success",
            "5,success",
            "4,success",
            "3,success",
            "2,success",
            "1,success",
            "0,success"
    })
    void shouldReturnSimSwapStatusForNumberWithLastDigit(int lastDigit, String expectedStatus) {
        OtpPhoneNumber number = givenNumberWithLastDigit(lastDigit);

        SimSwapResult result = factory.build(number, config);

        assertThat(result.getStatus()).isEqualTo(expectedStatus);
    }

    private OtpPhoneNumber givenNumberWithLastDigit(int lastDigit) {
        OtpPhoneNumber number = mock(OtpPhoneNumber.class);
        given(number.getLastDigit()).willReturn(lastDigit);
        return number;
    }

}
