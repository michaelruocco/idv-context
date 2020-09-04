package uk.co.idv.context.entities.context.create;

import uk.co.idv.context.entities.activity.DefaultActivityMother;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.channel.DefaultChannelMother;

public interface FacadeCreateContextRequestMother {

    static CreateContextRequest build() {
        return builder().build();
    }

    static FacadeCreateContextRequest.FacadeCreateContextRequestBuilder builder() {
        return FacadeCreateContextRequest.builder()
                .channel(DefaultChannelMother.build())
                .activity(DefaultActivityMother.build())
                .aliases(AliasesMother.creditCardNumberOnly());
    }

}
