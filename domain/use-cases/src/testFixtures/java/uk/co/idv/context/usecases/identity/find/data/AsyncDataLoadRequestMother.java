package uk.co.idv.context.usecases.identity.find.data;

import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.usecases.identity.find.data.DefaultAsyncDataLoadRequest.DefaultAsyncDataLoadRequestBuilder;

import java.time.Duration;

public interface AsyncDataLoadRequestMother {

    static AsyncDataLoadRequest withTimeout(Duration timeout) {
        return builder().timeout(timeout).build();
    }

    static AsyncDataLoadRequest withAliases(Aliases aliases) {
        return builder().aliases(aliases).build();
    }

    static AsyncDataLoadRequest build() {
        return builder().build();
    }

    static DefaultAsyncDataLoadRequestBuilder builder() {
        return DefaultAsyncDataLoadRequest.builder()
                .aliases(AliasesMother.creditCardNumberOnly())
                .timeout(Duration.ofSeconds(2));
    }

}
