package uk.co.idv.context.entities.policy.method;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.policy.RequestedDataMerger;
import uk.co.idv.context.entities.policy.RequestedDataProvider;
import uk.co.idv.identity.entities.identity.RequestedData;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class MethodPolicies implements RequestedDataProvider {

    private final Collection<MethodPolicy> values;

    public MethodPolicies(MethodPolicy... values) {
        this(Arrays.asList(values));
    }

    public RequestedData getRequestedData() {
        return RequestedDataMerger.mergeRequestedData(values);
    }

    public Stream<MethodPolicy> stream() {
        return values.stream();
    }

}
