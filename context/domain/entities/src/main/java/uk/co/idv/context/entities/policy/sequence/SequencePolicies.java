package uk.co.idv.context.entities.policy.sequence;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.policy.RequestedDataMerger;
import uk.co.idv.context.entities.policy.RequestedDataProvider;
import uk.co.idv.identity.entities.identity.RequestedData;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Data
public class SequencePolicies implements RequestedDataProvider, Iterable<SequencePolicy> {

    @Getter(AccessLevel.NONE)
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

    @Override
    public Iterator<SequencePolicy> iterator() {
        return values.iterator();
    }

}
