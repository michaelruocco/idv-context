package uk.co.idv.method.entities.method;


import uk.co.idv.method.entities.eligibility.EligibilityMother;
import uk.co.idv.method.entities.policy.FakeMethodConfigMother;

import java.time.Duration;

public interface FakeMethodMother {

    static FakeMethod build() {
        return builder().build();
    }

    static FakeMethod withName(String name) {
        return builder().name(name).build();
    }

    static FakeMethod withDuration(Duration duration) {
        return withConfig(FakeMethodConfigMother.withDuration(duration));
    }

    static FakeMethod withConfig(MethodConfig config) {
        return builder().config(config).build();
    }

    static FakeMethod eligible() {
        return builder().build();
    }

    static FakeMethod ineligible() {
        return builder().eligibility(EligibilityMother.ineligible()).build();
    }

    static FakeMethod complete() {
        return builder().complete(true).build();
    }

    static FakeMethod incomplete() {
        return builder().build();
    }

    static FakeMethod successful() {
        return builder().successful(true).build();
    }

    static FakeMethod unsuccessful() {
        return builder().build();
    }

    static FakeMethod.FakeMethodBuilder builder() {
        return FakeMethod.builder()
                .name("fake-method")
                .complete(false)
                .successful(false)
                .config(FakeMethodConfigMother.build())
                .eligibility(EligibilityMother.eligible());
    }

}
