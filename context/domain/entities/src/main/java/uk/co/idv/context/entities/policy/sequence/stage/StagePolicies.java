package uk.co.idv.context.entities.policy.sequence.stage;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.method.entities.policy.RequestedDataMerger;
import uk.co.idv.method.entities.policy.RequestedDataProvider;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Data
public class StagePolicies implements RequestedDataProvider, Iterable<StagePolicy> {

    @Getter(AccessLevel.NONE)
    private final Collection<StagePolicy> values;

    public StagePolicies(StagePolicy... values) {
        this(Arrays.asList(values));
    }

    @Override
    public RequestedData getRequestedData() {
        return RequestedDataMerger.mergeRequestedData(values);
    }

    @Override
    public Iterator<StagePolicy> iterator() {
        return values.iterator();
    }

    public Stream<StagePolicy> stream() {
        return values.stream();
    }

}
