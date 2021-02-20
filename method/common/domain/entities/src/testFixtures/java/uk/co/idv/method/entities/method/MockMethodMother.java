package uk.co.idv.method.entities.method;

import java.util.function.UnaryOperator;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public interface MockMethodMother {

    static Method successful(MethodVerifications verifications) {
        Method method = mock(Method.class);
        given(method.isSuccessful(verifications)).willReturn(true);
        return method;
    }

    static Method unsuccessful(MethodVerifications verifications) {
        Method method = mock(Method.class);
        given(method.isSuccessful(verifications)).willReturn(false);
        return method;
    }

    static Method complete(MethodVerifications verifications) {
        Method method = mock(Method.class);
        given(method.isComplete(verifications)).willReturn(true);
        return method;
    }

    static Method incomplete(MethodVerifications verifications) {
        Method method = mock(Method.class);
        given(method.isComplete(verifications)).willReturn(false);
        return method;
    }

    static Method withUpdatedMethod(UnaryOperator<Method> function, Method method) {
        Method updated = mock(Method.class);
        given(function.apply(method)).willReturn(updated);
        return updated;
    }

}
