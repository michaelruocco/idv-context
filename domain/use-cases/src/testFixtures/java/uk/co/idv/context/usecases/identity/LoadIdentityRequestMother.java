package uk.co.idv.context.usecases.identity;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.DefaultAliasMother;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.usecases.identity.DefaultFindIdentityRequest.DefaultFindIdentityRequestBuilder;

public interface LoadIdentityRequestMother {

    static FindIdentityRequest withProvidedAlias(Alias alias) {
        return builder().providedAlias(alias).build();
    }

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
                .providedAlias(DefaultAliasMother.build())
                .channel(DefaultChannelMother.build());
    }

}
