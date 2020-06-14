package uk.co.idv.context.usecases.identity;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.usecases.identity.find.data.TimeoutProvider;

@RequiredArgsConstructor
public class UpsertIdentityRequestConverter {

    private final TimeoutProvider config;

    public FindIdentityRequest toFindRequest(UpsertIdentityRequest request) {
        return DefaultFindIdentityRequest.builder()
                .aliases(request.getAliases())
                .channel(request.getChannel())
                .build();
    }

}
