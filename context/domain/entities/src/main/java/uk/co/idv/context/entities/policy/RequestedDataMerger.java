package uk.co.idv.context.entities.policy;

import uk.co.idv.identity.entities.identity.RequestedData;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class RequestedDataMerger {

    private RequestedDataMerger() {
        // utility class
    }

    public static RequestedData mergeRequestedData(RequestedDataProvider... providers) {
        return mergeRequestedData(Arrays.asList(providers));
    }

    public static RequestedData mergeRequestedData(Collection<? extends RequestedDataProvider> providers) {
        return new RequestedData(providers.stream()
                .flatMap(provider -> provider.getRequestedData().stream())
                .collect(Collectors.toList()));
    }

}
