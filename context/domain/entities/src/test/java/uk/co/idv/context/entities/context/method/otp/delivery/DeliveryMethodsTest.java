package uk.co.idv.context.entities.context.method.otp.delivery;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.eligibility.Eligible;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.EligibilityFutures;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.NoEligibleDeliveryMethodsAvailable;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class DeliveryMethodsTest {

    @Test
    void shouldBeIterable() {
        DeliveryMethod method1 = DeliveryMethodMother.build();
        DeliveryMethod method2 = DeliveryMethodMother.build();

        DeliveryMethods methods = new DeliveryMethods(method1, method2);

        assertThat(methods).containsExactly(method1, method2);
    }

    @Test
    void shouldReturnStream() {
        DeliveryMethod method1 = DeliveryMethodMother.build();
        DeliveryMethod method2 = DeliveryMethodMother.build();
        DeliveryMethods methods = new DeliveryMethods(method1, method2);

        Stream<DeliveryMethod> stream = methods.stream();

        assertThat(stream).containsExactly(method1, method2);
    }

    @Test
    void shouldReturnValues() {
        DeliveryMethod method1 = DeliveryMethodMother.build();
        DeliveryMethod method2 = DeliveryMethodMother.build();
        DeliveryMethods methods = new DeliveryMethods(method1, method2);

        Collection<DeliveryMethod> values = methods.getValues();

        assertThat(values).containsExactly(method1, method2);
    }

    @Test
    void shouldReturnAsyncEligibilityFutures() {
        CompletableFuture<Eligibility> future1 = CompletableFuture.completedFuture(mock(Eligibility.class));
        DeliveryMethod method1 = DeliveryMethodMother.withEligibilityFuture(future1);
        DeliveryMethod method2 = DeliveryMethodMother.build();
        CompletableFuture<Eligibility> future2 = CompletableFuture.completedFuture(mock(Eligibility.class));
        DeliveryMethod method3 = DeliveryMethodMother.withEligibilityFuture(future2);
        DeliveryMethods methods = new DeliveryMethods(method1, method2, method3);

        EligibilityFutures futures = methods.toSimSwapFutures();

        assertThat(futures).containsExactly(future1, future2);
    }

    @Test
    void shouldBeEligibleIfAtLeastOneDeliveryMethodIsEligible() {
        DeliveryMethod method1 = DeliveryMethodMother.ineligible();
        DeliveryMethod method2 = DeliveryMethodMother.eligible();
        DeliveryMethods methods = new DeliveryMethods(method1, method2);

        Eligibility eligibility = methods.getEligibility();

        assertThat(eligibility).isInstanceOf(Eligible.class);
    }

    @Test
    void shouldNotBeEligibleIfAllDeliveryMethodAreIneligible() {
        DeliveryMethod method1 = DeliveryMethodMother.ineligible();
        DeliveryMethod method2 = DeliveryMethodMother.ineligible();
        DeliveryMethods methods = new DeliveryMethods(method1, method2);

        Eligibility eligibility = methods.getEligibility();

        assertThat(eligibility).isInstanceOf(NoEligibleDeliveryMethodsAvailable.class);
    }

    @Test
    void shouldReplaceDeliveryMethodsWithMatchingIds() {
        DeliveryMethod method1 = DeliveryMethodMother.withId(UUID.fromString("2cd5e569-8b84-4606-90bf-debf0c245f06"));
        DeliveryMethod method2 = DeliveryMethodMother.withId(UUID.fromString("373fe399-b4a6-4e17-832d-0d7c606d4136"));
        DeliveryMethods methods = new DeliveryMethods(method1, method2);
        DeliveryMethod ignored = DeliveryMethodMother.withId(UUID.fromString("169a8855-328d-41cc-8642-5957fd38c124"));
        DeliveryMethod replacement = DeliveryMethodMother.withId(method2.getId());
        DeliveryMethods newValues = new DeliveryMethods(replacement, ignored);

        DeliveryMethods updated = methods.replace(newValues);

        assertThat(updated).containsExactlyInAnyOrder(method1, replacement);
    }

}
