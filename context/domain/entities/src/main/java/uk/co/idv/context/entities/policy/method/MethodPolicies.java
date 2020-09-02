package uk.co.idv.context.entities.policy.method;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.identity.RequestedData;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MethodPolicies {

    private final Collection<MethodPolicy> values;

    public MethodPolicies(MethodPolicy... values) {
        this(Arrays.asList(values));
    }

    public RequestedData getRequestedData() {
        return new RequestedData(values.stream()
                .flatMap(policy -> policy.getRequestedData().stream())
                .collect(Collectors.toList()));
    }

}
