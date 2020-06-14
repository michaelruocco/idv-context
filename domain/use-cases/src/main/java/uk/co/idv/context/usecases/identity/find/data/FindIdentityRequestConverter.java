package uk.co.idv.context.usecases.identity.find.data;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;

@RequiredArgsConstructor
public class FindIdentityRequestConverter {

    private final TimeoutProvider timeoutProvider;

    public AsyncDataLoadRequest toAsyncDataLoadRequest(Aliases aliases, FindIdentityRequest request) {
        return DefaultAsyncDataLoadRequest.builder()
                .timeout(timeoutProvider.getTimeout(request.getChannelId()))
                .aliases(aliases)
                .build();
    }

}
