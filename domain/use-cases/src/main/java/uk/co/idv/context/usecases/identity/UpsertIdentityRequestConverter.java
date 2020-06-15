package uk.co.idv.context.usecases.identity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpsertIdentityRequestConverter {

    public FindIdentityRequest toFindRequest(UpdateIdentityRequest request) {
        return DefaultFindIdentityRequest.builder()
                .aliases(request.getAliases())
                .channel(request.getChannel())
                .build();
    }

}
