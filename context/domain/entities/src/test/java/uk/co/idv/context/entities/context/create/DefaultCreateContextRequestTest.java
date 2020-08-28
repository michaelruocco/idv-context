package uk.co.idv.context.entities.context.create;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.activity.DefaultActivityMother;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
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
    void shouldReturnAliases() {
        Aliases aliases = AliasesMother.creditCardNumberOnly();

        CreateContextRequest request = DefaultCreateContextRequest.builder()
                .aliases(aliases)
                .build();

        assertThat(request.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldReturnAliasTypesFromAliases() {
        Aliases aliases = AliasesMother.creditCardNumberOnly();

        CreateContextRequest request = DefaultCreateContextRequest.builder()
                .aliases(aliases)
                .build();

        assertThat(request.getAliasTypes()).isEqualTo(aliases.getTypes());
    }

}
