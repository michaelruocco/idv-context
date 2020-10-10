package uk.co.idv.context.usecases.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequestMother;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.context.entities.context.sequence.SequencesRequest;
import uk.co.idv.context.entities.context.sequence.SequencesRequestMother;
import uk.co.idv.context.usecases.context.expiry.ExpiryCalculator;
import uk.co.idv.context.usecases.context.lockout.ContextLockoutService;
import uk.co.idv.context.usecases.context.sequence.SequencesBuilder;
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

class ContextServiceTest {

    private static final Instant NOW = Instant.parse("2020-09-13T07:14:01.050Z");

    private final ContextLockoutService lockoutService = mock(ContextLockoutService.class);
    private final CreateContextRequestConverter requestConverter = mock(CreateContextRequestConverter.class);
    private final Clock clock = Clock.fixed(NOW, ZoneId.systemDefault());
    private final SequencesBuilder sequencesBuilder = mock(SequencesBuilder.class);
    private final ExpiryCalculator expiryCalculator = mock(ExpiryCalculator.class);
    private final ContextRepository repository = mock(ContextRepository.class);

    private final ContextService service = ContextService.builder()
            .lockoutService(lockoutService)
            .requestConverter(requestConverter)
            .clock(clock)
            .sequencesBuilder(sequencesBuilder)
            .expiryCalculator(expiryCalculator)
            .repository(repository)
            .build();

    @Test
    void shouldThrowExceptionIfLockedOnCreate() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();
        givenLocked(request);

        Throwable error = catchThrowable(() -> service.create(request));

        assertThat(error).isInstanceOf(LockedOutException.class);
    }

    @Test
    void shouldPopulateIdOnContext() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();
        SequencesRequest sequencesRequest = givenConvertsToSequencesRequest(request);

        Context context = service.create(request);

        assertThat(context.getId()).isEqualTo(sequencesRequest.getContextId());
    }

    @Test
    void shouldPopulateCreatedOnContext() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();
        givenConvertsToSequencesRequest(request);

        Context context = service.create(request);

        assertThat(context.getCreated()).isEqualTo(NOW);
    }

    @Test
    void shouldPopulateRequestOnContext() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();
        givenConvertsToSequencesRequest(request);

        Context context = service.create(request);

        assertThat(context.getRequest()).isEqualTo(request);
    }

    @Test
    void shouldPopulateSequencesOnContext() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();
        Sequences sequences = givenSequencesBuiltFromRequest(request);

        Context context = service.create(request);

        assertThat(context.getSequences()).isEqualTo(sequences);
    }

    @Test
    void shouldPopulateExpiryOnContext() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();
        Sequences sequences = givenSequencesBuiltFromRequest(request);
        Instant expectedExpiry = givenExpiryCalculatedFor(sequences);

        Context context = service.create(request);

        assertThat(context.getExpiry()).isEqualTo(expectedExpiry);
    }

    @Test
    void shouldFindContextIfExists() {
        UUID id = UUID.randomUUID();
        Context expectedContext = givenContextFoundForId(id);

        Context context = service.find(id);

        assertThat(context).isEqualTo(expectedContext);
    }

    @Test
    void shouldThrowExceptionIfContextNotFound() {
        UUID id = UUID.randomUUID();
        givenContextNotFound(id);

        Throwable error = catchThrowable(() -> service.find(id));

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
                () -> service.find(id),
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

        Throwable error = catchThrowable(() -> service.find(id));

        assertThat(error).isInstanceOf(LockedOutException.class);
    }

    private SequencesRequest givenConvertsToSequencesRequest(ServiceCreateContextRequest request) {
        SequencesRequest sequencesRequest = SequencesRequestMother.build();
        given(requestConverter.toSequencesRequest(request)).willReturn(sequencesRequest);
        return sequencesRequest;
    }

    private Sequences givenSequencesBuiltFromRequest(ServiceCreateContextRequest request) {
        SequencesRequest sequencesRequest = givenConvertsToSequencesRequest(request);
        return givenSequencesBuiltFromRequest(sequencesRequest);
    }

    private Sequences givenSequencesBuiltFromRequest(SequencesRequest request) {
        Sequences sequences = mock(Sequences.class);
        given(sequencesBuilder.build(request)).willReturn(sequences);
        return sequences;
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

    private Instant givenExpiryCalculatedFor(Sequences sequences) {
        Instant expiry = Instant.parse("2020-09-25T07:20:01.050Z");
        given(expiryCalculator.calculate(NOW, sequences)).willReturn(expiry);
        return expiry;
    }

    private void givenLocked(ServiceCreateContextRequest request) {
        given(lockoutService.validateLockoutState(request)).willThrow(mock(LockedOutException.class));
    }

    private void givenLocked(Context context) {
        given(lockoutService.validateLockoutState(context)).willThrow(mock(LockedOutException.class));
    }

}
