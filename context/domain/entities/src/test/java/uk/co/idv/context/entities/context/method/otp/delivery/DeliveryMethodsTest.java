package uk.co.idv.context.entities.context.method.otp.delivery;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.EligibilityFutures;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DeliveryMethodsTest {

    @Test
    void shouldBeIterable() {
        DeliveryMethod method1 = mock(DeliveryMethod.class);
        DeliveryMethod method2 = mock(DeliveryMethod.class);

        DeliveryMethods methods = new DeliveryMethods(method1, method2);

        assertThat(methods).containsExactly(method1, method2);
    }

    @Test
    void shouldReturnStream() {
        DeliveryMethod method1 = mock(DeliveryMethod.class);
        DeliveryMethod method2 = mock(DeliveryMethod.class);
        DeliveryMethods methods = new DeliveryMethods(method1, method2);

        Stream<DeliveryMethod> stream = methods.stream();

        assertThat(stream).containsExactly(method1, method2);
    }

    @Test
    void shouldReturnAsyncEligibilityFutures() {
        CompletableFuture<Eligibility> future1 = CompletableFuture.completedFuture(mock(Eligibility.class));
        DeliveryMethod method1 = givenMethodWithEligibilityFuture(future1);
        DeliveryMethod method2 = mock(DeliveryMethod.class);
        CompletableFuture<Eligibility> future2 = CompletableFuture.completedFuture(mock(Eligibility.class));
        DeliveryMethod method3 = givenMethodWithEligibilityFuture(future2);
        DeliveryMethods methods = new DeliveryMethods(method1, method2, method3);

        EligibilityFutures futures = methods.toFutures();

        assertThat(futures).containsExactly(future1, future2);
    }

    private DeliveryMethod givenMethodWithEligibilityFuture(CompletableFuture<Eligibility> future) {
        DeliveryMethod method = mock(DeliveryMethod.class);
        given(method.getAsyncSimSwapEligibilityFuture()).willReturn(Optional.of(future));
        return method;
    }

}
