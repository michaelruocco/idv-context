package uk.co.idv.method.entities.push;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.eligibility.Eligible;
import uk.co.idv.method.entities.method.MethodVerifications;
import uk.co.idv.method.entities.push.eligibility.NoMobileDevicesRegistered;

import java.util.Collection;
import java.util.Collections;

import static java.lang.Integer.toUnsignedLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PushNotificationTest {

    @Test
    void shouldReturnName() {
        PushNotification pushNotification = PushNotification.builder().build();

        assertThat(pushNotification.getName()).isEqualTo("push-notification");
    }

    @Test
    void shouldReturnMobileDeviceTokens() {
        Collection<String> tokens = givenMobileDeviceTokens();

        PushNotification pushNotification = PushNotification.builder()
                .mobileDeviceTokens(tokens)
                .build();

        assertThat(pushNotification.getMobileDeviceTokens()).isEqualTo(tokens);
    }

    @Test
    void shouldReturnEligibleIfMobileDevicesIsNotEmpty() {
        Collection<String> tokens = givenMobileDeviceTokens();

        PushNotification pushNotification = PushNotification.builder()
                .mobileDeviceTokens(tokens)
                .build();

        assertThat(pushNotification.getEligibility()).isEqualTo(new Eligible());
    }

    @Test
    void shouldReturnIneligibleIfMobileDevicesIsEmpty() {
        Collection<String> tokens = Collections.emptyList();

        PushNotification pushNotification = PushNotification.builder()
                .mobileDeviceTokens(tokens)
                .build();

        assertThat(pushNotification.getEligibility()).isEqualTo(new NoMobileDevicesRegistered());
    }

    @Test
    void shouldReturnSuccessful() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        PushNotification pushNotification = PushNotification.builder().build();
        given(verifications.containsSuccessful(pushNotification.getName())).willReturn(true);

        boolean successful = pushNotification.isSuccessful(verifications);

        assertThat(successful).isTrue();
    }

    @Test
    void shouldBeCompleteIfSuccessfulVerificationForMethod() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        PushNotification pushNotification = PushNotification.builder()
                .config(PushNotificationConfigMother.build())
                .build();
        given(verifications.containsSuccessful(pushNotification.getName())).willReturn(true);

        boolean complete = pushNotification.isComplete(verifications);

        assertThat(complete).isTrue();
    }

    @Test
    void shouldBeCompleteIfNoSuccessfulVerificationsForMethodAndNoAttemptsRemaining() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        PushNotificationConfig config = PushNotificationConfigMother.build();
        PushNotification pushNotification = PushNotification.builder()
                .config(config)
                .build();
        given(verifications.containsSuccessful(pushNotification.getName())).willReturn(false);
        given(verifications.countByName(pushNotification.getName())).willReturn(toUnsignedLong(config.getMaxNumberOfAttempts()));

        boolean complete = pushNotification.isComplete(verifications);

        assertThat(complete).isTrue();
    }

    @Test
    void shouldNotBeCompleteIfNoSuccessfulVerificationsForMethodAndAttemptsRemaining() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        PushNotificationConfig config = PushNotificationConfigMother.build();
        PushNotification pushNotification = PushNotification.builder()
                .config(config)
                .build();
        given(verifications.containsSuccessful(pushNotification.getName())).willReturn(false);
        given(verifications.countByName(pushNotification.getName())).willReturn(toUnsignedLong(config.getMaxNumberOfAttempts() - 1));

        boolean complete = pushNotification.isComplete(verifications);

        assertThat(complete).isFalse();
    }

    @Test
    void shouldReturnMethodConfig() {
        PushNotificationConfig config = mock(PushNotificationConfig.class);

        PushNotification pushNotification = PushNotification.builder()
                .config(config)
                .build();

        assertThat(pushNotification.getConfig()).isEqualTo(config);
    }

    private Collection<String> givenMobileDeviceTokens() {
        return Collections.singleton("my-token");
    }

}
