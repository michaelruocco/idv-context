package uk.co.idv.context.usecases.context.method.otp;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.usecases.async.FutureWaiter;
import uk.co.idv.context.entities.context.method.MethodsRequest;
import uk.co.idv.context.entities.context.method.MethodsRequestMother;
import uk.co.idv.context.entities.context.method.otp.Otp;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.EligibilityFutures;
import uk.co.idv.context.entities.policy.method.MethodPolicy;
import uk.co.idv.context.entities.policy.method.otp.OtpConfig;
import uk.co.idv.context.entities.policy.method.otp.OtpPolicy;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfigs;
import uk.co.idv.context.usecases.context.method.otp.delivery.DeliveryMethodConfigsConverter;

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
        MethodsRequest request = MethodsRequestMother.build();
        MethodPolicy policy = mock(MethodPolicy.class);

        Throwable error = catchThrowable(() -> builder.build(request, policy));

        assertThat(error).isInstanceOf(ClassCastException.class);
    }

    @Test
    void shouldReturnOtpMethodWithNamePopulatedFromPolicy() {
        MethodsRequest request = MethodsRequestMother.build();
        DeliveryMethods deliveryMethods = mock(DeliveryMethods.class);
        OtpPolicy policy = givenOtpPolicyThatWillReturnDeliveryMethods(request, deliveryMethods);
        String expectedName = "one-time-passcode";
        given(policy.getName()).willReturn(expectedName);

        Otp otp = builder.build(request, policy);

        assertThat(otp.getName()).isEqualTo(expectedName);
    }

    @Test
    void shouldReturnOtpMethodWithDeliveryMethodsPopulated() {
        MethodsRequest request = MethodsRequestMother.build();
        DeliveryMethods expectedDeliveryMethods = mock(DeliveryMethods.class);
        OtpPolicy policy = givenOtpPolicyThatWillReturnDeliveryMethods(request, expectedDeliveryMethods);

        Otp otp = builder.build(request, policy);

        assertThat(otp.getDeliveryMethods()).isEqualTo(expectedDeliveryMethods);
    }

    @Test
    void shouldReturnOtpMethodWithMethodConfigPopulated() {
        MethodsRequest request = MethodsRequestMother.build();
        DeliveryMethods deliveryMethods = mock(DeliveryMethods.class);
        OtpPolicy policy = givenOtpPolicyThatWillReturnDeliveryMethods(request, deliveryMethods);
        OtpConfig expectedMethodConfig = mock(OtpConfig.class);
        given(policy.getMethodConfig()).willReturn(expectedMethodConfig);

        Otp otp = builder.build(request, policy);

        assertThat(otp.getConfig()).isEqualTo(expectedMethodConfig);
    }

    @Test
    void shouldNotWaitForAsyncSimSwapsIfTimeoutNotPresentAndEmptyFuturesReturnedFromDeliveryMethods() {
        MethodsRequest request = MethodsRequestMother.build();
        DeliveryMethods deliveryMethods = mock(DeliveryMethods.class);
        OtpPolicy policy = givenOtpPolicyThatWillReturnDeliveryMethods(request, deliveryMethods);

        builder.build(request, policy);

        verify(futureWaiter, never()).waitFor(any(CompletableFuture.class), any(Duration.class));
    }

    @Test
    void shouldNotWaitForAsyncSimSwapsIfTimeoutPresentButEmptyFuturesReturnedFromDeliveryMethods() {
        DeliveryMethodConfigs configs = givenDeliveryMethodConfigsWithLongestSimSwapTimeout(Duration.ofMillis(1));
        OtpPolicy policy = givenPolicyWithDeliveryMethodConfigs(configs);
        MethodsRequest request = MethodsRequestMother.build();
        DeliveryMethods deliveryMethods = mock(DeliveryMethods.class);
        given(configsConverter.toDeliveryMethods(request.getIdentity(), configs)).willReturn(deliveryMethods);
        EligibilityFutures futures = givenEligibilityFutures(deliveryMethods);
        given(futures.isEmpty()).willReturn(true);

        builder.build(request, policy);

        verify(futureWaiter, never()).waitFor(any(CompletableFuture.class), any(Duration.class));
    }

    @Test
    void shouldWaitForAsyncSimSwapsIfTimeoutPresentAndFuturesReturnedFromDeliveryMethods() {
        Duration expectedTimeout = Duration.ofMillis(1);
        DeliveryMethodConfigs configs = givenDeliveryMethodConfigsWithLongestSimSwapTimeout(expectedTimeout);
        OtpPolicy policy = givenPolicyWithDeliveryMethodConfigs(configs);
        MethodsRequest request = MethodsRequestMother.build();
        DeliveryMethods deliveryMethods = mock(DeliveryMethods.class);
        given(configsConverter.toDeliveryMethods(request.getIdentity(), configs)).willReturn(deliveryMethods);
        EligibilityFutures futures = givenEligibilityFutures(deliveryMethods);
        given(futures.isEmpty()).willReturn(false);
        CompletableFuture<Void> expectedFuture = mock(CompletableFuture.class);
        given(futures.all()).willReturn(expectedFuture);

        builder.build(request, policy);

        verify(futureWaiter).waitFor(expectedFuture, expectedTimeout);
    }

    private OtpPolicy givenOtpPolicyThatWillReturnDeliveryMethods(MethodsRequest request, DeliveryMethods deliveryMethods) {
        DeliveryMethodConfigs configs = mock(DeliveryMethodConfigs.class);
        OtpPolicy policy = givenPolicyWithDeliveryMethodConfigs(configs);
        given(configsConverter.toDeliveryMethods(request.getIdentity(), configs)).willReturn(deliveryMethods);
        return policy;
    }

    private OtpPolicy givenPolicyWithDeliveryMethodConfigs(DeliveryMethodConfigs configs) {
        OtpPolicy policy = mock(OtpPolicy.class);
        given(policy.getDeliveryMethodConfigs()).willReturn(configs);
        return policy;
    }

    private DeliveryMethodConfigs givenDeliveryMethodConfigsWithLongestSimSwapTimeout(Duration timeout) {
        DeliveryMethodConfigs configs = mock(DeliveryMethodConfigs.class);
        given(configs.getLongestSimSwapConfigTimeout()).willReturn(Optional.of(timeout));
        return configs;
    }

    private EligibilityFutures givenEligibilityFutures(DeliveryMethods deliveryMethods) {
        EligibilityFutures futures = mock(EligibilityFutures.class);
        given(deliveryMethods.toFutures()).willReturn(futures);
        return futures;
    }

}
