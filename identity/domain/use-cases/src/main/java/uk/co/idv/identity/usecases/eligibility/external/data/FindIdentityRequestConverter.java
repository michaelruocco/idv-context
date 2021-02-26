package uk.co.idv.identity.usecases.eligibility.external.data;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;

@RequiredArgsConstructor
public class FindIdentityRequestConverter {

    private final TimeoutProvider timeoutProvider;

    public AsyncDataLoadRequest toAsyncDataLoadRequest(Aliases aliases, FindIdentityRequest request) {
        return AsyncDataLoadRequest.builder()
                .timeout(timeoutProvider.getTimeout(request.getChannelId()))
                .aliases(aliases)
                .requestedData(request.getRequestedData())
                .build();
    }

}
