package uk.co.idv.context.entities.policy.method.otp.delivery;


public interface LastUpdatedConfigMother {

    static LastUpdatedConfig unknownAllowed() {
        return builder().allowUnknown(true).build();
    }

    static LastUpdatedConfig unknownNotAllowed() {
        return builder().allowUnknown(false).build();
    }

    static LastUpdatedConfig withCutoffDays(long days) {
        return builder().cutoffDays(days).build();
    }

    static LastUpdatedConfig withoutCutoffDays() {
        return builder().cutoffDays(null).build();
    }

    static LastUpdatedConfig.LastUpdatedConfigBuilder builder() {
        return LastUpdatedConfig.builder()
                .allowUnknown(true)
                .cutoffDays(5L);
    }

}
