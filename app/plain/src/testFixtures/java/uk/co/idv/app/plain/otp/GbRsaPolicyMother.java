package uk.co.idv.app.plain.otp;

import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.context.entities.policy.sequence.SequencePolicies;
import uk.co.idv.context.entities.policy.sequence.SequencePoliciesMother;
import uk.co.idv.identity.entities.channel.gb.GbRsa;
import uk.co.idv.lockout.entities.policy.AttemptsFilter;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutPolicyMother;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutStateCalculatorMother;
import uk.co.idv.method.entities.otp.OtpConfigMother;
import uk.co.idv.method.entities.otp.policy.OtpPolicyMother;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfigsMother;
import uk.co.idv.method.entities.otp.policy.delivery.phone.sms.SmsDeliveryMethodConfigMother;
import uk.co.idv.method.entities.policy.MethodPolicy;
import uk.co.idv.policy.entities.policy.key.ChannelPolicyKeyMother;
import uk.co.idv.policy.entities.policy.key.PolicyKey;

import java.util.UUID;

public interface GbRsaPolicyMother {

    static ContextPolicy gbRsaContextPolicy() {
        return ContextPolicyMother.builder()
                .key(buildKey(UUID.fromString("29afbb27-cc37-4ba4-810f-57915e90eac2")))
                .sequencePolicies(buildSequences())
                .protectSensitiveData(true)
                .build();
    }

    static LockoutPolicy gbRsaLockoutPolicy() {
        PolicyKey key = buildKey(UUID.fromString("235b1924-3401-4899-9365-9fd9e2b01001"));
        return LockoutPolicyMother.builder()
                .key(key)
                .attemptsFilter(new AttemptsFilter(key))
                .stateCalculator(HardLockoutStateCalculatorMother.build())
                .build();
    }

    private static PolicyKey buildKey(UUID id) {
        return ChannelPolicyKeyMother.builder()
                .id(id)
                .channelId(GbRsa.ID)
                .priority(99)
                .build();
    }

    private static SequencePolicies buildSequences() {
        MethodPolicy methodPolicy = OtpPolicyMother.builder()
                .deliveryMethodConfigs(DeliveryMethodConfigsMother.with(SmsDeliveryMethodConfigMother.withAsyncSimSwap()))
                .config(OtpConfigMother.build())
                .build();
        return SequencePoliciesMother.withMethodPolicy(methodPolicy);
    }

}
