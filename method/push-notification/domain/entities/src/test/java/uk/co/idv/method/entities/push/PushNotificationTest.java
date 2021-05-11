package uk.co.idv.method.entities.push;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;
import uk.co.idv.method.entities.eligibility.Eligible;
import uk.co.idv.method.entities.method.MethodVerifications;
import uk.co.idv.method.entities.push.eligibility.NoMobileDevicesRegistered;

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
    void shouldReturnMobileDevices() {
        MobileDevices mobileDevices = givenMobileDevices();

        PushNotification pushNotification = PushNotification.builder()
                .mobileDevices(mobileDevices)
                .build();

        assertThat(pushNotification.getMobileDevices()).isEqualTo(mobileDevices);
    }

    @Test
    void shouldReturnEligibleIfMobileDevicesIsNotEmpty() {
        MobileDevices mobileDevices = givenMobileDevices();
        given(mobileDevices.isEmpty()).willReturn(false);

        PushNotification pushNotification = PushNotification.builder()
                .mobileDevices(mobileDevices)
                .build();

        assertThat(pushNotification.getEligibility()).isEqualTo(new Eligible());
    }

    @Test
    void shouldReturnIneligibleIfMobileDevicesIsEmpty() {
        MobileDevices mobileDevices = givenMobileDevices();
        given(mobileDevices.isEmpty()).willReturn(true);

        PushNotification pushNotification = PushNotification.builder()
                .mobileDevices(mobileDevices)
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

    private MobileDevices givenMobileDevices() {
        return mock(MobileDevices.class);
    }

}
