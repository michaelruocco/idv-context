package uk.co.idv.method.entities.verification;

public interface VerificationsMother {

    static Verifications oneIncomplete() {
        return with(VerificationMother.incomplete());
    }

    static Verifications oneSuccessful() {
        return with(VerificationMother.successful());
    }

    static Verifications empty() {
        return new Verifications();
    }

    static Verifications with(Verification... verifications) {
        return new Verifications(verifications);
    }

}
