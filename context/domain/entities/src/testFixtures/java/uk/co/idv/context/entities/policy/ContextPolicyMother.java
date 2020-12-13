package uk.co.idv.context.entities.policy;

import uk.co.idv.context.entities.policy.sequence.SequencePolicies;
import uk.co.idv.context.entities.policy.sequence.SequencePoliciesMother;
import uk.co.idv.context.entities.policy.sequence.SequencePolicy;
import uk.co.idv.policy.entities.policy.key.ChannelPolicyKeyMother;
import uk.co.idv.policy.entities.policy.key.PolicyKey;

public interface ContextPolicyMother {

    static ContextPolicy build() {
        return builder().build();
    }

    static ContextPolicy withKey(PolicyKey key) {
        return builder().key(key).build();
    }

    static ContextPolicy withPolicy(SequencePolicy policy) {
        return withPolicies(SequencePoliciesMother.withSequencePolicy(policy));
    }

    static ContextPolicy withPolicies(SequencePolicies policies) {
        return builder().sequencePolicies(policies).build();
    }

    static ContextPolicy.ContextPolicyBuilder builder() {
        return ContextPolicy.builder()
                .key(ChannelPolicyKeyMother.build())
                .sequencePolicies(SequencePoliciesMother.build())
                .maskSensitiveData(false);
    }

}
