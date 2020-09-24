package uk.co.idv.context.entities.context.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.otp.Otp;

import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.idv.context.entities.context.method.MockMethodMother.givenCompleteMethod;
import static uk.co.idv.context.entities.context.method.MockMethodMother.givenEligibleAndIncompleteMethod;
import static uk.co.idv.context.entities.context.method.MockMethodMother.givenEligibleMethod;
import static uk.co.idv.context.entities.context.method.MockMethodMother.givenIncompleteMethod;
import static uk.co.idv.context.entities.context.method.MockMethodMother.givenIneligibleMethod;
import static uk.co.idv.context.entities.context.method.MockMethodMother.givenMethodWith;
import static uk.co.idv.context.entities.context.method.MockMethodMother.givenSuccessfulMethod;
import static uk.co.idv.context.entities.context.method.MockMethodMother.givenUnsuccessfulMethod;
import static uk.co.idv.context.entities.context.method.MockMethodMother.mockMethod;

class MethodsTest {

    @Test
    void shouldReturnMethods() {
        Method method1 = mockMethod();
        Method method2 = mockMethod();

        Methods methods = new Methods(method1, method2);

        assertThat(methods).containsExactly(
                method1,
                method2
        );
    }

    @Test
    void shouldReturnOtpIfIncompleteAndEligible() {
        Method method1 = mockMethod();
        Otp expectedOtp = givenEligibleAndIncompleteMethod(Otp.class);
        Methods methods = new Methods(method1, expectedOtp);

        Optional<Otp> otp = methods.findNextIncompleteEligibleOtp();

        assertThat(otp).contains(expectedOtp);
    }

    @Test
    void shouldNotReturnOtpIfComplete() {
        Method method1 = mockMethod();
        Method method2 = givenCompleteMethod(Otp.class);
        Methods methods = new Methods(method1, method2);

        Optional<Otp> otp = methods.findNextIncompleteEligibleOtp();

        assertThat(otp).isEmpty();
    }

    @Test
    void shouldNotReturnOtpIfEligible() {
        Method method1 = mockMethod();
        Method method2 = givenIneligibleMethod(Otp.class);
        Methods methods = new Methods(method1, method2);

        Optional<Otp> otp = methods.findNextIncompleteEligibleOtp();

        assertThat(otp).isEmpty();
    }

    @Test
    void shouldNotReturnOtpIfNotPresent() {
        Method method1 = givenEligibleAndIncompleteMethod(Method.class);
        Method method2 = mockMethod();
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

    @Test
    void shouldReturnSumOfMethodDurations() {
        Method method1 = givenMethodWith(Duration.ofMinutes(2));
        Method method2 = givenMethodWith(Duration.ofMinutes(3));

        Methods methods = new Methods(method1, method2);

        assertThat(methods.getDuration()).isEqualTo(Duration.ofMinutes(5));
    }

}
