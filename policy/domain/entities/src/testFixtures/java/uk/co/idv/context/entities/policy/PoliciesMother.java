package uk.co.idv.context.entities.policy;

public interface PoliciesMother {

    static Policies<Policy> singleFakePolicy() {
        return new Policies<>(FakePolicyMother.build());
    }

}
