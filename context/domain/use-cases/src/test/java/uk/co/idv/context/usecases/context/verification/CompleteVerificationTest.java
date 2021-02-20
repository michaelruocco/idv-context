package uk.co.idv.context.usecases.context.verification;


import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.lockout.ContextRecordAttemptRequest;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.context.FindContext;
import uk.co.idv.context.usecases.context.lockout.ContextLockoutService;
import uk.co.idv.method.entities.verification.CompleteVerificationRequest;
import uk.co.idv.method.entities.verification.CompleteVerificationRequestMother;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.VerificationMother;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CompleteVerificationTest {

    private static final Instant NOW = Instant.now();

    private final FindContext findContext = mock(FindContext.class);
    private final ContextRepository repository = mock(ContextRepository.class);
    private final ContextLockoutService lockoutService = mock(ContextLockoutService.class);
    private final Clock clock = Clock.fixed(NOW, ZoneId.systemDefault());

    private final CompleteVerification completeVerification = CompleteVerification.builder()
            .findContext(findContext)
            .repository(repository)
            .lockoutService(lockoutService)
            .clock(clock)
            .build();

    @Test
    void shouldReturnCompletedVerification() {
        CompleteVerificationRequest request = CompleteVerificationRequestMother.successful();
        Context originalContext = mock(Context.class);
        given(findContext.find(request.getContextId())).willReturn(originalContext);
        Context updatedContext = mock(Context.class);
        given(originalContext.completeVerification(request.withTimestampIfNotProvided(NOW))).willReturn(updatedContext);
        Verification expectedVerification = mock(Verification.class);
        given(updatedContext.getVerification(request.getId())).willReturn(expectedVerification);

        Verification verification = completeVerification.complete(request);

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldSaveUpdatedContext() {
        CompleteVerificationRequest request = CompleteVerificationRequestMother.withoutTimestamp();
        Context context = mock(Context.class);
        given(findContext.find(request.getContextId())).willReturn(context);
        Context updatedContext = mock(Context.class);
        given(context.completeVerification(request.withTimestampIfNotProvided(NOW))).willReturn(updatedContext);
        Verification verification = VerificationMother.successful();
        given(updatedContext.getVerification(request.getId())).willReturn(verification);

        completeVerification.complete(request);

        ArgumentCaptor<Context> captor = ArgumentCaptor.forClass(Context.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue()).isEqualTo(updatedContext);
    }

    @Test
    void shouldRecordAttemptIfRequired() {
        CompleteVerificationRequest request = CompleteVerificationRequestMother.withoutTimestamp();
        Context context = mock(Context.class);
        given(findContext.find(request.getContextId())).willReturn(context);
        Context updatedContext = mock(Context.class);
        given(context.completeVerification(request.withTimestampIfNotProvided(NOW))).willReturn(updatedContext);
        Verification verification = VerificationMother.successful();
        given(updatedContext.getVerification(request.getId())).willReturn(verification);
        given(updatedContext.hasMoreCompletedMethodsThan(context)).willReturn(true);
        given(updatedContext.hasMoreCompletedSequencesThan(context)).willReturn(true);

        completeVerification.complete(request);

        ArgumentCaptor<ContextRecordAttemptRequest> captor = ArgumentCaptor.forClass(ContextRecordAttemptRequest.class);
        verify(lockoutService).recordAttemptIfRequired(captor.capture());
        ContextRecordAttemptRequest recordAttemptRequest = captor.getValue();
        assertThat(recordAttemptRequest.getResult()).isEqualTo(verification.toResult());
        assertThat(recordAttemptRequest.getContext()).isEqualTo(updatedContext);
        assertThat(recordAttemptRequest.isMethodComplete()).isTrue();
        assertThat(recordAttemptRequest.isSequenceComplete()).isTrue();
    }

}
