package uk.co.idv.method.entities.verification;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompleteVerificationResultTest {

    @Test
    void shouldReturnVerification() {
        Verification verification = VerificationMother.successful();

        CompleteVerificationResult result = CompleteVerificationResult.builder()
                .verification(verification)
                .build();

        assertThat(result.getVerification()).isEqualTo(verification);
    }

    @Test
    void shouldReturnVerificationComplete() {
        Verification verification = VerificationMother.successful();

        CompleteVerificationResult result = CompleteVerificationResult.builder()
                .verification(verification)
                .build();

        assertThat(result.isVerificationComplete()).isEqualTo(verification.isComplete());
    }

    @Test
    void shouldReturnVerificationSuccessful() {
        Verification verification = VerificationMother.successful();

        CompleteVerificationResult result = CompleteVerificationResult.builder()
                .verification(verification)
                .build();

        assertThat(result.isVerificationSuccessful()).isEqualTo(verification.isSuccessful());
    }

    @Test
    void shouldReturnContextComplete() {
        CompleteVerificationResult result = CompleteVerificationResult.builder()
                .contextComplete(true)
                .build();

        assertThat(result.isContextComplete()).isTrue();
    }

    @Test
    void shouldReturnContextSuccessful() {
        CompleteVerificationResult result = CompleteVerificationResult.builder()
                .contextSuccessful(true)
                .build();

        assertThat(result.isContextSuccessful()).isTrue();
    }

}
