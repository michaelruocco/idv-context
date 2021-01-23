package uk.co.idv.context.entities.context.sequence.nextmethods;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.method.MockMethodsMother;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;
import uk.co.idv.method.entities.method.MockMethodMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class AnyOrderNextMethodsPolicyTest {

    private final NextMethodsPolicy policy = new AnyOrderNextMethodsPolicy();

    @Test
    void shouldReturnAllIncompleteMethodsFromMethods() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Method incomplete1 = MockMethodMother.incomplete(verifications);
        Method incomplete2 = MockMethodMother.incomplete(verifications);
        Methods methods = MockMethodsMother.withAllIncompleteMethods(verifications, incomplete1, incomplete2);

        Methods nextMethods = policy.calculateNextMethods(methods, verifications);

        assertThat(nextMethods).containsExactly(incomplete1, incomplete2);
    }

    @Test
    void shouldReturnEmptyNextIncompleteMethodsIfMethodsDoesNotHaveAnyIncompleteMethods() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Methods methods = MockMethodsMother.withoutIncompleteMethods(verifications);

        Methods nextMethods = policy.calculateNextMethods(methods, verifications);

        assertThat(nextMethods).isEmpty();
    }

}
