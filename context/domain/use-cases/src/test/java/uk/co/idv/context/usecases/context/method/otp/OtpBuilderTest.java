package uk.co.idv.context.usecases.context.method.otp;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.context.method.MethodsRequest;
import uk.co.idv.context.entities.context.method.MethodsRequestMother;
import uk.co.idv.context.usecases.context.method.otp.delivery.DeliveryMethodConfigsConverter;
import uk.co.idv.context.usecases.context.method.otp.simswap.SimSwap;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.OtpConfig;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.policy.OtpPolicy;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfigs;
import uk.co.idv.method.entities.otp.simswap.SimSwapRequest;
import uk.co.idv.method.entities.policy.MethodPolicy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class OtpBuilderTest {

    private final DeliveryMethodConfigsConverter configsConverter = mock(DeliveryMethodConfigsConverter.class);
    private final SimSwap simSwap = mock(SimSwap.class);

    private final OtpBuilder builder = OtpBuilder.builder()
            .configsConverter(configsConverter)
            .simSwap(simSwap)
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
        given(policy.getConfig()).willReturn(expectedMethodConfig);

        Otp otp = builder.build(request, policy);

        assertThat(otp.getConfig()).isEqualTo(expectedMethodConfig);
    }

    @Test
    void shouldPassContextIdToSimSwap() {
        MethodsRequest request = MethodsRequestMother.build();
        DeliveryMethods deliveryMethods = mock(DeliveryMethods.class);
        OtpPolicy policy = givenOtpPolicyThatWillReturnDeliveryMethods(request, deliveryMethods);

        builder.build(request, policy);

        ArgumentCaptor<SimSwapRequest> captor = ArgumentCaptor.forClass(SimSwapRequest.class);
        verify(simSwap).waitForSimSwapsIfRequired(captor.capture());
        SimSwapRequest simSwapRequest = captor.getValue();
        assertThat(simSwapRequest.getContextId()).isEqualTo(request.getContextId());
    }

    @Test
    void shouldPassDeliveryMethodsToSimSwap() {
        MethodsRequest methodRequest = MethodsRequestMother.build();
        DeliveryMethods deliveryMethods = mock(DeliveryMethods.class);
        OtpPolicy policy = givenOtpPolicyThatWillReturnDeliveryMethods(methodRequest, deliveryMethods);

        builder.build(methodRequest, policy);

        ArgumentCaptor<SimSwapRequest> captor = ArgumentCaptor.forClass(SimSwapRequest.class);
        verify(simSwap).waitForSimSwapsIfRequired(captor.capture());
        SimSwapRequest simSwapRequest = captor.getValue();
        assertThat(simSwapRequest.getDeliveryMethods()).isEqualTo(deliveryMethods);
    }

    @Test
    void shouldPassOtpPolicyToSimSwap() {
        MethodsRequest methodRequest = MethodsRequestMother.build();
        DeliveryMethods deliveryMethods = mock(DeliveryMethods.class);
        OtpPolicy policy = givenOtpPolicyThatWillReturnDeliveryMethods(methodRequest, deliveryMethods);

        builder.build(methodRequest, policy);

        ArgumentCaptor<SimSwapRequest> captor = ArgumentCaptor.forClass(SimSwapRequest.class);
        verify(simSwap).waitForSimSwapsIfRequired(captor.capture());
        SimSwapRequest simSwapRequest = captor.getValue();
        assertThat(simSwapRequest.getPolicy()).isEqualTo(policy);
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

}
