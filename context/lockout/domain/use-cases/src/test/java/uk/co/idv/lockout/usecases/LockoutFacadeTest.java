package uk.co.idv.lockout.usecases;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.lockout.entities.ExternalLockoutRequest;
import uk.co.idv.lockout.entities.DefaultExternalLockoutRequestMother;
import uk.co.idv.lockout.entities.LockoutRequest;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.identity.usecases.identity.find.FindIdentity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutFacadeTest {

    private final FindIdentity findIdentity = mock(FindIdentity.class);
    private final LockoutService lockoutService = mock(LockoutService.class);
    private final ExternalLockoutRequestConverter converter = mock(ExternalLockoutRequestConverter.class);

    private final LockoutFacade facade = LockoutFacade.builder()
            .findIdentity(findIdentity)
            .lockoutService(lockoutService)
            .converter(converter)
            .build();

    @Test
    void shouldLoadLockoutState() {
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.build();
        Identity identity = givenIdentityFoundForAliases(externalRequest.getAliases());
        LockoutRequest lockoutRequest = mock(LockoutRequest.class);
        given(converter.toLockoutRequest(externalRequest, identity.getIdvId())).willReturn(lockoutRequest);
        LockoutState expectedState = givenLockoutStateLoaded(lockoutRequest);

        LockoutState state = facade.loadState(externalRequest);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldResetLockoutState() {
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.build();
        Identity identity = givenIdentityFoundForAliases(externalRequest.getAliases());
        LockoutRequest lockoutRequest = mock(LockoutRequest.class);
        given(converter.toLockoutRequest(externalRequest, identity.getIdvId())).willReturn(lockoutRequest);
        LockoutState expectedState = givenLockoutStateReset(lockoutRequest);

        LockoutState state = facade.resetState(externalRequest);

        assertThat(state).isEqualTo(expectedState);
    }

    private Identity givenIdentityFoundForAliases(Aliases aliases) {
        Identity identity = IdentityMother.example();
        given(findIdentity.find(aliases)).willReturn(identity);
        return identity;
    }

    private LockoutState givenLockoutStateLoaded(LockoutRequest request) {
        LockoutState state = mock(LockoutState.class);
        given(lockoutService.loadState(request)).willReturn(state);
        return state;
    }

    private LockoutState givenLockoutStateReset(LockoutRequest request) {
        LockoutState state = mock(LockoutState.class);
        given(lockoutService.resetState(request)).willReturn(state);
        return state;
    }

}
