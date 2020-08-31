package uk.co.idv.context.entities.policy.method.otp.delivery;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LastUpdatedConfigTest {

    @Test
    void shouldReturnAllowUnknown() {
        LastUpdatedConfig config = LastUpdatedConfigMother.unknownAllowed();

        boolean allowUnknown = config.isAllowUnknown();

        assertThat(allowUnknown).isTrue();
    }

    @Test
    void shouldReturnEmptyMinDaysSinceUpdateIfNotConfigured() {
        LastUpdatedConfig config = LastUpdatedConfigMother.withoutMinDaysSinceUpdate();

        Optional<Long> cutoffDays = config.getMinDaysSinceUpdate();

        assertThat(cutoffDays).isEmpty();
    }

    @Test
    void shouldReturnMinDaysSinceUpdateIfConfigured() {
        LastUpdatedConfig config = LastUpdatedConfigMother.withMinDaysSinceUpdate(5);

        Optional<Long> cutoffDays = config.getMinDaysSinceUpdate();

        assertThat(cutoffDays).contains(5L);
    }

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
        LastUpdatedConfig config = LastUpdatedConfigMother.withMinDaysSinceUpdate(cutoff.toDays());
        Updatable number = UpdatableMother.withLastUpdated(now.minus(cutoff).plusMillis(1));

        boolean valid = config.isValid(number, now);

        assertThat(valid).isFalse();
    }

    @Test
    void shouldNotBeValidIfPhoneHasLastUpdatedDateEqualToCutoff() {
        Instant now = Instant.now();
        Duration cutoff = Duration.ofDays(5);
        LastUpdatedConfig config = LastUpdatedConfigMother.withMinDaysSinceUpdate(cutoff.toDays());
        Updatable number = UpdatableMother.withLastUpdated(now.minus(cutoff));

        boolean valid = config.isValid(number, now);

        assertThat(valid).isFalse();
    }

    @Test
    void shouldBeValidIfPhoneHasLastUpdatedDateBeforeCutoff() {
        Instant now = Instant.now();
        Duration cutoff = Duration.ofDays(5);
        LastUpdatedConfig config = LastUpdatedConfigMother.withMinDaysSinceUpdate(cutoff.toDays());
        Updatable number = UpdatableMother.withLastUpdated(now.minus(cutoff).minusMillis(1));

        boolean valid = config.isValid(number, now);

        assertThat(valid).isTrue();
    }

    @Test
    void shouldBeValidIfPhoneHasLastUpdatedDateAndMinDaysSinceUpdateNotConfigured() {
        Instant now = Instant.now();
        LastUpdatedConfig config = LastUpdatedConfigMother.withoutMinDaysSinceUpdate();
        Updatable number = UpdatableMother.withLastUpdated(now);

        boolean valid = config.isValid(number, now);

        assertThat(valid).isTrue();
    }

}
