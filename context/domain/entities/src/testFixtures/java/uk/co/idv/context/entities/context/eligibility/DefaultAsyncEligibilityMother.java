package uk.co.idv.context.entities.context.eligibility;


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
