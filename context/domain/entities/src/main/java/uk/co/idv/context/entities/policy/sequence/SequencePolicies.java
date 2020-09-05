package uk.co.idv.context.entities.policy.sequence;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.policy.RequestedDataMerger;
import uk.co.idv.context.entities.policy.RequestedDataProvider;
import uk.co.idv.identity.entities.identity.RequestedData;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class SequencePolicies implements RequestedDataProvider {

    private final Collection<SequencePolicy> values;

    public SequencePolicies(SequencePolicy... values) {
        this(Arrays.asList(values));
    }

    public RequestedData getRequestedData() {
        return RequestedDataMerger.mergeRequestedData(values);
    }

    public Stream<SequencePolicy> stream() {
        return values.stream();
    }

}
