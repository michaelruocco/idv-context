package uk.co.idv.context.usecases.identity;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.DefaultAliasMother;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.usecases.identity.DefaultLoadIdentityRequest.DefaultLoadIdentityRequestBuilder;

public interface LoadIdentityRequestMother {

    static LoadIdentityRequest withProvidedAlias(Alias alias) {
        return builder().providedAlias(alias).build();
    }

    static LoadIdentityRequest withAliases(Aliases aliases) {
        return builder().aliases(aliases).build();
    }

    static LoadIdentityRequest withChannel(Channel channel) {
        return builder().channel(channel).build();
    }

    static LoadIdentityRequest build() {
        return builder().build();
    }

    static DefaultLoadIdentityRequestBuilder builder() {
        return DefaultLoadIdentityRequest.builder()
                .providedAlias(DefaultAliasMother.build())
                .channel(DefaultChannelMother.build());
    }

}
