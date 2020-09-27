package uk.co.idv.context.entities.context.eligibility;


public interface EligibilityMother {

    static Eligible eligible() {
        return new Eligible();
    }

    static Ineligible ineligible() {
        return new Ineligible("fake-reason");
    }

}
