package uk.co.idv.method.entities.push.policy;

import uk.co.idv.method.entities.push.PushNotificationConfigMother;

public interface PushNotificationPolicyMother {

    static PushNotificationPolicy build() {
        return builder().build();
    }

    static PushNotificationPolicy.PushNotificationPolicyBuilder builder() {
        return PushNotificationPolicy.builder()
                .config(PushNotificationConfigMother.build());
    }

}
