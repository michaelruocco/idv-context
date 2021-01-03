package uk.co.idv.context.entities.verification;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.method.entities.result.Result;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CompleteVerificationResponseTest {

    @Test
    void shouldReturnOriginalContext() {
        Context expected = mock(Context.class);
        CompleteVerificationResponse response = CompleteVerificationResponse.builder()
                .original(expected)
                .build();

        Context original = response.getOriginal();

        assertThat(original).isEqualTo(expected);
    }

    @Test
    void shouldReturnUpdatedContext() {
        Context expected = mock(Context.class);
        CompleteVerificationResponse response = CompleteVerificationResponse.builder()
                .updated(expected)
                .build();

        Context updated = response.getUpdated();

        assertThat(updated).isEqualTo(expected);
    }

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
    void shouldReturnIsSequenceCompletedByVerificationIfCompletedContextHasMoreCompletedSequencesThanOriginal() {
        Context updated = mock(Context.class);
        Context original = mock(Context.class);
        given(updated.hasMoreCompletedSequencesThan(original)).willReturn(true);
        CompleteVerificationResponse response = CompleteVerificationResponse.builder()
                .original(original)
                .updated(updated)
                .build();

        boolean sequenceCompleted = response.isSequenceCompletedByVerification();

        assertThat(sequenceCompleted).isTrue();
    }

    @Test
    void shouldReturnIsMethodCompletedByVerificationIfCompletedContextHasMoreCompletedMethodsThanOriginal() {
        Context updated = mock(Context.class);
        Context original = mock(Context.class);
        given(updated.hasMoreCompletedMethodsThan(original)).willReturn(true);
        CompleteVerificationResponse response = CompleteVerificationResponse.builder()
                .original(original)
                .updated(updated)
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
