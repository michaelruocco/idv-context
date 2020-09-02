package uk.co.idv.identity.adapter.json.error.eligibilitynotconfigured;

public interface EligibilityNotConfiguredErrorMother {

    static EligibilityNotConfiguredError eligibilityNotConfiguredError() {
        return new EligibilityNotConfiguredError("default-channel");
    }

}
