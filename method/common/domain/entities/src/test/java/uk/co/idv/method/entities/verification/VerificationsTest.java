package uk.co.idv.method.entities.verification;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

class VerificationsTest {

    @Test
    void shouldReturnCountOfVerificationsByName() {
        String methodName = "my-method";
        Verification verification = VerificationMother.withMethodName(methodName);
        Verification otherMethodVerification = VerificationMother.withMethodName("other-method");
        Verifications verifications = VerificationsMother.with(verification, otherMethodVerification);

        long countByName = verifications.countByName(methodName);

        assertThat(countByName).isEqualTo(1);
    }

    @Test
    void shouldReturnContainsSuccessfulTrueIfSuccessfulVerificationWithMethodName() {
        String methodName = "my-method";
        Verification verification = VerificationMother.builder()
                .methodName(methodName)
                .successful(true)
                .build();
        Verifications verifications = VerificationsMother.with(verification);

        boolean containsSuccessful = verifications.containsSuccessful(methodName);

        assertThat(containsSuccessful).isTrue();
    }

    @Test
    void shouldReturnContainsSuccessfulFalseIfSuccessfulVerificationWithDifferentMethodName() {
        Verification verification = VerificationMother.builder()
                .methodName("other-method")
                .successful(true)
                .build();
        Verifications verifications = VerificationsMother.with(verification);

        boolean containsSuccessful = verifications.containsSuccessful("my-method");

        assertThat(containsSuccessful).isFalse();
    }

    @Test
    void shouldReturnContainsSuccessfulFalseIfUnsuccessfulVerificationWithMethodName() {
        String methodName = "my-method";
        Verification verification = VerificationMother.builder()
                .methodName(methodName)
                .successful(false)
                .build();
        Verifications verifications = VerificationsMother.with(verification);

        boolean containsSuccessful = verifications.containsSuccessful("my-method");

        assertThat(containsSuccessful).isFalse();
    }

    @Test
    void shouldBeIterable() {
        Verification verification1 = mock(Verification.class);
        Verification verification2 = mock(Verification.class);
        Verifications verifications = VerificationsMother.with(verification1, verification2);

        assertThat(verifications).containsExactly(verification1, verification2);
    }

    @Test
    void shouldReturnValues() {
        Verification verification1 = mock(Verification.class);
        Verification verification2 = mock(Verification.class);
        Verifications verifications = VerificationsMother.with(verification1, verification2);

        Collection<Verification> values = verifications.getValues();

        assertThat(values).containsExactly(verification1, verification2);
    }

    @Test
    void shouldReturnVerificationById() {
        Verification expectedVerification = VerificationMother.successful();
        Verifications verifications = VerificationsMother.with(expectedVerification);

        Verification verification = verifications.getById(expectedVerification.getId());

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldThrowExceptionIfDoesNotContainVerificationWithId() {
        Verifications verifications = VerificationsMother.oneIncomplete();
        UUID id = UUID.fromString("de76e271-ac02-45d4-b93d-0812b4ce7566");

        Throwable error = catchThrowable(() -> verifications.getById(id));

        assertThat(error)
                .isInstanceOf(VerificationNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldAddVerification() {
        Verifications verifications = VerificationsMother.empty();
        Verification verification = VerificationMother.successful();

        Verifications updated = verifications.add(verification);

        assertThat(updated).containsExactly(verification);
    }

    @Test
    void shouldThrowExceptionIfDoesNotContainVerificationWithIdOnComplete() {
        UUID id = UUID.fromString("de76e271-ac02-45d4-b93d-0812b4ce7566");
        CompleteVerificationRequest request = CompleteVerificationRequestMother.withId(id);
        Verifications verifications = VerificationsMother.oneIncomplete();

        Throwable error = catchThrowable(() -> verifications.complete(request));

        assertThat(error)
                .isInstanceOf(VerificationNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldCompleteVerificationWithIdOnComplete() {
        Verification verification = VerificationMother.incomplete();
        CompleteVerificationRequest request = CompleteVerificationRequestMother.withId(verification.getId());
        Verifications verifications = VerificationsMother.with(verification);

        Verifications updated = verifications.complete(request);

        assertThat(updated).hasSameSizeAs(verifications);
        Verification completed = updated.getById(verification.getId());
        assertThat(completed)
                .usingRecursiveComparison()
                .ignoringFields("complete", "completed", "successful")
                .isEqualTo(verification);
        assertThat(completed.isComplete()).isTrue();
        assertThat(completed.getCompleted()).contains(request.forceGetTimestamp());
        assertThat(completed.isSuccessful()).isEqualTo(request.isSuccessful());
    }

}
