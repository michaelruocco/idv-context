package uk.co.idv.context.entities.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequestMother;
import uk.co.idv.method.entities.method.Methods;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.method.entities.verification.CompleteVerificationRequest;
import uk.co.idv.method.entities.verification.CompleteVerificationRequestMother;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.VerificationMother;
import uk.co.idv.method.entities.verification.Verifications;
import uk.co.idv.method.entities.verification.VerificationsMother;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ContextTest {

    @Test
    void shouldReturnId() {
        UUID id = UUID.randomUUID();

        Context context = Context.builder()
                .id(id)
                .build();

        assertThat(context.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnCreated() {
        Instant created = Instant.now();

        Context context = Context.builder()
                .created(created)
                .build();

        assertThat(context.getCreated()).isEqualTo(created);
    }

    @Test
    void shouldReturnExpiry() {
        Instant expiry = Instant.now();

        Context context = Context.builder()
                .expiry(expiry)
                .build();

        assertThat(context.getExpiry()).isEqualTo(expiry);
    }

    @Test
    void shouldReturnIdentityCreateContextRequest() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();

        Context context = Context.builder()
                .request(request)
                .build();

        assertThat(context.getRequest()).isEqualTo(request);
    }

    @Test
    void shouldReturnChannelFromCreateContextRequest() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();

        Context context = Context.builder()
                .request(request)
                .build();

        assertThat(context.getChannel()).isEqualTo(request.getChannel());
    }

    @Test
    void shouldReturnChannelIdFromCreateContextRequest() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();

        Context context = Context.builder()
                .request(request)
                .build();

        assertThat(context.getChannelId()).isEqualTo(request.getChannelId());
    }

    @Test
    void shouldReturnActivityFromCreateContextRequest() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();

        Context context = Context.builder()
                .request(request)
                .build();

        assertThat(context.getActivity()).isEqualTo(request.getActivity());
    }

    @Test
    void shouldReturnActivityNameFromCreateContextRequest() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();

        Context context = Context.builder()
                .request(request)
                .build();

        assertThat(context.getActivityName()).isEqualTo(request.getActivityName());
    }

    @Test
    void shouldReturnIdentityFromCreateContextRequest() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();

        Context context = Context.builder()
                .request(request)
                .build();

        assertThat(context.getIdentity()).isEqualTo(request.getIdentity());
    }

    @Test
    void shouldReturnIdvIdFromCreateContextRequest() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();

        Context context = Context.builder()
                .request(request)
                .build();

        assertThat(context.getIdvId()).isEqualTo(request.getIdvId());
    }

    @Test
    void shouldReturnAliasesFromCreateContextRequest() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();

        Context context = Context.builder()
                .request(request)
                .build();

        assertThat(context.getAliases()).isEqualTo(request.getAliases());
    }

    @Test
    void shouldReturnAliasTypesFromCreateContextRequest() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();

        Context context = Context.builder()
                .request(request)
                .build();

        assertThat(context.getAliasTypes()).isEqualTo(request.getAliasTypes());
    }

    @Test
    void shouldReturnIsProtectSensitiveDataFromCreateContextRequest() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();

        Context context = Context.builder()
                .request(request)
                .build();

        assertThat(context.isProtectSensitiveData()).isEqualTo(request.isProtectSensitiveData());
    }

    @Test
    void shouldReturnSequences() {
        Sequences sequences = mock(Sequences.class);

        Context context = Context.builder()
                .sequences(sequences)
                .build();

        assertThat(context.getSequences()).isEqualTo(sequences);
    }

    @Test
    void shouldReturnEligibleFromSequences() {
        Sequences sequences = mock(Sequences.class);
        given(sequences.isEligible()).willReturn(true);

        Context context = Context.builder()
                .sequences(sequences)
                .build();

        assertThat(context.isEligible()).isTrue();
    }

    @Test
    void shouldReturnCompleteFromSequences() {
        Sequences sequences = mock(Sequences.class);
        Verifications verifications = mock(Verifications.class);
        given(sequences.isComplete(verifications)).willReturn(true);

        Context context = Context.builder()
                .sequences(sequences)
                .verifications(verifications)
                .build();

        assertThat(context.isComplete()).isTrue();
    }

    @Test
    void shouldReturnSuccessfulFromSequences() {
        Sequences sequences = mock(Sequences.class);
        Verifications verifications = mock(Verifications.class);
        given(sequences.isSuccessful(verifications)).willReturn(true);

        Context context = Context.builder()
                .sequences(sequences)
                .verifications(verifications)
                .build();

        assertThat(context.isSuccessful()).isTrue();
    }

    @Test
    void shouldReturnDurationFromSequences() {
        Sequences sequences = mock(Sequences.class);
        Duration duration = Duration.ofMinutes(5);
        given(sequences.getDuration()).willReturn(duration);

        Context context = Context.builder()
                .sequences(sequences)
                .build();

        assertThat(context.getDuration()).isEqualTo(duration);
    }

    @Test
    void shouldReturnExpiredFalseIfCurrentTimeIsAfterExpiry() {
        Instant expiry = Instant.parse("2020-09-30T21:00:00.000Z");
        Context context = ContextMother.withExpiry(expiry);

        boolean expired = context.hasExpired(expiry.minusMillis(1));

        assertThat(expired).isFalse();
    }

    @Test
    void shouldReturnExpiredFalseIfCurrentTimeIsEqualToExpiry() {
        Instant expiry = Instant.parse("2020-09-30T21:00:00.000Z");
        Context context = ContextMother.withExpiry(expiry);

        boolean expired = context.hasExpired(expiry);

        assertThat(expired).isFalse();
    }

    @Test
    void shouldReturnExpiredTrueIfCurrentTimeIsAfterExpiry() {
        Instant expiry = Instant.parse("2020-09-30T21:00:00.000Z");
        Context context = ContextMother.withExpiry(expiry);

        boolean expired = context.hasExpired(expiry.plusMillis(1));

        assertThat(expired).isTrue();
    }

    @Test
    void shouldUpdateMethodsOnAllSequences() {
        UnaryOperator<Method> function = mock(UnaryOperator.class);
        Sequences sequences = mock(Sequences.class);
        Sequences updatedSequences = givenUpdatedSequences(function, sequences);
        Context context = ContextMother.withSequences(sequences);

        Context updated = context.updateMethods(function);

        assertThat(updated.getSequences()).isEqualTo(updatedSequences);
        assertThat(updated)
                .usingRecursiveComparison()
                .ignoringFields("sequences")
                .isEqualTo(context);
    }

    @Test
    void shouldReturnNextMethods() {
        Methods expectedNextMethods = mock(Methods.class);
        Verifications verifications = mock(Verifications.class);
        Sequences sequences = givenSequencesWithNextMethods(verifications, expectedNextMethods);
        Context context = ContextMother.builder()
                .sequences(sequences)
                .verifications(verifications)
                .build();

        Methods nextMethods = context.getNextMethods();

        assertThat(nextMethods).isEqualTo(expectedNextMethods);
    }

    @Test
    void shouldReturnNextMethodsMatchingName() {
        String methodName = "fake-method";
        Verifications verifications = mock(Verifications.class);
        Methods nextMethods = mock(Methods.class);
        Context context = ContextMother.builder()
                .sequences(givenSequencesWithNextMethods(verifications, nextMethods))
                .verifications(verifications)
                .build();
        Methods nextMethodsByName = mock(Methods.class);
        given(nextMethods.getByName(methodName)).willReturn(nextMethodsByName);

        Methods methods = context.getNextMethods(methodName);

        assertThat(methods).isEqualTo(nextMethodsByName);
    }

    @Test
    void shouldReturnHasMoreCompletedSequencesTrueIfUpdatedHasMoreCompletedSequencesThanOriginal() {
        Verifications verifications = mock(Verifications.class);
        Context original = ContextMother.builder()
                .sequences(givenSequencesWithCompletedCount(verifications, 1))
                .verifications(verifications)
                .build();
        Context updated = ContextMother.builder()
                .sequences(givenSequencesWithCompletedCount(verifications, 2))
                .verifications(verifications)
                .build();

        boolean hasMoreCompletedSequences = updated.hasMoreCompletedSequencesThan(original);

        assertThat(hasMoreCompletedSequences).isTrue();
    }

    @Test
    void shouldReturnHasMoreCompletedSequencesFalseIfUpdatedDoesNotHaveMoreCompletedSequencesThanOriginal() {
        Verifications verifications = mock(Verifications.class);
        Context original = ContextMother.withSequences(givenSequencesWithCompletedCount(verifications, 1));
        Context updated = ContextMother.withSequences(givenSequencesWithCompletedCount(verifications, 1));

        boolean hasMoreCompletedSequences = updated.hasMoreCompletedSequencesThan(original);

        assertThat(hasMoreCompletedSequences).isFalse();
    }

    @Test
    void shouldReturnHasMoreCompletedMethodsTrueIfUpdatedHasMoreCompletedMethodsThanOriginal() {
        Verifications verifications = mock(Verifications.class);
        Context original = ContextMother.builder()
                .sequences(givenSequencesWithCompletedMethodCount(verifications, 1))
                .verifications(verifications)
                .build();
        Context updated = ContextMother.builder()
                .sequences(givenSequencesWithCompletedMethodCount(verifications, 2))
                .verifications(verifications)
                .build();

        boolean hasMoreCompletedMethods = updated.hasMoreCompletedMethodsThan(original);

        assertThat(hasMoreCompletedMethods).isTrue();
    }

    @Test
    void shouldReturnHasMoreCompletedMethodsFalseIfUpdatedDoesNotHaveMoreCompletedMethodsThanOriginal() {
        Verifications verifications = mock(Verifications.class);
        Context original = ContextMother.withSequences(givenSequencesWithCompletedMethodCount(verifications, 1));
        Context updated = ContextMother.withSequences(givenSequencesWithCompletedMethodCount(verifications, 1));

        boolean hasMoreCompletedMethods = updated.hasMoreCompletedMethodsThan(original);

        assertThat(hasMoreCompletedMethods).isFalse();
    }

    @Test
    void shouldReturnContextWithUpdatedVerifications() {
        Verifications verifications = mock(Verifications.class);
        Context original = ContextMother.build();

        Context updated = original.withVerifications(verifications);

        assertThat(updated)
                .usingRecursiveComparison()
                .ignoringFields("verifications")
                .isEqualTo(original);
        assertThat(updated.getVerifications()).isEqualTo(verifications);
    }

    @Test
    void shouldAddVerificationToContextIfMatchesNextMethod() {
        Verification verification = VerificationMother.successful();
        Context original = ContextMother.build();

        Context updated = original.add(verification);

        assertThat(updated)
                .usingRecursiveComparison()
                .ignoringFields("verifications")
                .isEqualTo(original);
        assertThat(updated.getVerifications()).isEqualTo(original.getVerifications().add(verification));
    }

    @Test
    void shouldThrowExceptionIfAddedVerificationDoesNotMatchNextMethod() {
        String methodName = "other-method";
        Verification verification = VerificationMother.withMethodName(methodName);
        Context original = ContextMother.build();

        Throwable error = catchThrowable(() -> original.add(verification));

        assertThat(error)
                .isInstanceOf(NotNextMethodException.class)
                .hasMessage(methodName);
    }

    @Test
    void shouldReturnVerificationById() {
        Verification expectedVerification = VerificationMother.successful();
        Context context = ContextMother.withVerifications(VerificationsMother.with(expectedVerification));

        Verification verification = context.getVerification(expectedVerification.getId());

        assertThat(verification).isEqualTo(expectedVerification);
    }

    @Test
    void shouldReturnUpdatedContextWithCompletedVerification() {
        Verification incomplete = VerificationMother.incomplete();
        Context original = ContextMother.withVerifications(VerificationsMother.with(incomplete));
        CompleteVerificationRequest request = CompleteVerificationRequestMother.builder()
                .contextId(original.getId())
                .id(incomplete.getId())
                .successful(true)
                .timestamp(Instant.parse("2020-09-14T20:08:02.003Z"))
                .build();

        Context updatedContext = original.completeVerification(request);

        assertThat(updatedContext)
                .usingRecursiveComparison()
                .ignoringFields("verifications")
                .isEqualTo(original);
        Verification updatedVerification = updatedContext.getVerification(incomplete.getId());
        assertThat(updatedVerification)
                .usingRecursiveComparison()
                .ignoringFields("completed", "successful")
                .isEqualTo(incomplete);
        assertThat(updatedVerification.getCompleted()).contains(request.forceGetTimestamp());
        assertThat(updatedVerification.isSuccessful()).isTrue();
    }

    static Sequences givenUpdatedSequences(UnaryOperator<Method> function, Sequences sequences) {
        Sequences updated = mock(Sequences.class);
        given(sequences.updateMethods(function)).willReturn(updated);
        return updated;
    }

    static Sequences givenSequencesWithCompletedCount(MethodVerifications verifications, long count) {
        Sequences sequences = mock(Sequences.class);
        given(sequences.completedSequenceCount(verifications)).willReturn(count);
        return sequences;
    }

    static Sequences givenSequencesWithCompletedMethodCount(MethodVerifications verifications, long count) {
        Sequences sequences = mock(Sequences.class);
        given(sequences.completedMethodCount(verifications)).willReturn(count);
        return sequences;
    }

    static Sequences givenSequencesWithNextMethods(MethodVerifications verifications, Methods methods) {
        Sequences sequences = mock(Sequences.class);
        given(sequences.getNextMethods(verifications)).willReturn(methods);
        return sequences;
    }

}
