package uk.co.idv.context.entities.policy.method;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.method.entities.policy.MethodPolicy;
import uk.co.idv.method.entities.policy.RequestedDataMerger;
import uk.co.idv.method.entities.policy.RequestedDataProvider;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Data
public class MethodPolicies implements RequestedDataProvider, Iterable<MethodPolicy> {

    @Getter(AccessLevel.NONE)
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

    @Override
    public Iterator<MethodPolicy> iterator() {
        return values.iterator();
    }

}
