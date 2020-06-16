package uk.co.idv.context.usecases.identity;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.usecases.identity.find.external.ExternalFindIdentityRequest;
import uk.co.idv.context.usecases.identity.update.UpdateIdentityRequest;

@RequiredArgsConstructor
public class UpdateIdentityRequestConverter {

    public ExternalFindIdentityRequest toFindRequest(UpdateIdentityRequest request) {
        return ExternalFindIdentityRequest.builder()
                .aliases(request.getAliases())
                .channel(request.getChannel())
                .build();
    }

}
