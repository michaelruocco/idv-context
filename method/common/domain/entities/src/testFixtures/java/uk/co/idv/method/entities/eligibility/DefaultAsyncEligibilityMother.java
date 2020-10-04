package uk.co.idv.method.entities.eligibility;


public interface DefaultAsyncEligibilityMother {

    static AsyncEligibility eligible() {
        return builder().build();
    }

    static AsyncEligibility ineligible() {
        return builder()
                .eligible(false)
                .reason("fake-reason")
                .build();
    }

    static DefaultAsyncEligibility.DefaultAsyncEligibilityBuilder builder() {
        return DefaultAsyncEligibility.builder()
                .eligible(true)
                .complete(true);
    }

}
