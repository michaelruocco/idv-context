package uk.co.idv.context.usecases.context.lockout;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.context.entities.context.create.ContextLockoutRequest;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.context.lockout.ContextRecordAttemptRequest;
import uk.co.idv.context.entities.lockout.ContextRecordAttemptRequestMother;
import uk.co.idv.lockout.entities.LockoutRequest;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.RecordAttemptRequest;
import uk.co.idv.lockout.usecases.LockoutService;

import java.time.Clock;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ContextLockoutServiceTest {

    private final Clock clock = mock(Clock.class);
    private final LockoutService lockoutService = mock(LockoutService.class);

    private final ContextLockoutService contextLockoutService = ContextLockoutService.builder()
            .clock(clock)
            .lockoutService(lockoutService)
            .build();

    @Test
    void shouldPassInitialRequestToLockoutService() {
        Context context = ContextMother.build();
        ServiceCreateContextRequest initialRequest = context.getRequest();

        contextLockoutService.validateLockoutState(context);

        ArgumentCaptor<ContextLockoutRequest> captor = ArgumentCaptor.forClass(ContextLockoutRequest.class);
        verify(lockoutService).loadAndValidateState(captor.capture());
        ContextLockoutRequest lockoutRequest = captor.getValue();
        assertThat(lockoutRequest.getContextRequest()).isEqualTo(initialRequest);
    }

    @Test
    void shouldPassTimestampToLockoutService() {
        Context context = ContextMother.build();
        Instant timestamp = givenNowTimestamp();

        contextLockoutService.validateLockoutState(context);

        ArgumentCaptor<ContextLockoutRequest> captor = ArgumentCaptor.forClass(ContextLockoutRequest.class);
        verify(lockoutService).loadAndValidateState(captor.capture());
        ContextLockoutRequest lockoutRequest = captor.getValue();
        assertThat(lockoutRequest.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    void shouldReturnLockoutState() {
        Context context = ContextMother.build();
        LockoutState expectedState = givenLockoutStateLoaded();

        LockoutState state = contextLockoutService.validateLockoutState(context);

        assertThat(state).isEqualTo(expectedState);
    }


    @Test
    void shouldRecordAttemptIfRequired() {
        ContextRecordAttemptRequest request = ContextRecordAttemptRequestMother.build();
        LockoutState expectedState = givenLockoutStateUpdated(request);

        LockoutState state = contextLockoutService.recordAttemptIfRequired(request);

        assertThat(state).isEqualTo(expectedState);
    }

    private Instant givenNowTimestamp() {
        Instant now = Instant.now();
        given(clock.instant()).willReturn(now);
        return now;
    }

    private LockoutState givenLockoutStateLoaded() {
        LockoutState state = mock(LockoutState.class);
        given(lockoutService.loadAndValidateState(any(LockoutRequest.class))).willReturn(state);
        return state;
    }

    private LockoutState givenLockoutStateUpdated(RecordAttemptRequest request) {
        LockoutState state = mock(LockoutState.class);
        given(lockoutService.recordAttemptIfRequired(request)).willReturn(state);
        return state;
    }

}
