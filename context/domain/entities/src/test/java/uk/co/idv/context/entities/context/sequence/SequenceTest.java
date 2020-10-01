package uk.co.idv.context.entities.context.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.context.method.query.MethodQuery;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SequenceTest {

    @Test
    void shouldReturnName() {
        String name = "my-name";

        Sequence sequence = Sequence.builder()
                .name(name)
                .build();

        assertThat(sequence.getName()).isEqualTo(name);
    }

    @Test
    void shouldReturnMethods() {
        Methods methods = mock(Methods.class);

        Sequence sequence = Sequence.builder()
                .methods(methods)
                .build();

        assertThat(sequence.getMethods()).isEqualTo(methods);
    }

    @Test
    void shouldReturnIncompleteEligibleOtpFromMethodsIfPresent() {
        MethodQuery<Method> query = mock(MethodQuery.class);
        Method expectedMethod = mock(Method.class);
        Methods methods = givenMethodsWillReturnMethodForQuery(query, expectedMethod);
        Sequence sequence = Sequence.builder()
                .methods(methods)
                .build();

        Collection<Method> method = sequence.find(query);

        assertThat(method).contains(expectedMethod);
    }

    @Test
    void shouldReturnEligibleFromMethods() {
        Methods methods = mock(Methods.class);
        given(methods.isEligible()).willReturn(true);

        Sequence sequence = Sequence.builder()
                .methods(methods)
                .build();

        assertThat(sequence.isEligible()).isTrue();
    }

    @Test
    void shouldReturnCompleteFromMethods() {
        Methods methods = mock(Methods.class);
        given(methods.isComplete()).willReturn(true);

        Sequence sequence = Sequence.builder()
                .methods(methods)
                .build();

        assertThat(sequence.isComplete()).isTrue();
    }

    @Test
    void shouldReturnSuccessfulFromMethods() {
        Methods methods = mock(Methods.class);
        given(methods.isSuccessful()).willReturn(true);

        Sequence sequence = Sequence.builder()
                .methods(methods)
                .build();

        assertThat(sequence.isSuccessful()).isTrue();
    }

    @Test
    void shouldReturnDurationFromMethods() {
        Methods methods = mock(Methods.class);
        Duration duration = Duration.ofMinutes(5);
        given(methods.getDuration()).willReturn(duration);

        Sequence sequence = Sequence.builder()
                .methods(methods)
                .build();

        assertThat(sequence.getDuration()).isEqualTo(duration);
    }

    @Test
    void shouldReplaceDeliveryMethodsOnMethods() {
        Methods methods = mock(Methods.class);
        DeliveryMethods deliveryMethods = mock(DeliveryMethods.class);
        Methods expectedMethods = givenReplacedDeliveryMethodsMethods(methods, deliveryMethods);
        Sequence sequence = SequenceMother.withMethods(methods);

        Sequence updated = sequence.replaceOtpDeliveryMethods(deliveryMethods);

        assertThat(updated).usingRecursiveComparison()
                .ignoringFields("methods")
                .isEqualTo(sequence);
        assertThat(updated.getMethods()).isEqualTo(expectedMethods);
    }

    private Methods givenReplacedDeliveryMethodsMethods(Methods methods, DeliveryMethods deliveryMethods) {
        Methods replaced = mock(Methods.class);
        given(methods.replaceDeliveryMethods(deliveryMethods)).willReturn(replaced);
        return replaced;
    }

    private Methods givenMethodsWillReturnMethodForQuery(MethodQuery<Method> query, Method method) {
        Methods methods = mock(Methods.class);
        given(methods.find(query)).willReturn(Collections.singleton(method));
        return methods;
    }

}
