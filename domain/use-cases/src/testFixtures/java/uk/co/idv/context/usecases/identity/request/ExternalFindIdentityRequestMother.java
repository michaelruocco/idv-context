package uk.co.idv.context.usecases.identity.request;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.usecases.identity.find.external.DefaultExternalFindIdentityRequest;
import uk.co.idv.context.usecases.identity.find.external.DefaultExternalFindIdentityRequest.DefaultExternalFindIdentityRequestBuilder;
import uk.co.idv.context.usecases.identity.find.external.ExternalFindIdentityRequest;

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

    static DefaultExternalFindIdentityRequest build() {
        return builder().build();
    }

    static DefaultExternalFindIdentityRequestBuilder builder() {
        return DefaultExternalFindIdentityRequest.builder()
                .aliases(AliasesMother.creditCardNumberOnly())
                .channel(DefaultChannelMother.build());
    }

}
