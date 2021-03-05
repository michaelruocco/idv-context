package uk.co.idv.policy.entities.policy.key.validcookie;

import uk.co.idv.policy.entities.policy.key.channelactivity.ChannelActivityPolicyKeyMother;

public interface ValidCookiePolicyKeyMother {

    static ValidCookiePolicyKey build() {
        return builder().build();
    }

    static ValidCookiePolicyKey.ValidCookiePolicyKeyBuilder builder() {
        return ValidCookiePolicyKey.builder()
                .baseKey(ChannelActivityPolicyKeyMother.build())
                .validCookie(true);
    }

}
