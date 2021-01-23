package uk.co.idv.context.entities.context.sequence.nextmethods;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.method.MockMethodsMother;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class InOrderNextMethodsPolicyTest {

    private final NextMethodsPolicy policy = new InOrderNextMethodsPolicy();

    @Test
    void shouldReturnNextIncompleteMethodsFromMethods() {
        Method expectedMethod = FakeMethodMother.build();
        MethodVerifications verifications = mock(MethodVerifications.class);
        Methods methods = MockMethodsMother.withNextIncompleteMethod(verifications, expectedMethod);

        Methods nextMethods = policy.calculateNextMethods(methods, verifications);

        assertThat(nextMethods).containsExactly(expectedMethod);
    }

    @Test
    void shouldReturnEmptyNextIncompleteMethodsIfMethodsDoesNotHaveNextIncompleteMethod() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Methods methods = MockMethodsMother.withoutNextIncompleteMethod(verifications);

        Methods nextMethods = policy.calculateNextMethods(methods, verifications);

        assertThat(nextMethods).isEmpty();
    }

}
