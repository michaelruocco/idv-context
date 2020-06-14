package uk.co.idv.context.usecases.identity;

import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.usecases.identity.DefaultFindIdentityRequest.DefaultFindIdentityRequestBuilder;

public interface FindIdentityRequestMother {

    static FindIdentityRequest withAliases(Aliases aliases) {
        return builder().aliases(aliases).build();
    }

    static FindIdentityRequest withChannel(Channel channel) {
        return builder().channel(channel).build();
    }

    static FindIdentityRequest build() {
        return builder().build();
    }

    static DefaultFindIdentityRequestBuilder builder() {
        return DefaultFindIdentityRequest.builder()
                .aliases(AliasesMother.creditCardNumberOnly())
                .channel(DefaultChannelMother.build());
    }

}
