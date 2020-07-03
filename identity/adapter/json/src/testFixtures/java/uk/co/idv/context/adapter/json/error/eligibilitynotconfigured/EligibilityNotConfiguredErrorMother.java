package uk.co.idv.context.adapter.json.error.eligibilitynotconfigured;

public interface EligibilityNotConfiguredErrorMother {

    static EligibilityNotConfiguredError eligibilityNotConfiguredError() {
        return new EligibilityNotConfiguredError("default-channel");
    }

}
