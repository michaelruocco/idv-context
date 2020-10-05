package uk.co.idv.context.entities.policy.method.otp.delivery;


public interface LastUpdatedConfigMother {

    static LastUpdatedConfig unknownAllowed() {
        return builder().allowUnknown(true).build();
    }

    static LastUpdatedConfig unknownNotAllowed() {
        return builder().allowUnknown(false).build();
    }

    static LastUpdatedConfig withMinDaysSinceUpdate(long days) {
        return builder().minDaysSinceUpdate(days).build();
    }

    static LastUpdatedConfig withoutMinDaysSinceUpdate() {
        return builder().minDaysSinceUpdate(null).build();
    }

    static LastUpdatedConfig.LastUpdatedConfigBuilder builder() {
        return LastUpdatedConfig.builder()
                .allowUnknown(true)
                .minDaysSinceUpdate(5L);
    }

}
