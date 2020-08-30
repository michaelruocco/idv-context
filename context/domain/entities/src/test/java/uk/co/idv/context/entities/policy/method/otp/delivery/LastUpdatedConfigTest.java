package uk.co.idv.context.entities.policy.method.otp.delivery;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class LastUpdatedConfigTest {

    @Test
    void shouldBeValidIfUnknownAllowedAndPhoneNumberHasNoLastUpdatedDate() {
        LastUpdatedConfig config = LastUpdatedConfigMother.unknownAllowed();
        Updatable number = UpdatableMother.withoutLastUpdated();

        boolean valid = config.isValid(number, Instant.now());

        assertThat(valid).isTrue();
    }

    @Test
    void shouldNotBeValidIfUnknownNotAllowedAndPhoneHasNoLastUpdatedDate() {
        LastUpdatedConfig config = LastUpdatedConfigMother.unknownNotAllowed();
        Updatable number = UpdatableMother.withoutLastUpdated();

        boolean valid = config.isValid(number, Instant.now());

        assertThat(valid).isFalse();
    }

    @Test
    void shouldNotBeValidIfPhoneHasLastUpdatedDateAfterCutoff() {
        Instant now = Instant.now();
        Duration cutoff = Duration.ofDays(5);
        LastUpdatedConfig config = LastUpdatedConfigMother.withCutoffDays(cutoff.toDays());
        Updatable number = UpdatableMother.withLastUpdated(now.minus(cutoff).plusMillis(1));

        boolean valid = config.isValid(number, now);

        assertThat(valid).isFalse();
    }

    @Test
    void shouldNotBeValidIfPhoneHasLastUpdatedDateEqualToCutoff() {
        Instant now = Instant.now();
        Duration cutoff = Duration.ofDays(5);
        LastUpdatedConfig config = LastUpdatedConfigMother.withCutoffDays(cutoff.toDays());
        Updatable number = UpdatableMother.withLastUpdated(now.minus(cutoff));

        boolean valid = config.isValid(number, now);

        assertThat(valid).isFalse();
    }

    @Test
    void shouldBeValidIfPhoneHasLastUpdatedDateBeforeCutoff() {
        Instant now = Instant.now();
        Duration cutoff = Duration.ofDays(5);
        LastUpdatedConfig config = LastUpdatedConfigMother.withCutoffDays(cutoff.toDays());
        Updatable number = UpdatableMother.withLastUpdated(now.minus(cutoff).minusMillis(1));

        boolean valid = config.isValid(number, now);

        assertThat(valid).isTrue();
    }

    @Test
    void shouldBeValidIfPhoneHasLastUpdatedDateAndCutoffDaysNoConfigured() {
        Instant now = Instant.now();
        LastUpdatedConfig config = LastUpdatedConfigMother.withoutCutoffDays();
        Updatable number = UpdatableMother.withLastUpdated(now);

        boolean valid = config.isValid(number, now);

        assertThat(valid).isTrue();
    }

}
