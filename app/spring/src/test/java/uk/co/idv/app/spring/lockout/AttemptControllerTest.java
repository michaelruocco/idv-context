package uk.co.idv.app.spring.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.lockout.DefaultRecordAttemptRequestMother;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptRequest;
import uk.co.idv.context.usecases.lockout.LockoutFacade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AttemptControllerTest {

    private final LockoutFacade facade = mock(LockoutFacade.class);

    private final AttemptController controller = new AttemptController(facade);

    @Test
    void shouldRecordAttempt() {
        RecordAttemptRequest request = DefaultRecordAttemptRequestMother.build();
        LockoutState expectedState = givenLockoutStateUpdated(request);

        LockoutState state = controller.recordAttempt(request);

        assertThat(state).isEqualTo(expectedState);
    }

    private LockoutState givenLockoutStateUpdated(RecordAttemptRequest request) {
        LockoutState state = mock(LockoutState.class);
        given(facade.recordAttempt(request)).willReturn(state);
        return state;
    }

}
