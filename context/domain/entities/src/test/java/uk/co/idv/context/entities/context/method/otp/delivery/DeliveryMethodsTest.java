package uk.co.idv.context.entities.context.method.otp.delivery;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.eligibility.Eligible;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.EligibilityFutures;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.NoEligibleDeliveryMethodsAvailable;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DeliveryMethodsTest {

    @Test
    void shouldBeIterable() {
        DeliveryMethod method1 = givenDeliveryMethod();
        DeliveryMethod method2 = givenDeliveryMethod();

        DeliveryMethods methods = new DeliveryMethods(method1, method2);

        assertThat(methods).containsExactly(method1, method2);
    }

    @Test
    void shouldReturnStream() {
        DeliveryMethod method1 = givenDeliveryMethod();
        DeliveryMethod method2 = givenDeliveryMethod();
        DeliveryMethods methods = new DeliveryMethods(method1, method2);

        Stream<DeliveryMethod> stream = methods.stream();

        assertThat(stream).containsExactly(method1, method2);
    }

    @Test
    void shouldReturnValues() {
        DeliveryMethod method1 = givenDeliveryMethod();
        DeliveryMethod method2 = givenDeliveryMethod();
        DeliveryMethods methods = new DeliveryMethods(method1, method2);

        Collection<DeliveryMethod> values = methods.getValues();

        assertThat(values).containsExactly(method1, method2);
    }

    @Test
    void shouldReturnAsyncEligibilityFutures() {
        CompletableFuture<Eligibility> future1 = CompletableFuture.completedFuture(mock(Eligibility.class));
        DeliveryMethod method1 = givenMethodWithEligibilityFuture(future1);
        DeliveryMethod method2 = givenDeliveryMethod();
        CompletableFuture<Eligibility> future2 = CompletableFuture.completedFuture(mock(Eligibility.class));
        DeliveryMethod method3 = givenMethodWithEligibilityFuture(future2);
        DeliveryMethods methods = new DeliveryMethods(method1, method2, method3);

        EligibilityFutures futures = methods.toFutures();

        assertThat(futures).containsExactly(future1, future2);
    }

    @Test
    void shouldBeEligibleIfAtLeastOneDeliveryMethodIsEligible() {
        DeliveryMethod method1 = givenDeliveryMethod();
        DeliveryMethod method2 = givenEligibleDeliveryMethod();
        DeliveryMethods methods = new DeliveryMethods(method1, method2);

        Eligibility eligibility = methods.getEligibility();

        assertThat(eligibility).isInstanceOf(Eligible.class);
    }

    @Test
    void shouldNotBeEligibleIfAllDeliveryMethodAreIneligible() {
        DeliveryMethod method1 = givenIneligibleDeliveryMethod();
        DeliveryMethod method2 = givenIneligibleDeliveryMethod();
        DeliveryMethods methods = new DeliveryMethods(method1, method2);

        Eligibility eligibility = methods.getEligibility();

        assertThat(eligibility).isInstanceOf(NoEligibleDeliveryMethodsAvailable.class);
    }

    private DeliveryMethod givenMethodWithEligibilityFuture(CompletableFuture<Eligibility> future) {
        DeliveryMethod method = mock(DeliveryMethod.class);
        given(method.getAsyncSimSwapEligibilityFuture()).willReturn(Optional.of(future));
        return method;
    }

    private DeliveryMethod givenEligibleDeliveryMethod() {
        DeliveryMethod method = givenDeliveryMethod();
        given(method.isEligible()).willReturn(true);
        return method;
    }

    private DeliveryMethod givenIneligibleDeliveryMethod() {
        DeliveryMethod method = givenDeliveryMethod();
        given(method.isEligible()).willReturn(false);
        return method;
    }

    private DeliveryMethod givenDeliveryMethod() {
        return mock(DeliveryMethod.class);
    }

}
