package uk.co.idv.context.usecases.eligibility.external.data;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.FindIdentityRequest;

@RequiredArgsConstructor
public class FindIdentityRequestConverter {

    private final TimeoutProvider timeoutProvider;

    public AsyncDataLoadRequest toAsyncDataLoadRequest(Aliases aliases, FindIdentityRequest request) {
        return AsyncDataLoadRequest.builder()
                .timeout(timeoutProvider.getTimeout(request.getChannelId()))
                .aliases(aliases)
                .build();
    }

}
