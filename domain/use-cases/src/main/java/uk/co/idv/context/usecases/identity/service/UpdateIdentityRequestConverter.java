package uk.co.idv.context.usecases.identity.service;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.usecases.identity.request.ExternalFindIdentityRequest;
import uk.co.idv.context.usecases.identity.service.update.UpdateIdentityRequest;

@RequiredArgsConstructor
public class UpdateIdentityRequestConverter {

    public ExternalFindIdentityRequest toFindRequest(UpdateIdentityRequest request) {
        return ExternalFindIdentityRequest.builder()
                .aliases(request.getAliases())
                .channel(request.getChannel())
                .build();
    }

}
