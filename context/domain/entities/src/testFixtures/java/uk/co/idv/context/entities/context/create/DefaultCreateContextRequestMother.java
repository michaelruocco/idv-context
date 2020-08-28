package uk.co.idv.context.entities.context.create;

import uk.co.idv.context.entities.activity.DefaultActivityMother;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.channel.DefaultChannelMother;

public interface DefaultCreateContextRequestMother {

    static CreateContextRequest build() {
        return builder().build();
    }

    static DefaultCreateContextRequest.DefaultCreateContextRequestBuilder builder() {
        return DefaultCreateContextRequest.builder()
                .channel(DefaultChannelMother.build())
                .activity(DefaultActivityMother.build())
                .aliases(AliasesMother.creditCardNumberOnly());
    }

}
