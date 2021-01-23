package uk.co.idv.context.entities.context.method;

import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public interface MockMethodsMother {

    static Methods withEmptyIneligibleMethodNames() {
        return withIneligibleMethodNames();
    }

    static Methods withIneligibleMethodNames(String... names) {
        Methods methods = mock(Methods.class);
        given(methods.getIneligibleNames()).willReturn(Arrays.asList(names));
        return methods;
    }

    static Methods withAllSuccessful(MethodVerifications verifications) {
        Methods methods = mock(Methods.class);
        given(methods.allSuccessful(verifications)).willReturn(true);
        return methods;
    }

    static Methods withAllComplete(MethodVerifications verifications) {
        Methods methods = mock(Methods.class);
        given(methods.allComplete(verifications)).willReturn(true);
        return methods;
    }

    static Methods withCompletedCount(MethodVerifications verifications, long count) {
        Methods methods = mock(Methods.class);
        given(methods.completedCount(verifications)).willReturn(count);
        return methods;
    }

    static Methods withNextIncompleteMethod(MethodVerifications verifications, Method method) {
        Methods methods = mock(Methods.class);
        given(methods.getNextIncompleteMethod(verifications)).willReturn(Optional.of(method));
        return methods;
    }

    static Methods withoutNextIncompleteMethod(MethodVerifications verifications) {
        Methods methods = mock(Methods.class);
        given(methods.getNextIncompleteMethod(verifications)).willReturn(Optional.empty());
        return methods;
    }

    static Methods withAllIncompleteMethods(MethodVerifications verifications, Method... incompleteMethods) {
        Methods methods = mock(Methods.class);
        given(methods.getAllIncompleteMethods(verifications)).willReturn(MethodsMother.with(incompleteMethods));
        return methods;
    }

    static Methods withoutIncompleteMethods(MethodVerifications verifications) {
        Methods methods = mock(Methods.class);
        given(methods.getAllIncompleteMethods(verifications)).willReturn(MethodsMother.empty());
        return methods;
    }

}
