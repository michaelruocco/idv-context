package uk.co.idv.method.entities.method.fake.policy;

import uk.co.idv.identity.entities.identity.RequestedDataMother;
import uk.co.idv.method.entities.method.fake.FakeMethodConfigMother;

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
