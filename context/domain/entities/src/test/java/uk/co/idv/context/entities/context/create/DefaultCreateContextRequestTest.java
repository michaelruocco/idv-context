package uk.co.idv.context.entities.context.create;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.activity.DefaultActivityMother;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.DefaultAliasMother;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultCreateContextRequestTest {

    @Test
    void shouldReturnChannel() {
        Channel channel = DefaultChannelMother.build();

        CreateContextRequest request = DefaultCreateContextRequest.builder()
                .channel(channel)
                .build();

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnChannelIdFromChannel() {
        Channel channel = DefaultChannelMother.build();

        CreateContextRequest request = DefaultCreateContextRequest.builder()
                .channel(channel)
                .build();

        assertThat(request.getChannelId()).isEqualTo(channel.getId());
    }

    @Test
    void shouldReturnActivity() {
        Activity activity = DefaultActivityMother.build();

        CreateContextRequest request = DefaultCreateContextRequest.builder()
                .activity(activity)
                .build();

        assertThat(request.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldReturnActivityNameFromActivity() {
        Activity activity = DefaultActivityMother.build();

        CreateContextRequest request = DefaultCreateContextRequest.builder()
                .activity(activity)
                .build();

        assertThat(request.getActivityName()).isEqualTo(activity.getName());
    }

    @Test
    void shouldReturnAlias() {
        Alias alias = DefaultAliasMother.build();

        CreateContextRequest request = DefaultCreateContextRequest.builder()
                .alias(alias)
                .build();

        assertThat(request.getAlias()).isEqualTo(alias);
    }

    @Test
    void shouldReturnAliasTypeFromAlias() {
        Alias alias = DefaultAliasMother.build();

        CreateContextRequest request = DefaultCreateContextRequest.builder()
                .alias(alias)
                .build();

        assertThat(request.getAliasType()).isEqualTo(alias.getType());
    }

}
