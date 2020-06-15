package uk.co.idv.context.usecases.identity.request;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.usecases.identity.service.find.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.request.DefaultFindIdentityRequest.DefaultFindIdentityRequestBuilder;

public interface FindIdentityRequestMother {

    static FindIdentityRequest withAliases(Alias... aliases) {
        return builder().aliases(AliasesMother.with(aliases)).build();
    }

    static FindIdentityRequest withAliases(Aliases aliases) {
        return builder().aliases(aliases).build();
    }

    static FindIdentityRequest withChannelId(String channelId) {
        return builder().channelId(channelId).build();
    }

    static FindIdentityRequest build() {
        return builder().build();
    }

    static DefaultFindIdentityRequestBuilder builder() {
        return DefaultFindIdentityRequest.builder()
                .aliases(AliasesMother.creditCardNumberOnly())
                .channelId(DefaultChannelMother.build().getId());
    }

}
