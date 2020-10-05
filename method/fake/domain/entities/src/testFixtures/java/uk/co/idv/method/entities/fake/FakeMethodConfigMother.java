package uk.co.idv.method.entities.fake;

import java.time.Duration;

public interface FakeMethodConfigMother {

    static FakeMethodConfig build() {
        return builder().build();
    }

    static FakeMethodConfig withDuration(Duration duration) {
        return builder().duration(duration).build();
    }

    static FakeMethodConfig.FakeMethodConfigBuilder builder() {
        return FakeMethodConfig.builder()
                .maxNumberOfAttempts(3)
                .duration(Duration.ofMinutes(5))
                .fakeValue("fake");
    }

}
