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
        Otp expectedOtp = givenEligibleAndCompleteMethod(Otp.class);
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
        Method method1 = givenEligibleAndCompleteMethod(Method.class);
        Method method2 = mock(Method.class);
        Methods methods = new Methods(method1, method2);

        Optional<Otp> otp = methods.findNextIncompleteEligibleOtp();

        assertThat(otp).isEmpty();
    }

    private <T extends Method> T givenEligibleAndCompleteMethod(Class<T> type) {
        T method = mock(type);
        given(method.isComplete()).willReturn(false);
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

}
