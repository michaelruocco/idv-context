package uk.co.idv.context.entities.context.create;

import org.junit.jupiter.api.Test;
import uk.co.idv.activity.entities.Activity;
import uk.co.idv.activity.entities.DefaultActivityMother;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.alias.DefaultAliases;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.channel.DefaultChannelMother;

import static org.assertj.core.api.Assertions.assertThat;

class FacadeCreateContextRequestTest {

    @Test
    void shouldReturnChannel() {
        Channel channel = DefaultChannelMother.build();

        CreateContextRequest request = FacadeCreateContextRequest.builder()
                .channel(channel)
                .build();

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnChannelIdFromChannel() {
        Channel channel = DefaultChannelMother.build();

        CreateContextRequest request = FacadeCreateContextRequest.builder()
                .channel(channel)
                .build();

        assertThat(request.getChannelId()).isEqualTo(channel.getId());
    }

    @Test
    void shouldReturnActivity() {
        Activity activity = DefaultActivityMother.build();

        CreateContextRequest request = FacadeCreateContextRequest.builder()
                .activity(activity)
                .build();

        assertThat(request.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldReturnActivityNameFromActivity() {
        Activity activity = DefaultActivityMother.build();

        CreateContextRequest request = FacadeCreateContextRequest.builder()
                .activity(activity)
                .build();

        assertThat(request.getActivityName()).isEqualTo(activity.getName());
    }

    @Test
    void shouldReturnAliases() {
        DefaultAliases aliases = AliasesMother.creditCardNumberOnly();

        CreateContextRequest request = FacadeCreateContextRequest.builder()
                .aliases(aliases)
                .build();

        assertThat(request.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldReturnAliasTypesFromAliases() {
        DefaultAliases aliases = AliasesMother.creditCardNumberOnly();

        CreateContextRequest request = FacadeCreateContextRequest.builder()
                .aliases(aliases)
                .build();

        assertThat(request.getAliasTypes()).isEqualTo(aliases.getTypes());
    }

}
