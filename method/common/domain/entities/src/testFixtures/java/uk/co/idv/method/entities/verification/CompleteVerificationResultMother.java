package uk.co.idv.method.entities.verification;

public interface CompleteVerificationResultMother {

    static CompleteVerificationResult successful() {
        return builder().build();
    }

    static CompleteVerificationResult.CompleteVerificationResultBuilder builder() {
        return CompleteVerificationResult.builder()
                .verification(VerificationMother.successful())
                .contextComplete(true)
                .contextSuccessful(true);
    }

}
