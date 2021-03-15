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
import uk.co.idv.method.entities.verification.CompleteVerificationResult;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.VerificationMother;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

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
        Context originalContext = givenOriginalContext(request.getContextId());
        Context updatedContext = givenUpdatedContext(originalContext, request);
        Verification expectedVerification = givenVerification(updatedContext, request);

        CompleteVerificationResult result = completeVerification.complete(request);

        assertThat(result.getVerification()).isEqualTo(expectedVerification);
    }

    @Test
    void shouldReturnContextSuccessful() {
        CompleteVerificationRequest request = CompleteVerificationRequestMother.successful();
        Context originalContext = givenOriginalContext(request.getContextId());
        Context updatedContext = givenUpdatedContext(originalContext, request);
        givenVerification(updatedContext, request);
        given(updatedContext.isSuccessful()).willReturn(true);

        CompleteVerificationResult result = completeVerification.complete(request);

        assertThat(result.isContextSuccessful()).isTrue();
    }

    @Test
    void shouldReturnContextComplete() {
        CompleteVerificationRequest request = CompleteVerificationRequestMother.successful();
        Context originalContext = givenOriginalContext(request.getContextId());
        Context updatedContext = givenUpdatedContext(originalContext, request);
        givenVerification(updatedContext, request);
        given(updatedContext.isComplete()).willReturn(true);

        CompleteVerificationResult result = completeVerification.complete(request);

        assertThat(result.isContextComplete()).isTrue();
    }

    @Test
    void shouldSaveUpdatedContext() {
        CompleteVerificationRequest request = CompleteVerificationRequestMother.withoutTimestamp();
        Context originalContext = givenOriginalContext(request.getContextId());
        Context updatedContext = givenUpdatedContext(originalContext, request);
        givenVerification(updatedContext, request);

        completeVerification.complete(request);

        ArgumentCaptor<Context> captor = ArgumentCaptor.forClass(Context.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue()).isEqualTo(updatedContext);
    }

    @Test
    void shouldRecordAttemptIfRequired() {
        CompleteVerificationRequest request = CompleteVerificationRequestMother.withoutTimestamp();
        Context originalContext = givenOriginalContext(request.getContextId());
        Context updatedContext = givenUpdatedContext(originalContext, request);
        Verification verification = givenVerification(updatedContext, request);
        given(updatedContext.hasMoreCompletedMethodsThan(originalContext)).willReturn(true);
        given(updatedContext.hasMoreCompletedSequencesThan(originalContext)).willReturn(true);

        completeVerification.complete(request);

        ArgumentCaptor<ContextRecordAttemptRequest> captor = ArgumentCaptor.forClass(ContextRecordAttemptRequest.class);
        verify(lockoutService).recordAttemptIfRequired(captor.capture());
        ContextRecordAttemptRequest recordAttemptRequest = captor.getValue();
        assertThat(recordAttemptRequest.getResult()).isEqualTo(verification.toResult());
        assertThat(recordAttemptRequest.getContext()).isEqualTo(updatedContext);
        assertThat(recordAttemptRequest.isMethodComplete()).isTrue();
        assertThat(recordAttemptRequest.isSequenceComplete()).isTrue();
    }

    private Context givenOriginalContext(UUID contextId) {
        Context context = mock(Context.class);
        given(findContext.find(contextId)).willReturn(context);
        return context;
    }

    private Context givenUpdatedContext(Context originalContext, CompleteVerificationRequest request) {
        Context updatedContext = mock(Context.class);
        given(originalContext.completeVerification(request.withTimestampIfNotProvided(NOW))).willReturn(updatedContext);
        return updatedContext;
    }

    private Verification givenVerification(Context updatedContext, CompleteVerificationRequest request) {
        Verification verification = VerificationMother.successful();
        given(updatedContext.getVerification(request.getId())).willReturn(verification);
        return verification;
    }

}
