package uk.co.idv.context.entities.context.method.otp.delivery;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.AsyncFutureSimSwapEligibility;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DeliveryMethodTest {

    @Test
    void shouldReturnId() {
        UUID id = UUID.randomUUID();

        DeliveryMethod method = DeliveryMethod.builder()
                .id(id)
                .build();

        assertThat(method.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnType() {
        String type = "my-type";

        DeliveryMethod method = DeliveryMethod.builder()
                .type(type)
                .build();

        assertThat(method.getType()).isEqualTo(type);
    }

    @Test
    void shouldReturnValue() {
        String value = "my-value";

        DeliveryMethod method = DeliveryMethod.builder()
                .value(value)
                .build();

        assertThat(method.getValue()).isEqualTo(value);
    }

    @Test
    void shouldReturnEligibility() {
        Eligibility eligibility = mock(Eligibility.class);

        DeliveryMethod method = DeliveryMethod.builder()
                .eligibility(eligibility)
                .build();

        assertThat(method.getEligibility()).isEqualTo(eligibility);
    }

    @Test
    void shouldReturnIsEligibleFromEligibility() {
        Eligibility eligibility = mock(Eligibility.class);
        given(eligibility.isEligible()).willReturn(true);

        DeliveryMethod method = DeliveryMethod.builder()
                .eligibility(eligibility)
                .build();

        assertThat(method.isEligible()).isTrue();
    }

    @Test
    void shouldReturnEmptyOptionalIfEligibilityIsNotAsyncSimSwapEligibility() {
        Eligibility eligibility = mock(Eligibility.class);

        DeliveryMethod method = DeliveryMethod.builder()
                .eligibility(eligibility)
                .build();

        assertThat(method.getAsyncSimSwapEligibilityFuture()).isEmpty();
    }

    @Test
    void shouldReturnFutureFromAsyncSimSwapEligibilityIfEligibilityIsAsyncSimSwapEligibility() {
        AsyncFutureSimSwapEligibility eligibility = mock(AsyncFutureSimSwapEligibility.class);
        CompletableFuture<Eligibility> expectedFuture = givenFutureReturnedFrom(eligibility);

        DeliveryMethod method = DeliveryMethod.builder()
                .eligibility(eligibility)
                .build();

        assertThat(method.getAsyncSimSwapEligibilityFuture()).contains(expectedFuture);
    }

    @Test
    void shouldReturnLastUpdated() {
        Instant lastUpdated = Instant.now();

        DeliveryMethod method = DeliveryMethod.builder()
                .lastUpdated(lastUpdated)
                .build();

        assertThat(method.getLastUpdated()).contains(lastUpdated);
    }

    private CompletableFuture<Eligibility> givenFutureReturnedFrom(AsyncFutureSimSwapEligibility eligibility) {
        CompletableFuture<Eligibility> future = mock(CompletableFuture.class);
        given(eligibility.getFuture()).willReturn(future);
        return future;
    }

}
