package uk.co.idv.app.plain.otp;

import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.context.entities.policy.sequence.SequencePolicies;
import uk.co.idv.context.entities.policy.sequence.SequencePoliciesMother;
import uk.co.idv.identity.entities.channel.gb.Abc;
import uk.co.idv.lockout.entities.policy.AttemptsFilter;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutPolicyMother;
import uk.co.idv.lockout.entities.policy.soft.RecurringSoftLockoutStateCalculatorMother;
import uk.co.idv.method.entities.otp.OtpConfigMother;
import uk.co.idv.method.entities.otp.policy.OtpPolicyMother;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfigsMother;
import uk.co.idv.method.entities.otp.policy.delivery.email.EmailDeliveryMethodConfigMother;
import uk.co.idv.method.entities.policy.MethodPolicy;
import uk.co.idv.policy.entities.policy.key.ChannelPolicyKeyMother;
import uk.co.idv.policy.entities.policy.key.PolicyKey;

import java.util.UUID;

public interface AbcPolicyMother {

    static ContextPolicy abcContextPolicy() {
        return ContextPolicyMother.builder()
                .key(buildKey(UUID.fromString("98eb6d18-0d62-4883-a719-448286bc7a4b")))
                .sequencePolicies(buildSequences())
                .build();
    }

    static LockoutPolicy abcLockoutPolicy() {
        PolicyKey key = buildKey(UUID.fromString("b253ba5e-8133-47f0-bb7a-29a53ce7ba1d"));
        return LockoutPolicyMother.builder()
                .key(key)
                .attemptsFilter(new AttemptsFilter(key))
                .stateCalculator(RecurringSoftLockoutStateCalculatorMother.build())
                .build();
    }

    private static PolicyKey buildKey(UUID id) {
        return ChannelPolicyKeyMother.builder()
                .id(id)
                .channelId(Abc.ID)
                .priority(99)
                .build();
    }

    private static SequencePolicies buildSequences() {
        MethodPolicy methodPolicy = OtpPolicyMother.builder()
                .deliveryMethodConfigs(DeliveryMethodConfigsMother.with(EmailDeliveryMethodConfigMother.email()))
                .config(OtpConfigMother.build())
                .build();
        return SequencePoliciesMother.withMethodPolicy(methodPolicy);
    }

}
