package uk.co.idv.method.entities.eligibility;


public interface EligibilityMother {

    static Eligible eligible() {
        return new Eligible();
    }

    static Ineligible ineligible() {
        return ineligible("fake-reason");
    }

    static Ineligible ineligible(String reason) {
        return new Ineligible(reason);
    }

}
