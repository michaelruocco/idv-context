package uk.co.idv.policy.entities.policy;

public interface PoliciesMother {

    static Policies<Policy> singleFakePolicy() {
        return new Policies<>(FakePolicyMother.build());
    }

}
