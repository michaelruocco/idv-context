package uk.co.idv.identity.entities.eligibility;

public interface EligibilityNotConfiguredExceptionMother {

    static EligibilityNotConfiguredException build() {
        return new EligibilityNotConfiguredException("my-message");
    }

}
