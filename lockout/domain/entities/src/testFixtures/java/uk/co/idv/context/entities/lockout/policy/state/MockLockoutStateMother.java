package uk.co.idv.context.entities.lockout.policy.state;

import uk.co.idv.identity.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.lockout.policy.LockoutState;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MockLockoutStateMother {

    private MockLockoutStateMother() {
        // utility class
    }

    public static LockoutState locked() {
        LockoutState state = build();
        given(state.isLocked()).willReturn(true);
        return state;
    }

    public static LockoutState unlocked() {
        LockoutState state = build();
        given(state.isLocked()).willReturn(false);
        return state;
    }

    private static LockoutState build() {
        LockoutState state = mock(LockoutState.class);
        given(state.getIdvId()).willReturn(IdvIdMother.idvId());
        return state;
    }

}
