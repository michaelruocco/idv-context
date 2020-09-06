package uk.co.idv.context.entities.policy.method.otp.delivery;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.eligibility.Eligible;
import uk.co.idv.context.entities.policy.method.otp.delivery.eligibility.UpdatedAfterCutoff;
import uk.co.idv.context.entities.policy.method.otp.delivery.eligibility.UnknownLastUpdatedNotAllowed;

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
    void shouldReturnEligibleIfUnknownAllowedAndPhoneNumberHasNoLastUpdatedDate() {
        LastUpdatedConfig config = LastUpdatedConfigMother.unknownAllowed();
        Updatable number = UpdatableMother.withoutLastUpdated();

        Eligibility eligibility = config.toEligibility(number, Instant.now());

        assertThat(eligibility).isInstanceOf(Eligible.class);
    }

    @Test
    void shouldReturnUnknownLastUpdatedNotAllowedIfUnknownNotAllowedAndPhoneHasNoLastUpdatedDate() {
        LastUpdatedConfig config = LastUpdatedConfigMother.unknownNotAllowed();
        Updatable number = UpdatableMother.withoutLastUpdated();

        Eligibility eligibility = config.toEligibility(number, Instant.now());

        assertThat(eligibility).isInstanceOf(UnknownLastUpdatedNotAllowed.class);
    }

    @Test
    void shouldReturnUpdatedAfterCutoffIfPhoneHasLastUpdatedDateAfterCutoff() {
        Instant now = Instant.parse("2020-09-01T06:13:15.266Z");
        Duration cutoff = Duration.ofDays(5);
        LastUpdatedConfig config = LastUpdatedConfigMother.withMinDaysSinceUpdate(cutoff.toDays());
        Updatable number = UpdatableMother.withLastUpdated(now.minus(cutoff).plusMillis(1));

        Eligibility eligibility = config.toEligibility(number, now);

        assertThat(eligibility).isInstanceOf(UpdatedAfterCutoff.class);
        assertThat(eligibility.getReason()).contains("updated at 2020-08-27T06:13:15.267Z (cutoff 2020-08-27T06:13:15.266Z)");
    }

    @Test
    void shouldReturnEligibleIfPhoneHasLastUpdatedDateEqualToCutoff() {
        Instant now = Instant.parse("2020-09-01T06:13:15.266Z");
        Duration cutoff = Duration.ofDays(5);
        LastUpdatedConfig config = LastUpdatedConfigMother.withMinDaysSinceUpdate(cutoff.toDays());
        Updatable number = UpdatableMother.withLastUpdated(now.minus(cutoff));

        Eligibility eligibility = config.toEligibility(number, now);

        assertThat(eligibility).isInstanceOf(Eligible.class);
    }

    @Test
    void shouldReturnEligibleIfPhoneHasLastUpdatedDateBeforeCutoff() {
        Instant now = Instant.parse("2020-09-01T06:13:15.266Z");
        Duration cutoff = Duration.ofDays(5);
        LastUpdatedConfig config = LastUpdatedConfigMother.withMinDaysSinceUpdate(cutoff.toDays());
        Updatable number = UpdatableMother.withLastUpdated(now.minus(cutoff).minusMillis(1));

        Eligibility eligibility = config.toEligibility(number, now);

        assertThat(eligibility).isInstanceOf(Eligible.class);
    }

    @Test
    void shouldReturnEligibleIfPhoneHasLastUpdatedDateAndMinDaysSinceUpdateNotConfigured() {
        Instant now = Instant.now();
        LastUpdatedConfig config = LastUpdatedConfigMother.withoutMinDaysSinceUpdate();
        Updatable number = UpdatableMother.withLastUpdated(now);

        Eligibility eligibility = config.toEligibility(number, now);

        assertThat(eligibility).isInstanceOf(Eligible.class);
    }

}
