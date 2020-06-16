package uk.co.idv.context.usecases.identity.request;

import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.update.DefaultUpdateIdentityRequest;
import uk.co.idv.context.usecases.identity.update.UpdateIdentityRequest;
import uk.co.idv.context.usecases.identity.update.DefaultUpdateIdentityRequest.DefaultUpdateIdentityRequestBuilder;

public interface UpdateIdentityRequestMother {

    static UpdateIdentityRequest withIdentity(Identity identity) {
        return builder().identity(identity).build();
    }

    static UpdateIdentityRequest withChannel(Channel channel) {
        return builder().channel(channel).build();
    }

    static UpdateIdentityRequest build() {
        return builder().build();
    }

    static DefaultUpdateIdentityRequestBuilder builder() {
        return DefaultUpdateIdentityRequest.builder()
                .channel(DefaultChannelMother.build())
                .identity(IdentityMother.example());
    }

}
