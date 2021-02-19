package uk.co.idv.method.entities.method;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public interface MockMethodsMother {

    static Methods withEmptyIneligibleMethodNames() {
        return withIneligibleMethodNames();
    }

    static Methods containingMethod(String name) {
        Methods methods = mock(DefaultMethods.class);
        given(methods.containsMethod(name)).willReturn(true);
        return methods;
    }

    static Methods withIneligibleMethodNames(String... names) {
        Methods methods = mock(DefaultMethods.class);
        given(methods.getIneligibleNames()).willReturn(Arrays.asList(names));
        return methods;
    }

    static Methods withAtLeastOneEligible() {
        Methods methods = mock(DefaultMethods.class);
        given(methods.allIneligible()).willReturn(false);
        return methods;
    }

    static Methods withAllIneligible() {
        Methods methods = mock(DefaultMethods.class);
        given(methods.allIneligible()).willReturn(true);
        return methods;
    }

    static Methods withContainsSuccessful(MethodVerifications verifications) {
        Methods methods = mock(DefaultMethods.class);
        given(methods.containsSuccessful(verifications)).willReturn(true);
        return methods;
    }

    static Methods withAllSuccessful(MethodVerifications verifications) {
        Methods methods = mock(DefaultMethods.class);
        given(methods.allSuccessful(verifications)).willReturn(true);
        return methods;
    }

    static Methods withAllComplete(MethodVerifications verifications) {
        Methods methods = mock(DefaultMethods.class);
        given(methods.allComplete(verifications)).willReturn(true);
        return methods;
    }

    static Methods withCompletedCount(MethodVerifications verifications, long count) {
        Methods methods = mock(DefaultMethods.class);
        given(methods.completedCount(verifications)).willReturn(count);
        return methods;
    }

    static Methods withNextIncompleteMethod(MethodVerifications verifications, Method method) {
        Methods methods = mock(DefaultMethods.class);
        given(methods.getNextIncompleteMethod(verifications)).willReturn(Optional.of(method));
        return methods;
    }

    static Methods withoutNextIncompleteMethod(MethodVerifications verifications) {
        Methods methods = mock(DefaultMethods.class);
        given(methods.getNextIncompleteMethod(verifications)).willReturn(Optional.empty());
        return methods;
    }

    static Methods withAllIncompleteMethods(MethodVerifications verifications, Method... incompleteMethods) {
        Methods methods = mock(DefaultMethods.class);
        given(methods.getAllIncompleteMethods(verifications)).willReturn(MethodsMother.with(incompleteMethods));
        return methods;
    }

    static Methods withoutIncompleteMethods(MethodVerifications verifications) {
        Methods methods = mock(DefaultMethods.class);
        given(methods.getAllIncompleteMethods(verifications)).willReturn(MethodsMother.empty());
        return methods;
    }

    static Methods withTotalDuration(Duration duration) {
        Methods methods = mock(DefaultMethods.class);
        given(methods.getTotalDuration()).willReturn(duration);
        return methods;
    }

    static Methods withLongestDuration(Duration duration) {
        Methods methods = mock(DefaultMethods.class);
        given(methods.getLongestDuration()).willReturn(duration);
        return methods;
    }

}
