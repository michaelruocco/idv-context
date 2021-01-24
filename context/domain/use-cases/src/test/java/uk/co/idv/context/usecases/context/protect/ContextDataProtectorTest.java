package uk.co.idv.context.usecases.context.protect;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.context.entities.context.method.MethodsMother;
import uk.co.idv.context.entities.context.sequence.Sequence;
import uk.co.idv.context.entities.context.sequence.SequenceMother;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.context.entities.context.sequence.SequencesMother;
import uk.co.idv.context.entities.context.sequence.stage.StagesMother;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.method.entities.method.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ContextDataProtectorTest {

    private final Method protectedMethod = mock(Method.class);
    private final Identity protectedIdentity = mock(Identity.class);
    private final Channel protectedChannel = mock(Channel.class);

    private final ContextDataProtector protector = ContextDataProtector.builder()
            .channelProtector(channel -> protectedChannel)
            .identityProtector(identity -> protectedIdentity)
            .methodProtector(method -> protectedMethod)
            .build();

    @Test
    void shouldReturnNonProtectedFieldsUnchanged() {
        Context context = ContextMother.build();

        Context protectedContext = protector.apply(context);

        assertThat(protectedContext)
                .usingRecursiveComparison()
                .ignoringFields(
                        "request.initial.channel",
                        "request.identity",
                        "sequences")
                .isEqualTo(context);
    }

    @Test
    void shouldProtectChannel() {
        Context context = ContextMother.build();

        Context protectedContext = protector.apply(context);

        assertThat(protectedContext.getChannel()).isEqualTo(protectedChannel);
    }

    @Test
    void shouldProtectIdentity() {
        Context context = ContextMother.build();

        Context protectedContext = protector.apply(context);

        assertThat(protectedContext.getIdentity()).isEqualTo(protectedIdentity);
    }

    @Test
    void shouldMaskMethods() {
        Sequence sequence = SequenceMother.fakeOnly();
        Context context = ContextMother.withSequences(SequencesMother.with(sequence));

        Context protectedContext = protector.apply(context);

        Sequences sequences = protectedContext.getSequences();
        assertThat(sequences).containsExactly(sequence.withStages(StagesMother.with(MethodsMother.with(protectedMethod))));
    }

}
