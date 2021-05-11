package uk.co.idv.method.usecases.push;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.method.MethodsRequest;
import uk.co.idv.method.entities.method.MethodsRequestMother;
import uk.co.idv.method.entities.policy.MethodPolicy;
import uk.co.idv.method.entities.push.PushNotification;
import uk.co.idv.method.entities.push.PushNotificationConfig;
import uk.co.idv.method.entities.push.policy.PushNotificationPolicy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PushNotificationBuilderTest {

    private final PushNotificationBuilder builder = new PushNotificationBuilder();

    @Test
    void shouldSupportPushNotificationPolicy() {
        PushNotificationPolicy policy = mock(PushNotificationPolicy.class);

        boolean supports = builder.supports(policy);

        assertThat(supports).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherMethodPolicy() {
        MethodPolicy policy = mock(MethodPolicy.class);

        boolean supports = builder.supports(policy);

        assertThat(supports).isFalse();
    }

    @Test
    void shouldThrowExceptionIfAttemptToBuildMethodWithUnsupportedMethodPolicy() {
        MethodsRequest request = MethodsRequestMother.build();
        MethodPolicy policy = mock(MethodPolicy.class);

        Throwable error = catchThrowable(() -> builder.build(request, policy));

        assertThat(error).isInstanceOf(ClassCastException.class);
    }

    @Test
    void shouldReturnPushNotificationMethodWithMobileDevicesPopulated() {
        MethodsRequest request = MethodsRequestMother.build();
        PushNotificationPolicy policy = mock(PushNotificationPolicy.class);

        PushNotification pushNotification = builder.build(request, policy);

        assertThat(pushNotification.getMobileDevices()).isEqualTo(request.getMobileDevices());
    }

    @Test
    void shouldReturnPushNotificationMethodWithMethodConfigPopulated() {
        MethodsRequest request = MethodsRequestMother.build();
        PushNotificationPolicy policy = mock(PushNotificationPolicy.class);
        PushNotificationConfig expectedMethodConfig = mock(PushNotificationConfig.class);
        given(policy.getConfig()).willReturn(expectedMethodConfig);

        PushNotification pushNotification = builder.build(request, policy);

        assertThat(pushNotification.getConfig()).isEqualTo(expectedMethodConfig);
    }

}
