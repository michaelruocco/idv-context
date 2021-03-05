package uk.co.idv.identity.usecases.eligibility.external.data;

import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.alias.DefaultAliases;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.identity.entities.identity.RequestedDataMother;
import uk.co.idv.identity.usecases.eligibility.external.data.AsyncDataLoadRequest.AsyncDataLoadRequestBuilder;

import java.time.Duration;

public interface AsyncDataLoadRequestMother {

    static AsyncDataLoadRequest withTimeout(Duration timeout) {
        return builder().timeout(timeout).build();
    }

    static AsyncDataLoadRequest withAliases(DefaultAliases aliases) {
        return builder().aliases(aliases).build();
    }

    static AsyncDataLoadRequest withRequestedData(RequestedData requestedData) {
        return builder().requestedData(requestedData).build();
    }

    static AsyncDataLoadRequest build() {
        return builder().build();
    }

    static AsyncDataLoadRequestBuilder builder() {
        return AsyncDataLoadRequest.builder()
                .aliases(AliasesMother.creditCardNumberOnly())
                .timeout(Duration.ofSeconds(2))
                .requestedData(RequestedDataMother.allRequested());
    }

}
