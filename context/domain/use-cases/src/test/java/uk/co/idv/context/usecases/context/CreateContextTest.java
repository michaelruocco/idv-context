package uk.co.idv.context.usecases.context;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.common.adapter.protector.ContextDataProtector;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequestMother;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.context.entities.context.sequence.SequencesRequest;
import uk.co.idv.context.entities.context.sequence.SequencesRequestMother;
import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.context.usecases.context.event.create.ContextCreatedHandler;
import uk.co.idv.context.usecases.context.expiry.ExpiryCalculator;
import uk.co.idv.context.usecases.context.lockout.ContextLockoutService;
import uk.co.idv.context.usecases.context.sequence.SequencesBuilder;
import uk.co.idv.lockout.usecases.state.LockedOutException;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class CreateContextTest {

    private static final Instant NOW = Instant.parse("2020-09-13T07:14:01.050Z");

    private final ContextLockoutService lockoutService = mock(ContextLockoutService.class);
    private final CreateContextRequestConverter requestConverter = mock(CreateContextRequestConverter.class);
    private final Clock clock = Clock.fixed(NOW, ZoneId.systemDefault());
    private final SequencesBuilder sequencesBuilder = mock(SequencesBuilder.class);
    private final ExpiryCalculator expiryCalculator = mock(ExpiryCalculator.class);
    private final ContextCreatedHandler createdHandler = mock(ContextCreatedHandler.class);
    private final ContextRepository repository = mock(ContextRepository.class);
    private final ContextDataProtector protector = mock(ContextDataProtector.class);

    private final CreateContext createContext = CreateContext.builder()
            .lockoutService(lockoutService)
            .requestConverter(requestConverter)
            .clock(clock)
            .sequencesBuilder(sequencesBuilder)
            .expiryCalculator(expiryCalculator)
            .createdHandler(createdHandler)
            .repository(repository)
            .protector(protector)
            .build();

    @Test
    void shouldThrowExceptionIfLockedOnCreate() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();
        givenLocked(request);

        Throwable error = catchThrowable(() -> createContext.create(request));

        assertThat(error).isInstanceOf(LockedOutException.class);
    }

    @Test
    void shouldPopulateIdOnContext() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();
        SequencesRequest sequencesRequest = givenConvertsToSequencesRequest(request);
        givenSequencesBuiltFromRequest(request);

        Context context = createContext.create(request);

        assertThat(context.getId()).isEqualTo(sequencesRequest.getContextId());
    }

    @Test
    void shouldPopulateCreatedOnContext() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();
        givenConvertsToSequencesRequest(request);
        givenSequencesBuiltFromRequest(request);

        Context context = createContext.create(request);

        assertThat(context.getCreated()).isEqualTo(NOW);
    }

    @Test
    void shouldPopulateRequestOnContext() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();
        givenConvertsToSequencesRequest(request);
        givenSequencesBuiltFromRequest(request);

        Context context = createContext.create(request);

        assertThat(context.getRequest()).isEqualTo(request);
    }

    @Test
    void shouldPopulateSequencesOnContext() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();
        Sequences sequences = givenSequencesBuiltFromRequest(request);

        Context context = createContext.create(request);

        assertThat(context.getSequences()).isEqualTo(sequences);
    }

    @Test
    void shouldPopulateExpiryOnContext() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();
        Sequences sequences = givenSequencesBuiltFromRequest(request);
        Instant expectedExpiry = givenExpiryCalculatedFor(sequences);

        Context context = createContext.create(request);

        assertThat(context.getExpiry()).isEqualTo(expectedExpiry);
    }

    @Test
    void shouldSaveContext() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();
        givenSequencesBuiltFromRequest(request);

        Context context = createContext.create(request);

        ArgumentCaptor<Context> captor = ArgumentCaptor.forClass(Context.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue()).isEqualTo(context);
    }

    @Test
    void shouldPublishContextCreatedOnCreate() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();
        givenSequencesBuiltFromRequest(request);

        Context context = createContext.create(request);

        ArgumentCaptor<Context> captor = ArgumentCaptor.forClass(Context.class);
        verify(createdHandler).created(captor.capture());
        assertThat(captor.getValue()).isEqualTo(context);
    }

    @Test
    void shouldNotProtectIfPolicyNotConfiguredToProtectSensitiveData() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.builder()
                .policy(ContextPolicyMother.withProtectSensitiveData(false))
                .build();
        givenSequencesBuiltFromRequest(request);

        Context context = createContext.create(request);

        verify(protector, never()).apply(any(Context.class));
        assertThat(context).isNotNull();
    }

    @Test
    void shouldProtectIfPolicyConfiguredToProtectSensitiveData() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.builder()
                .policy(ContextPolicyMother.withProtectSensitiveData(true))
                .build();
        givenSequencesBuiltFromRequest(request);
        Context expectedContext = givenProtectedContext();

        Context context = createContext.create(request);

        assertThat(context).isEqualTo(expectedContext);
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

    private Instant givenExpiryCalculatedFor(Sequences sequences) {
        Duration duration = Duration.ofMinutes(5);
        Instant expiry = Instant.parse("2020-09-25T07:20:01.050Z");
        given(sequences.getDuration()).willReturn(duration);
        given(expiryCalculator.calculate(NOW, duration)).willReturn(expiry);
        return expiry;
    }

    private void givenLocked(ServiceCreateContextRequest request) {
        given(lockoutService.validateLockoutState(request)).willThrow(mock(LockedOutException.class));
    }

    private Context givenProtectedContext() {
        Context protectedContext = mock(Context.class);
        given(protector.apply(any(Context.class))).willReturn(protectedContext);
        return protectedContext;
    }

}
