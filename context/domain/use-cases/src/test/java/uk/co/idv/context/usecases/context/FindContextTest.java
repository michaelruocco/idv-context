package uk.co.idv.context.usecases.context;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.context.usecases.context.lockout.ContextLockoutService;
import uk.co.idv.lockout.usecases.state.LockedOutException;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class FindContextTest {

    private static final Instant NOW = Instant.parse("2020-09-13T07:14:01.050Z");

    private final ContextLockoutService lockoutService = mock(ContextLockoutService.class);
    private final Clock clock = Clock.fixed(NOW, ZoneId.systemDefault());
    private final ContextRepository repository = mock(ContextRepository.class);
    private final MdcPopulator mdcPopulator = mock(MdcPopulator.class);

    private final FindContext findContext = FindContext.builder()
            .lockoutService(lockoutService)
            .clock(clock)
            .repository(repository)
            .mdcPopulator(mdcPopulator)
            .build();

    @Test
    void shouldPopulateContextIdInMdcContext() {
        UUID id = UUID.randomUUID();
        givenContextFoundForId(id);

        findContext.find(id);

        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);
        verify(mdcPopulator).populateContextId(captor.capture());
        assertThat(captor.getValue()).isEqualTo(id);
    }

    @Test
    void shouldFindContextIfExists() {
        UUID id = UUID.randomUUID();
        Context expectedContext = givenContextFoundForId(id);

        Context context = findContext.find(id);

        assertThat(context).isEqualTo(expectedContext);
    }

    @Test
    void shouldThrowExceptionIfContextNotFound() {
        UUID id = UUID.randomUUID();
        givenContextNotFound(id);

        Throwable error = catchThrowable(() -> findContext.find(id));

        assertThat(error)
                .isInstanceOf(ContextNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldThrowExceptionIfContextExpired() {
        Instant expiry = NOW.minusMillis(1);
        Context context = ContextMother.withExpiry(expiry);
        UUID id = context.getId();
        givenContextFound(context);

        ContextExpiredException error = catchThrowableOfType(
                () -> findContext.find(id),
                ContextExpiredException.class
        );

        assertThat(error.getId()).isEqualTo(id);
        assertThat(error.getExpiry()).isEqualTo(expiry);
    }

    @Test
    void shouldThrowExceptionIfLockedOnFind() {
        Context context = ContextMother.build();
        UUID id = context.getId();
        givenContextFound(context);
        givenLocked(context);

        Throwable error = catchThrowable(() -> findContext.find(id));

        assertThat(error).isInstanceOf(LockedOutException.class);
    }

    private Context givenContextFoundForId(UUID id) {
        Context context = ContextMother.withId(id);
        givenContextFound(context);
        return context;
    }

    private void givenContextFound(Context context) {
        given(repository.load(context.getId())).willReturn(Optional.of(context));
    }

    private void givenContextNotFound(UUID id) {
        given(repository.load(id)).willReturn(Optional.empty());
    }

    private void givenLocked(Context context) {
        given(lockoutService.validateLockoutState(context)).willThrow(mock(LockedOutException.class));
    }

}
