package uk.co.idv.context.usecases.identity;

import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.DefaultUpsertIdentityRequest.DefaultUpsertIdentityRequestBuilder;

public interface UpsertIdentityRequestMother {

    static UpsertIdentityRequest withIdentity(Identity identity) {
        return builder().identity(identity).build();
    }

    static UpsertIdentityRequest withChannel(Channel channel) {
        return builder().channel(channel).build();
    }

    static UpsertIdentityRequest build() {
        return builder().build();
    }

    static DefaultUpsertIdentityRequestBuilder builder() {
        return DefaultUpsertIdentityRequest.builder()
                .channel(DefaultChannelMother.build())
                .identity(IdentityMother.example());
    }

}
