package uk.co.idv.context.entities.context.create;

import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.activity.DefaultActivityMother;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.DefaultAliasMother;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;

public interface DefaultCreateContextRequestMother {

    static CreateContextRequest withChannel(Channel channel) {
        return builder().channel(channel).build();
    }

    static CreateContextRequest withAlias(Alias alias) {
        return builder().alias(alias).build();
    }

    static CreateContextRequest withActivity(Activity activity) {
        return builder().activity(activity).build();
    }

    static CreateContextRequest build() {
        return builder().build();
    }

    static DefaultCreateContextRequest.DefaultCreateContextRequestBuilder builder() {
        return DefaultCreateContextRequest.builder()
                .channel(DefaultChannelMother.build())
                .activity(DefaultActivityMother.build())
                .alias(DefaultAliasMother.build());
    }

}
