package uk.co.idv.context.entities.policy.method.otp.delivery;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.phonenumber.PhoneNumber;
import uk.co.idv.context.entities.phonenumber.PhoneNumberMother;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class LastUpdatedConfigTest {

    @Test
    void shouldBeValidIfUnknownAllowedAndPhoneHasNoLastUpdatedDate() {
        LastUpdatedConfig config = LastUpdatedConfigMother.unknownAllowed();
        PhoneNumber number = PhoneNumberMother.withoutLastUpdated();

        boolean valid = config.isValid(number, Instant.now());

        assertThat(valid).isTrue();
    }

    @Test
    void shouldNotBeValidIfUnknownNotAllowedAndPhoneHasNoLastUpdatedDate() {
        LastUpdatedConfig config = LastUpdatedConfigMother.unknownNotAllowed();
        PhoneNumber number = PhoneNumberMother.withoutLastUpdated();

        boolean valid = config.isValid(number, Instant.now());

        assertThat(valid).isFalse();
    }

    @Test
    void shouldNotBeValidIfPhoneHasLastUpdatedDateAfterCutoff() {
        Instant now = Instant.now();
        Duration cutoff = Duration.ofDays(5);
        LastUpdatedConfig config = LastUpdatedConfigMother.withCutoffDays(cutoff.toDays());
        PhoneNumber number = PhoneNumberMother.withLastUpdated(now.minus(cutoff).plusMillis(1));

        boolean valid = config.isValid(number, now);

        assertThat(valid).isFalse();
    }

    @Test
    void shouldNotBeValidIfPhoneHasLastUpdatedDateEqualToCutoff() {
        Instant now = Instant.now();
        Duration cutoff = Duration.ofDays(5);
        LastUpdatedConfig config = LastUpdatedConfigMother.withCutoffDays(cutoff.toDays());
        PhoneNumber number = PhoneNumberMother.withLastUpdated(now.minus(cutoff));

        boolean valid = config.isValid(number, now);

        assertThat(valid).isFalse();
    }

    @Test
    void shouldBeValidIfPhoneHasLastUpdatedDateBeforeCutoff() {
        Instant now = Instant.now();
        Duration cutoff = Duration.ofDays(5);
        LastUpdatedConfig config = LastUpdatedConfigMother.withCutoffDays(cutoff.toDays());
        PhoneNumber number = PhoneNumberMother.withLastUpdated(now.minus(cutoff).minusMillis(1));

        boolean valid = config.isValid(number, now);

        assertThat(valid).isTrue();
    }

    @Test
    void shouldBeValidIfPhoneHasLastUpdatedDateAndCutoffDaysNoConfigured() {
        Instant now = Instant.now();
        LastUpdatedConfig config = LastUpdatedConfigMother.withoutCutoffDays();
        PhoneNumber number = PhoneNumberMother.withLastUpdated(now);

        boolean valid = config.isValid(number, now);

        assertThat(valid).isTrue();
    }

}
