package uk.co.idv.method.entities.fake.policy;

import uk.co.idv.identity.entities.identity.RequestedDataMother;

public interface FakeMethodPolicyMother {

    static FakeMethodPolicy build() {
        return builder().build();
    }

    static FakeMethodPolicy.FakeMethodPolicyBuilder builder() {
        return FakeMethodPolicy.builder()
                .name("fake-method")
                .config(FakeMethodConfigMother.build())
                .requestedData(RequestedDataMother.noneRequested());
    }

}
