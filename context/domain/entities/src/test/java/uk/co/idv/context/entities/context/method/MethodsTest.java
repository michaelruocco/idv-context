package uk.co.idv.context.entities.context.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.otp.Otp;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MethodsTest {

    @Test
    void shouldReturnMethods() {
        Method method1 = mock(Method.class);
        Method method2 = mock(Method.class);

        Methods methods = new Methods(method1, method2);

        assertThat(methods).containsExactly(
                method1,
                method2
        );
    }

    @Test
    void shouldReturnOtpIfIncompleteAndEligible() {
        Method method1 = mock(Method.class);
        Otp expectedOtp = givenEligibleAndIncompleteMethod(Otp.class);
        Methods methods = new Methods(method1, expectedOtp);

        Optional<Otp> otp = methods.findNextIncompleteEligibleOtp();

        assertThat(otp).contains(expectedOtp);
    }

    @Test
    void shouldNotReturnOtpIfComplete() {
        Method method1 = mock(Method.class);
        Method method2 = givenCompleteMethod(Otp.class);
        Methods methods = new Methods(method1, method2);

        Optional<Otp> otp = methods.findNextIncompleteEligibleOtp();

        assertThat(otp).isEmpty();
    }

    @Test
    void shouldNotReturnOtpIfEligible() {
        Method method1 = mock(Method.class);
        Method method2 = givenIneligibleMethod(Otp.class);
        Methods methods = new Methods(method1, method2);

        Optional<Otp> otp = methods.findNextIncompleteEligibleOtp();

        assertThat(otp).isEmpty();
    }

    @Test
    void shouldNotReturnOtpIfNotPresent() {
        Method method1 = givenEligibleAndIncompleteMethod(Method.class);
        Method method2 = mock(Method.class);
        Methods methods = new Methods(method1, method2);

        Optional<Otp> otp = methods.findNextIncompleteEligibleOtp();

        assertThat(otp).isEmpty();
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

    private <T extends Method> T givenEligibleAndIncompleteMethod(Class<T> type) {
        T method = givenEligibleMethod(type);
        given(method.isComplete()).willReturn(false);
        return method;
    }

    private <T extends Method> T givenEligibleMethod(Class<T> type) {
        T method = mock(type);
        given(method.isEligible()).willReturn(true);
        return method;
    }

    private <T extends Method> T givenIneligibleMethod(Class<T> type) {
        T method = mock(type);
        given(method.isEligible()).willReturn(false);
        return method;
    }

    private <T extends Method> T givenCompleteMethod(Class<T> type) {
        T method = mock(type);
        given(method.isComplete()).willReturn(true);
        return method;
    }

    private Method givenIncompleteMethod() {
        Method method = mock(Method.class);
        given(method.isComplete()).willReturn(false);
        return method;
    }

    private Method givenSuccessfulMethod() {
        Method method = mock(Method.class);
        given(method.isSuccessful()).willReturn(true);
        return method;
    }

    private Method givenUnsuccessfulMethod() {
        Method method = mock(Method.class);
        given(method.isSuccessful()).willReturn(false);
        return method;
    }

}
