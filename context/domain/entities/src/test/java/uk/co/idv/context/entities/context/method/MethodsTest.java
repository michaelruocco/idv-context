package uk.co.idv.context.entities.context.method;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.context.method.otp.Otp;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.context.method.query.MethodQuery;

import java.time.Duration;
import java.util.Collection;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static uk.co.idv.context.entities.context.method.MockMethodMother.givenCompleteMethod;
import static uk.co.idv.context.entities.context.method.MockMethodMother.givenEligibleMethod;
import static uk.co.idv.context.entities.context.method.MockMethodMother.givenIncompleteMethod;
import static uk.co.idv.context.entities.context.method.MockMethodMother.givenIneligibleMethod;
import static uk.co.idv.context.entities.context.method.MockMethodMother.givenMethodWith;
import static uk.co.idv.context.entities.context.method.MockMethodMother.givenSuccessfulMethod;
import static uk.co.idv.context.entities.context.method.MockMethodMother.givenUnsuccessfulMethod;
import static uk.co.idv.context.entities.context.method.MockMethodMother.mockMethod;

class MethodsTest {

    @Test
    void shouldBeIterable() {
        Method method1 = mockMethod();
        Method method2 = mockMethod();

        Methods methods = new Methods(method1, method2);

        assertThat(methods).containsExactly(
                method1,
                method2
        );
    }

    @Test
    void shouldReturnValues() {
        Method method1 = mockMethod();
        Method method2 = mockMethod();

        Methods methods = new Methods(method1, method2);

        assertThat(methods.getValues()).containsExactly(
                method1,
                method2
        );
    }

    @Test
    void shouldReturnEligibleIfAllMethodsEligible() {
        Method method1 = givenEligibleMethod(Method.class);
        Method method2 = givenEligibleMethod(Method.class);

        Methods methods = new Methods(method1, method2);

        assertThat(methods.isEligible()).isTrue();
    }

    @Test
    void shouldReturnIneligibleIfAtLeastOnMethodIsNotEligible() {
        Method method1 = givenEligibleMethod(Method.class);
        Method method2 = givenIneligibleMethod(Method.class);

        Methods methods = new Methods(method1, method2);

        assertThat(methods.isEligible()).isFalse();
    }

    @Test
    void shouldReturnCompleteIfAllMethodsComplete() {
        Method method1 = givenCompleteMethod(Method.class);
        Method method2 = givenCompleteMethod(Method.class);

        Methods methods = new Methods(method1, method2);

        assertThat(methods.isComplete()).isTrue();
    }

    @Test
    void shouldReturnIncompleteIfAtLeastOnMethodIsNotComplete() {
        Method method1 = givenCompleteMethod(Method.class);
        Method method2 = givenIncompleteMethod();

        Methods methods = new Methods(method1, method2);

        assertThat(methods.isComplete()).isFalse();
    }

    @Test
    void shouldReturnSuccessfulIfAllMethodsSuccessful() {
        Method method1 = givenSuccessfulMethod();
        Method method2 = givenSuccessfulMethod();

        Methods methods = new Methods(method1, method2);

        assertThat(methods.isSuccessful()).isTrue();
    }

    @Test
    void shouldReturnUnsuccessfulIfAtLeastOnMethodIsNotComplete() {
        Method method1 = givenSuccessfulMethod();
        Method method2 = givenUnsuccessfulMethod();

        Methods methods = new Methods(method1, method2);

        assertThat(methods.isSuccessful()).isFalse();
    }

    @Test
    void shouldReturnSumOfMethodDurations() {
        Method method1 = givenMethodWith(Duration.ofMinutes(2));
        Method method2 = givenMethodWith(Duration.ofMinutes(3));

        Methods methods = new Methods(method1, method2);

        assertThat(methods.getDuration()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void shouldReplaceDeliveryMethodsOnAllOtpMethods() {
        Method method1 = mockMethod();
        Otp method2 = mock(Otp.class);
        Otp method3 = mock(Otp.class);
        DeliveryMethods deliveryMethods = mock(DeliveryMethods.class);
        Otp replaced2 = givenReplacedDeliveryMethodsOtp(method2, deliveryMethods);
        Otp replaced3 = givenReplacedDeliveryMethodsOtp(method3, deliveryMethods);
        Methods methods = new Methods(method1, method2, method3);

        Methods replaced = methods.replaceDeliveryMethods(deliveryMethods);

        assertThat(replaced).containsExactly(method1, replaced2, replaced3);
    }

    @Test
    void shouldReturnResultFromQuery() {
        Method method1 = mockMethod();
        Method method2 = mockMethod();
        Method expectedMethod = mockMethod();
        MethodQuery<Method> query = givenQueryReturningMethod(expectedMethod);
        Methods methods = new Methods(method1, method2);

        Collection<Method> method = methods.find(query);

        assertThat(method).contains(expectedMethod);
    }

    @Test
    void shouldPassMethodStreamToQuery() {
        Method method1 = mockMethod();
        Method method2 = mockMethod();
        MethodQuery<Method> query = givenQueryReturningMethod(method1);
        Methods methods = new Methods(method1, method2);

        methods.find(query);

        ArgumentCaptor<Stream<Method>> captor = ArgumentCaptor.forClass(Stream.class);
        verify(query).apply(captor.capture());
        assertThat(captor.getValue()).containsExactly(method1, method2);
    }

    private Otp givenReplacedDeliveryMethodsOtp(Otp otp, DeliveryMethods deliveryMethods) {
        Otp replaced = mockMethod(Otp.class);
        given(otp.replaceDeliveryMethods(deliveryMethods)).willReturn(replaced);
        return replaced;
    }

    static MethodQuery<Method> givenQueryReturningMethod(Method method) {
        MethodQuery<Method> query = mock(MethodQuery.class);
        given(query.apply(any(Stream.class))).willReturn(Stream.of(method));
        return query;
    }

}
