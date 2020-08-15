package uk.co.idv.context.usecases.eligibility;

public interface EligibilityNotConfiguredExceptionMother {

    static EligibilityNotConfiguredException build() {
        return new EligibilityNotConfiguredException("my-message");
    }

}
