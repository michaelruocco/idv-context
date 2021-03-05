package uk.co.idv.context.usecases.context;

import uk.co.idv.context.entities.context.ApiContext;
import uk.co.idv.context.entities.context.Context;

public class ContextConverter {

    public ApiContext toApiContext(Context context) {
        return ApiContext.builder()
                .id(context.getId())
                .created(context.getCreated())
                .expiry(context.getExpiry())
                .channel(context.getChannel())
                .activity(context.getActivity())
                .aliases(context.getAliases())
                .sequences(context.getSequences())
                .verifications(context.getVerifications())
                .build();
    }
}
