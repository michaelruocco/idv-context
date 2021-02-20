package uk.co.idv.method.entities.verification;

public interface CompleteVerificationResponseMother {

    static CompleteVerificationResponse build() {
        return builder().build();
    }

    static CompleteVerificationResponse.CompleteVerificationResponseBuilder builder() {
        Verification successfulVerification = VerificationMother.successful();
        return CompleteVerificationResponse.builder()
                .sequenceCompletedByVerification(false)
                .methodCompletedByVerification(true)
                .verification(successfulVerification);
    }

}
