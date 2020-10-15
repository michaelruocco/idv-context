package uk.co.idv.app.spring.lockout;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.lockout.entities.DefaultExternalLockoutRequestMother;
import uk.co.idv.lockout.entities.ExternalLockoutRequest;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutStateMother;
import uk.co.idv.lockout.usecases.LockoutFacade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LockoutStateControllerTest {

    private static final String CHANNEL_ID = "default-channel";
    private static final String ACTIVITY_NAME = "default-activity";
    private static final String ALIAS_TYPE = "default-alias";
    private static final String ALIAS_VALUE = "my-alias-value";

    private final LockoutFacade facade = mock(LockoutFacade.class);

    private final LockoutStateController controller = new LockoutStateController(facade);

    @Test
    void shouldPassExternalLockoutRequestWhenGettingLockoutState() {
        Aliases aliases = AliasesMother.defaultAliasOnly();
        given(facade.toAliases(ALIAS_TYPE, ALIAS_VALUE)).willReturn(aliases);
        ArgumentCaptor<ExternalLockoutRequest> captor = ArgumentCaptor.forClass(ExternalLockoutRequest.class);

        controller.getState(CHANNEL_ID, ACTIVITY_NAME, ALIAS_TYPE, ALIAS_VALUE);

        verify(facade).loadState(captor.capture());
        assertThat(captor.getValue())
                .hasFieldOrPropertyWithValue("channelId", CHANNEL_ID)
                .hasFieldOrPropertyWithValue("activityName", ACTIVITY_NAME)
                .hasFieldOrPropertyWithValue("aliases", aliases);
    }

    @Test
    void shouldReturnLockoutStateOnGet() {
        LockoutState expectedState = HardLockoutStateMother.build();
        given(facade.loadState(any(ExternalLockoutRequest.class))).willReturn(expectedState);

        LockoutState state = controller.getState(CHANNEL_ID, ACTIVITY_NAME, ALIAS_TYPE, ALIAS_VALUE);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldResetLockoutState() {
        ExternalLockoutRequest request = DefaultExternalLockoutRequestMother.build();
        LockoutState expectedState = HardLockoutStateMother.build();
        given(facade.resetState(request)).willReturn(expectedState);

        LockoutState state = controller.resetState(request);

        assertThat(state).isEqualTo(expectedState);
    }

}
