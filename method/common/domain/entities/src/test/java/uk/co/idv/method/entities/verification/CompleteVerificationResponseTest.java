package uk.co.idv.method.entities.verification;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.result.Result;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CompleteVerificationResponseTest {

    @Test
    void shouldReturnVerification() {
        Verification expectedVerification = mock(Verification.class);
        CompleteVerificationResponse response = CompleteVerificationResponse.builder()
                .verification(expectedVerification)
                .build();

        Verification verification = response.getVerification();

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldReturnResultFromVerification() {
        Result expectedResult = mock(Result.class);
        Verification verification = givenVerificationConvertsToResult(expectedResult);
        CompleteVerificationResponse response = CompleteVerificationResponse.builder()
                .verification(verification)
                .build();

        Result result = response.getResult();

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnIsSequenceCompletedByVerification() {
        CompleteVerificationResponse response = CompleteVerificationResponse.builder()
                .sequenceCompletedByVerification(true)
                .build();

        boolean sequenceCompleted = response.isSequenceCompletedByVerification();

        assertThat(sequenceCompleted).isTrue();
    }

    @Test
    void shouldReturnIsMethodCompletedByVerification() {
        CompleteVerificationResponse response = CompleteVerificationResponse.builder()
                .methodCompletedByVerification(true)
                .build();

        boolean methodCompleted = response.isMethodCompletedByVerification();

        assertThat(methodCompleted).isTrue();
    }

    private Verification givenVerificationConvertsToResult(Result result) {
        Verification verification = mock(Verification.class);
        given(verification.toResult()).willReturn(result);
        return verification;
    }

}
