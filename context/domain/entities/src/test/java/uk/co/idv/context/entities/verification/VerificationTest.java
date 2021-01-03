package uk.co.idv.context.entities.verification;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.method.entities.result.Result;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.mock;

class VerificationTest {

    @Test
    void shouldReturnId() {
        UUID id = UUID.randomUUID();

        Verification verification = Verification.builder()
                .id(id)
                .build();

        assertThat(verification.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnContextId() {
        UUID contextId = UUID.randomUUID();

        Verification verification = Verification.builder()
                .contextId(contextId)
                .build();

        assertThat(verification.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnActivity() {
        Activity activity = mock(Activity.class);

        Verification verification = Verification.builder()
                .activity(activity)
                .build();

        assertThat(verification.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldReturnMethods() {
        Methods methods = mock(Methods.class);

        Verification verification = Verification.builder()
                .methods(methods)
                .build();

        assertThat(verification.getMethods()).isEqualTo(methods);
    }

    @Test
    void shouldReturnProtectSensitiveData() {
        Verification verification = Verification.builder()
                .protectSensitiveData(true)
                .build();

        assertThat(verification.isProtectSensitiveData()).isTrue();
    }

    @Test
    void shouldNotBeCompletedByDefault() {
        Verification verification = Verification.builder()
                .build();

        assertThat(verification.getCompleted()).isEmpty();
        assertThat(verification.isComplete()).isFalse();
    }

    @Test
    void shouldNotBeSuccessfulByDefault() {
        Verification verification = Verification.builder()
                .build();

        assertThat(verification.isSuccessful()).isFalse();
    }

    @Test
    void shouldCompleteVerification() {
        Verification verification = VerificationMother.incomplete();

        CompleteVerificationRequest request = CompleteVerificationRequestMother.successful();

        Verification completed = verification.complete(request);

        assertThat(completed)
                .usingRecursiveComparison()
                .ignoringFields("complete", "completed", "successful")
                .isEqualTo(verification);
        assertThat(completed.isComplete()).isTrue();
        assertThat(completed.getCompleted()).contains(request.forceGetTimestamp());
    }

    @Test
    void shouldThrowExceptionIfAttemptToCompleteVerificationAfterExpiry() {
        Verification verification = VerificationMother.incomplete();

        CompleteVerificationRequest request = CompleteVerificationRequestMother.builder()
                .timestamp(verification.getExpiry().plusMillis(1))
                .build();

        VerificationExpiredException error = catchThrowableOfType(
                () -> verification.complete(request),
                VerificationExpiredException.class
        );

        assertThat(error.getId()).isEqualTo(verification.getId());
        assertThat(error.getExpiry()).isEqualTo(verification.getExpiry());
    }

    @Test
    void shouldReturnTrueIfVerificationHasMethodName() {
        Verification verification = VerificationMother.incomplete();

        boolean hasName = verification.hasMethodName(verification.getMethodName());

        assertThat(hasName).isTrue();
    }

    @Test
    void shouldReturnFalseIfVerificationDoesNotHaveMethodName() {
        Verification verification = VerificationMother.incomplete();

        boolean hasName = verification.hasMethodName("other-name");

        assertThat(hasName).isFalse();
    }

    @Test
    void shouldReturnTrueIfHasId() {
        Verification verification = VerificationMother.incomplete();

        boolean hasName = verification.hasId(verification.getId());

        assertThat(hasName).isTrue();
    }

    @Test
    void shouldReturnFalseIfDoesNotHaveId() {
        Verification verification = VerificationMother.incomplete();

        boolean hasName = verification.hasId(UUID.fromString("bc446eb5-608e-4b8e-9859-c4715442fd3d"));

        assertThat(hasName).isFalse();
    }

    @Test
    void shouldReturnEmptyCompletedIfVerificationIsIncomplete() {
        Verification verification = VerificationMother.incomplete();

        Optional<Instant> completed = verification.getCompleted();

        assertThat(completed).isEmpty();
    }

    @Test
    void shouldThrowExceptionOnForceGetCompletedIfVerificationIsIncomplete() {
        Verification verification = VerificationMother.incomplete();

        Throwable error = catchThrowable(verification::forceGetCompleted);

        assertThat(error)
                .isInstanceOf(VerificationNotCompletedException.class)
                .hasMessage(verification.getId().toString());
    }

    @Test
    void shouldReturnCompletedIfVerificationIsComplete() {
        Instant expectedCompleted = Instant.parse("2020-09-14T20:04:03.003Z");
        Verification verification = VerificationMother.successful(expectedCompleted);

        Optional<Instant> completed = verification.getCompleted();

        assertThat(completed).contains(expectedCompleted);
    }

    @Test
    void shouldReturnCompletedOnForceGetCompletedIfVerificationIsComplete() {
        Instant expectedCompleted = Instant.parse("2020-09-14T20:04:03.003Z");
        Verification verification = VerificationMother.successful(expectedCompleted);

        Instant completed = verification.forceGetCompleted();

        assertThat(completed).isEqualTo(expectedCompleted);
    }

    @Test
    void shouldConvertVerificationToResult() {
        Verification verification = VerificationMother.successful();

        Result result = verification.toResult();

        assertThat(result.getVerificationId()).isEqualTo(verification.getId());
        assertThat(result.getMethodName()).isEqualTo(verification.getMethodName());
        assertThat(result.getTimestamp()).isEqualTo(verification.forceGetCompleted());
        assertThat(result.isSuccessful()).isEqualTo(verification.isSuccessful());
    }

    @Test
    void shouldThrowExceptionOnToResultIfVerificationNotComplete() {
        Verification verification = VerificationMother.incomplete();

        Throwable error = catchThrowable(verification::toResult);

        assertThat(error)
                .isInstanceOf(VerificationNotCompletedException.class)
                .hasMessage(verification.getId().toString());
    }

}
