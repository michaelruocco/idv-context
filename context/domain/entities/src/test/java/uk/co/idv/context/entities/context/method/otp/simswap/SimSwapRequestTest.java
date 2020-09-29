package uk.co.idv.context.entities.context.method.otp.simswap;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.MethodsRequest;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.EligibilityFutures;
import uk.co.idv.context.entities.policy.method.otp.OtpPolicy;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SimSwapRequestTest {

    @Test
    void shouldReturnMethodsRequest() {
        MethodsRequest methodsRequest = mock(MethodsRequest.class);

        SimSwapRequest request = SimSwapRequest.builder()
                .methodsRequest(methodsRequest)
                .build();

        assertThat(request.getMethodsRequest()).isEqualTo(methodsRequest);
    }

    @Test
    void shouldReturnOtpPolicy() {
        OtpPolicy policy = mock(OtpPolicy.class);

        SimSwapRequest request = SimSwapRequest.builder()
                .policy(policy)
                .build();

        assertThat(request.getPolicy()).isEqualTo(policy);
    }

    @Test
    void shouldReturnDeliveryMethods() {
        DeliveryMethods deliveryMethods = mock(DeliveryMethods.class);

        SimSwapRequest request = SimSwapRequest.builder()
                .deliveryMethods(deliveryMethods)
                .build();

        assertThat(request.getDeliveryMethods()).isEqualTo(deliveryMethods);
    }

    @Test
    void shouldReturnContextIdFromMethodsRequest() {
        MethodsRequest methodsRequest = mock(MethodsRequest.class);
        UUID id = UUID.randomUUID();
        given(methodsRequest.getContextId()).willReturn(id);

        SimSwapRequest request = SimSwapRequest.builder()
                .methodsRequest(methodsRequest)
                .build();

        assertThat(request.getContextId()).isEqualTo(id);
    }

    @Test
    void shouldReturnHasAsyncSimSwapFromOtpPolicy() {
        OtpPolicy policy = mock(OtpPolicy.class);
        given(policy.hasAsyncSimSwap()).willReturn(true);

        SimSwapRequest request = SimSwapRequest.builder()
                .policy(policy)
                .build();

        assertThat(request.hasAsyncSimSwap()).isTrue();
    }

    @Test
    void shouldReturnAllSimSwapFuturesFromDeliveryMethods() {
        CompletableFuture<Void> expectedFuture = mock(CompletableFuture.class);
        DeliveryMethods deliveryMethods = mock(DeliveryMethods.class);
        EligibilityFutures simSwapFutures = givenSimSwapFutures(deliveryMethods);
        given(simSwapFutures.all()).willReturn(expectedFuture);

        SimSwapRequest request = SimSwapRequest.builder()
                .deliveryMethods(deliveryMethods)
                .build();

        assertThat(request.getAllSimSwapFutures()).isEqualTo(expectedFuture);
    }

    @Test
    void shouldReturnAllFuturesDoneFromDeliveryMethodSimSwapFutures() {
        DeliveryMethods deliveryMethods = givenDeliveryMethodsWithAllFuturesDone();

        SimSwapRequest request = SimSwapRequest.builder()
                .deliveryMethods(deliveryMethods)
                .build();

        assertThat(request.allSimSwapFuturesDone()).isTrue();
    }

    @Test
    void shouldReturnLongestSimSwapConfigTimeoutFromPolicyIfConfigured() {
        Duration expectedTimeout = Duration.ofSeconds(2);
        OtpPolicy policy = mock(OtpPolicy.class);
        given(policy.getLongestSimSwapConfigTimeout()).willReturn(Optional.of(expectedTimeout));
        SimSwapRequest request = SimSwapRequest.builder()
                .policy(policy)
                .build();

        Duration timeout = request.getLongestSimSwapConfigTimeout();

        assertThat(timeout).isEqualTo(expectedTimeout);
    }

    @Test
    void shouldReturnDefaultTimeoutIfNotConfigured() {
        SimSwapRequest request = SimSwapRequest.builder()
                .policy(mock(OtpPolicy.class))
                .build();

        Duration timeout = request.getLongestSimSwapConfigTimeout();

        assertThat(timeout).isEqualTo(Duration.ofSeconds(5));
    }

    private EligibilityFutures givenSimSwapFutures(DeliveryMethods deliveryMethods) {
        EligibilityFutures futures = mock(EligibilityFutures.class);
        given(deliveryMethods.toSimSwapFutures()).willReturn(futures);
        return futures;
    }

    private DeliveryMethods givenDeliveryMethodsWithAllFuturesDone() {
        DeliveryMethods deliveryMethods = mock(DeliveryMethods.class);
        EligibilityFutures futures = givenSimSwapFutures(deliveryMethods);
        given(futures.all()).willReturn(CompletableFuture.allOf());
        return deliveryMethods;
    }

}
