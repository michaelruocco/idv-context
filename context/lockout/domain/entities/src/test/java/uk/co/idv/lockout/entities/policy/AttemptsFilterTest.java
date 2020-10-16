package uk.co.idv.lockout.entities.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.policy.entities.policy.key.PolicyKey;
import uk.co.idv.lockout.entities.attempt.Attempts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AttemptsFilterTest {

    private final PolicyKey key = mock(PolicyKey.class);

    private final AttemptsFilter filter = new AttemptsFilter(key);

    @Test
    void shouldReturnApplicableAttemptsIfKeyDoesNotHaveAliasType() {
        given(key.hasAliasType()).willReturn(false);
        LockoutStateRequest request = mock(LockoutStateRequest.class);
        Attempts attempts = mock(Attempts.class);
        given(request.getAttempts()).willReturn(attempts);
        Attempts applicable = mock(Attempts.class);
        given(attempts.applyingTo(key)).willReturn(applicable);

        Attempts resetAttempts = filter.filter(request);

        assertThat(resetAttempts).isEqualTo(applicable);
    }

    @Test
    void shouldReturnApplicableAttemptsWithAliasIfKeyHasAliasType() {
        given(key.hasAliasType()).willReturn(true);
        Aliases aliases = mock(Aliases.class);
        LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getAliases()).willReturn(aliases);
        Attempts attempts = mock(Attempts.class);
        given(request.getAttempts()).willReturn(attempts);
        Attempts applicable = mock(Attempts.class);
        Attempts applicableWithAlias = mock(Attempts.class);
        given(attempts.applyingTo(key)).willReturn(applicable);
        given(applicable.with(aliases)).willReturn(applicableWithAlias);

        Attempts resetAttempts = filter.filter(request);

        assertThat(resetAttempts).isEqualTo(applicableWithAlias);
    }


}
