package uk.co.idv.context.usecases.context;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.context.create.ContextLockoutRequest;
import uk.co.idv.context.entities.context.create.IdentityCreateContextRequest;
import uk.co.idv.context.entities.context.create.IdentityCreateContextRequestMother;
import uk.co.idv.lockout.entities.LockoutRequest;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.usecases.LockoutService;

import java.time.Clock;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LockoutStateValidatorTest {

    private final Clock clock = mock(Clock.class);
    private final LockoutService lockoutService = mock(LockoutService.class);

    private final LockoutStateValidator validator = LockoutStateValidator.builder()
            .clock(clock)
            .lockoutService(lockoutService)
            .build();

    @Test
    void shouldPassInitialRequestToLockoutService() {
        IdentityCreateContextRequest initialRequest = IdentityCreateContextRequestMother.build();

        validator.validateLockoutState(initialRequest);

        ArgumentCaptor<ContextLockoutRequest> captor = ArgumentCaptor.forClass(ContextLockoutRequest.class);
        verify(lockoutService).loadAndValidateState(captor.capture());
        ContextLockoutRequest lockoutRequest = captor.getValue();
        assertThat(lockoutRequest.getIdentityRequest()).isEqualTo(initialRequest);
    }

    @Test
    void shouldPassTimestampToLockoutService() {
        IdentityCreateContextRequest initialRequest = IdentityCreateContextRequestMother.build();
        Instant timestamp = givenNowTimestamp();

        validator.validateLockoutState(initialRequest);

        ArgumentCaptor<ContextLockoutRequest> captor = ArgumentCaptor.forClass(ContextLockoutRequest.class);
        verify(lockoutService).loadAndValidateState(captor.capture());
        ContextLockoutRequest lockoutRequest = captor.getValue();
        assertThat(lockoutRequest.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    void shouldReturnLockoutState() {
        IdentityCreateContextRequest initialRequest = IdentityCreateContextRequestMother.build();
        LockoutState expectedState = givenLockoutStateReturned();

        LockoutState state = validator.validateLockoutState(initialRequest);

        assertThat(state).isEqualTo(expectedState);
    }

    private Instant givenNowTimestamp() {
        Instant now = Instant.now();
        given(clock.instant()).willReturn(now);
        return now;
    }

    private LockoutState givenLockoutStateReturned() {
        LockoutState state = mock(LockoutState.class);
        given(lockoutService.loadAndValidateState(any(LockoutRequest.class))).willReturn(state);
        return state;
    }

}
