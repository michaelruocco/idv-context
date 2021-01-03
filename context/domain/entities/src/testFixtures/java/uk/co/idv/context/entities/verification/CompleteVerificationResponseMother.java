package uk.co.idv.context.entities.verification;


import uk.co.idv.context.entities.context.ContextMother;

public interface CompleteVerificationResponseMother {

    static CompleteVerificationResponse build() {
        return builder().build();
    }

    static CompleteVerificationResponse.CompleteVerificationResponseBuilder builder() {
        Verification successfulVerification = VerificationMother.successful();
        return CompleteVerificationResponse.builder()
                .original(ContextMother.withVerifications(VerificationsMother.oneIncomplete()))
                .updated(ContextMother.withVerifications(VerificationsMother.with(successfulVerification)))
                .verification(successfulVerification);
    }

}
