package uk.co.idv.context.entities.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.activity.entities.Activity;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.method.entities.verification.Verifications;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ApiContextTest {

    @Test
    void shouldReturnId() {
        UUID id = UUID.randomUUID();

        ApiContext context = ApiContext.builder()
                .id(id)
                .build();

        assertThat(context.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnCreated() {
        Instant created = Instant.now();

        ApiContext context = ApiContext.builder()
                .created(created)
                .build();

        assertThat(context.getCreated()).isEqualTo(created);
    }

    @Test
    void shouldReturnExpiry() {
        Instant expiry = Instant.now();

        ApiContext context = ApiContext.builder()
                .expiry(expiry)
                .build();

        assertThat(context.getExpiry()).isEqualTo(expiry);
    }

    @Test
    void shouldReturnChannel() {
        Channel channel = mock(Channel.class);

        ApiContext context = ApiContext.builder()
                .channel(channel)
                .build();

        assertThat(context.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnAliases() {
        Aliases aliases = mock(Aliases.class);

        ApiContext context = ApiContext.builder()
                .aliases(aliases)
                .build();

        assertThat(context.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldReturnActivity() {
        Activity activity = mock(Activity.class);

        ApiContext context = ApiContext.builder()
                .activity(activity)
                .build();

        assertThat(context.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldReturnSequences() {
        Sequences sequences = mock(Sequences.class);

        ApiContext context = ApiContext.builder()
                .sequences(sequences)
                .build();

        assertThat(context.getSequences()).isEqualTo(sequences);
    }

    @Test
    void shouldReturnVerifications() {
        Verifications verifications = mock(Verifications.class);

        ApiContext context = ApiContext.builder()
                .verifications(verifications)
                .build();

        assertThat(context.getVerifications()).isEqualTo(verifications);
    }

    @Test
    void shouldReturnIsEligibleFromSequence() {
        Sequences sequences = mock(Sequences.class);
        given(sequences.isEligible()).willReturn(true);

        ApiContext context = ApiContext.builder()
                .sequences(sequences)
                .build();

        assertThat(context.isEligible()).isTrue();
    }

    @Test
    void shouldReturnIsCompleteFromSequence() {
        Sequences sequences = mock(Sequences.class);
        Verifications verifications = mock(Verifications.class);
        given(sequences.isComplete(verifications)).willReturn(true);

        ApiContext context = ApiContext.builder()
                .sequences(sequences)
                .verifications(verifications)
                .build();

        assertThat(context.isComplete()).isTrue();
    }

    @Test
    void shouldReturnIsSuccessfulFromSequence() {
        Sequences sequences = mock(Sequences.class);
        Verifications verifications = mock(Verifications.class);
        given(sequences.isSuccessful(verifications)).willReturn(true);

        ApiContext context = ApiContext.builder()
                .sequences(sequences)
                .verifications(verifications)
                .build();

        assertThat(context.isSuccessful()).isTrue();
    }

}
