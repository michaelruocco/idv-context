package uk.co.idv.context.entities.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.lockout.attempt.VerificationAttempts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ResetAttemptsFilterTest {

    private final PolicyKey key = mock(PolicyKey.class);

    private final AttemptsFilter filter = new AttemptsFilter(key);

    @Test
    void shouldReturnApplicableAttemptsIfKeyDoesNotHaveAliasType() {
        given(key.hasAliasType()).willReturn(false);
        LockoutStateRequest request = mock(LockoutStateRequest.class);
        VerificationAttempts attempts = mock(VerificationAttempts.class);
        given(request.getAttempts()).willReturn(attempts);
        VerificationAttempts applicable = mock(VerificationAttempts.class);
        given(attempts.applyingTo(key)).willReturn(applicable);

        VerificationAttempts resetAttempts = filter.filter(request);

        assertThat(resetAttempts).isEqualTo(applicable);
    }

    @Test
    void shouldReturnApplicableAttemptsWithAliasIfKeyHasAliasType() {
        given(key.hasAliasType()).willReturn(true);
        Alias alias = mock(Alias.class);
        LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getAlias()).willReturn(alias);
        VerificationAttempts attempts = mock(VerificationAttempts.class);
        given(request.getAttempts()).willReturn(attempts);
        VerificationAttempts applicable = mock(VerificationAttempts.class);
        VerificationAttempts applicableWithAlias = mock(VerificationAttempts.class);
        given(attempts.applyingTo(key)).willReturn(applicable);
        given(applicable.with(alias)).willReturn(applicableWithAlias);

        VerificationAttempts resetAttempts = filter.filter(request);

        assertThat(resetAttempts).isEqualTo(applicableWithAlias);
    }


}
