package uk.co.idv.identity.usecases.eligibility;

public interface EligibilityNotConfiguredExceptionMother {

    static EligibilityNotConfiguredException build() {
        return new EligibilityNotConfiguredException("my-message");
    }

}
