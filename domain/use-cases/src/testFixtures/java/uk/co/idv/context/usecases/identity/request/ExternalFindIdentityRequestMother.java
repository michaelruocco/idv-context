package uk.co.idv.context.usecases.identity.request;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.usecases.identity.find.external.ExternalFindIdentityRequest;
import uk.co.idv.context.usecases.identity.find.external.ExternalFindIdentityRequest.ExternalFindIdentityRequestBuilder;

public interface ExternalFindIdentityRequestMother {

    static ExternalFindIdentityRequest withAliases(Alias... aliases) {
        return builder().aliases(AliasesMother.with(aliases)).build();
    }

    static ExternalFindIdentityRequest withAliases(Aliases aliases) {
        return builder().aliases(aliases).build();
    }

    static ExternalFindIdentityRequest withChannel(Channel channel) {
        return builder().channel(channel).build();
    }

    static ExternalFindIdentityRequest build() {
        return builder().build();
    }

    static ExternalFindIdentityRequestBuilder builder() {
        return ExternalFindIdentityRequest.builder()
                .aliases(AliasesMother.creditCardNumberOnly())
                .channel(DefaultChannelMother.build());
    }

}
