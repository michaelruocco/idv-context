package uk.co.idv.context.usecases.context.method.otp;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.usecases.async.FutureWaiter;
import uk.co.idv.context.entities.context.method.otp.Otp;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.EligibilityFutures;
import uk.co.idv.context.entities.policy.method.MethodPolicy;
import uk.co.idv.context.entities.policy.method.otp.OtpPolicy;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfigs;
import uk.co.idv.context.usecases.context.method.otp.delivery.DeliveryMethodConfigsConverter;
import uk.co.idv.identity.entities.identity.Identity;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class OtpBuilderTest {

    private final DeliveryMethodConfigsConverter configsConverter = mock(DeliveryMethodConfigsConverter.class);
    private final FutureWaiter futureWaiter = mock(FutureWaiter.class);

    private final OtpBuilder builder = OtpBuilder.builder()
            .configsConverter(configsConverter)
            .futureWaiter(futureWaiter)
            .build();

    @Test
    void shouldSupportOtpPolicy() {
        MethodPolicy policy = mock(OtpPolicy.class);

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
        Identity identity = mock(Identity.class);
        MethodPolicy policy = mock(MethodPolicy.class);

        Throwable error = catchThrowable(() -> builder.build(identity, policy));

        assertThat(error).isInstanceOf(ClassCastException.class);
    }

    @Test
    void shouldReturnOtpMethodWithNamePopulatedFromPolicy() {
        Identity identity = mock(Identity.class);
        DeliveryMethods deliveryMethods = mock(DeliveryMethods.class);
        OtpPolicy policy = givenOtpPolicyThatWillReturnDeliveryMethods(identity, deliveryMethods);
        String expectedName = "one-time-passcode";
        given(policy.getName()).willReturn(expectedName);

        Otp otp = builder.build(identity, policy);

        assertThat(otp.getName()).isEqualTo(expectedName);
    }

    @Test
    void shouldReturnOtpMethodWithDeliveryMethodsPopulated() {
        Identity identity = mock(Identity.class);
        DeliveryMethods expectedDeliveryMethods = mock(DeliveryMethods.class);
        OtpPolicy policy = givenOtpPolicyThatWillReturnDeliveryMethods(identity, expectedDeliveryMethods);

        Otp otp = builder.build(identity, policy);

        assertThat(otp.getDeliveryMethods()).isEqualTo(expectedDeliveryMethods);
    }

    @Test
    void shouldNotWaitForAsyncSimSwapsIfTimeoutNotPresentAndEmptyFuturesReturnedFromDeliveryMethods() {
        Identity identity = mock(Identity.class);
        DeliveryMethods deliveryMethods = mock(DeliveryMethods.class);
        OtpPolicy policy = givenOtpPolicyThatWillReturnDeliveryMethods(identity, deliveryMethods);

        builder.build(identity, policy);

        verify(futureWaiter, never()).waitFor(any(CompletableFuture.class), any(Duration.class));
    }

    @Test
    void shouldNotWaitForAsyncSimSwapsIfTimeoutPresentButEmptyFuturesReturnedFromDeliveryMethods() {
        DeliveryMethodConfigs configs = givenDeliveryMethodConfigsWithShortestSimSwapTimeout(Duration.ofMillis(1));
        OtpPolicy policy = givenPolicyWithDeliveryMethodConfigs(configs);
        Identity identity = mock(Identity.class);
        DeliveryMethods deliveryMethods = mock(DeliveryMethods.class);
        given(configsConverter.toDeliveryMethods(identity, configs)).willReturn(deliveryMethods);
        EligibilityFutures futures = givenEligibilityFutures(deliveryMethods);
        given(futures.isEmpty()).willReturn(true);

        builder.build(identity, policy);

        verify(futureWaiter, never()).waitFor(any(CompletableFuture.class), any(Duration.class));
    }

    @Test
    void shouldWaitForAsyncSimSwapsIfTimeoutPresentAndFuturesReturnedFromDeliveryMethods() {
        Duration expectedTimeout = Duration.ofMillis(1);
        DeliveryMethodConfigs configs = givenDeliveryMethodConfigsWithShortestSimSwapTimeout(expectedTimeout);
        OtpPolicy policy = givenPolicyWithDeliveryMethodConfigs(configs);
        Identity identity = mock(Identity.class);
        DeliveryMethods deliveryMethods = mock(DeliveryMethods.class);
        given(configsConverter.toDeliveryMethods(identity, configs)).willReturn(deliveryMethods);
        EligibilityFutures futures = givenEligibilityFutures(deliveryMethods);
        given(futures.isEmpty()).willReturn(false);
        CompletableFuture<Void> expectedFuture = mock(CompletableFuture.class);
        given(futures.all()).willReturn(expectedFuture);

        builder.build(identity, policy);

        verify(futureWaiter).waitFor(expectedFuture, expectedTimeout);
    }

    private OtpPolicy givenOtpPolicyThatWillReturnDeliveryMethods(Identity identity, DeliveryMethods deliveryMethods) {
        DeliveryMethodConfigs configs = mock(DeliveryMethodConfigs.class);
        OtpPolicy policy = givenPolicyWithDeliveryMethodConfigs(configs);
        given(configsConverter.toDeliveryMethods(identity, configs)).willReturn(deliveryMethods);
        return policy;
    }

    private OtpPolicy givenPolicyWithDeliveryMethodConfigs(DeliveryMethodConfigs configs) {
        OtpPolicy policy = mock(OtpPolicy.class);
        given(policy.getDeliveryMethodConfigs()).willReturn(configs);
        return policy;
    }

    private DeliveryMethodConfigs givenDeliveryMethodConfigsWithShortestSimSwapTimeout(Duration timeout) {
        DeliveryMethodConfigs configs = mock(DeliveryMethodConfigs.class);
        given(configs.getShortestSimSwapConfigTimeout()).willReturn(Optional.of(timeout));
        return configs;
    }

    private EligibilityFutures givenEligibilityFutures(DeliveryMethods deliveryMethods) {
        EligibilityFutures futures = mock(EligibilityFutures.class);
        given(deliveryMethods.toFutures()).willReturn(futures);
        return futures;
    }


}
