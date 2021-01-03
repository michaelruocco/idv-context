package uk.co.idv.context.entities.verification;

public interface VerificationsMother {

    static Verifications oneIncomplete() {
        return with(VerificationMother.incomplete());
    }

    static Verifications empty() {
        return new Verifications();
    }

    static Verifications with(Verification... verifications) {
        return new Verifications(verifications);
    }

}
