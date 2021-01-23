package uk.co.idv.context.entities.context.method;

import java.util.Arrays;

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

}
