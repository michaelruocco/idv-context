package uk.co.idv.method.entities.push;


import java.time.Duration;

public interface PushNotificationConfigMother {

    static PushNotificationConfig build() {
        return builder().build();
    }

    static PushNotificationConfig.PushNotificationConfigBuilder builder() {
        return PushNotificationConfig.builder()
                .maxNumberOfAttempts(3)
                .duration(Duration.ofMinutes(5));
    }

}
