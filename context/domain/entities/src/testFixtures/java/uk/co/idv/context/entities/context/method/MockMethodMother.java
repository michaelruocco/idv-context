package uk.co.idv.context.entities.context.method;


import java.time.Duration;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public interface MockMethodMother {

    static Method mockMethod() {
        return mock(Method.class);
    }

    static <T extends Method> T mockMethod(Class<T> type) {
        return mock(type);
    }

    static Method givenEligibleMethod() {
        return givenEligibleMethod(Method.class);
    }

    static <T extends Method> T givenEligibleMethod(Class<T> type) {
        T method = mock(type);
        given(method.isEligible()).willReturn(true);
        return method;
    }

    static Method givenIneligibleMethod() {
        return givenIneligibleMethod(Method.class);
    }

    static <T extends Method> T givenIneligibleMethod(Class<T> type) {
        T method = mock(type);
        given(method.isEligible()).willReturn(false);
        return method;
    }

    static Method givenCompleteMethod() {
        return givenCompleteMethod(Method.class);
    }

    static <T extends Method> T givenCompleteMethod(Class<T> type) {
        T method = mock(type);
        given(method.isComplete()).willReturn(true);
        return method;
    }

    static Method givenIncompleteMethod() {
        Method method = mock(Method.class);
        given(method.isComplete()).willReturn(false);
        return method;
    }

    static Method givenSuccessfulMethod() {
        Method method = mock(Method.class);
        given(method.isSuccessful()).willReturn(true);
        return method;
    }

    static Method givenUnsuccessfulMethod() {
        Method method = mock(Method.class);
        given(method.isSuccessful()).willReturn(false);
        return method;
    }

    static Method givenMethodWith(Duration duration) {
        Method method = mock(Method.class);
        given(method.getDuration()).willReturn(duration);
        return method;
    }

}
