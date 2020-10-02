package uk.co.idv.context.entities.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequestMother;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.sequence.Sequences;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

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
    void shouldReturnActivityFromCreateContextRequest() {
        ServiceCreateContextRequest request = ServiceCreateContextRequestMother.build();

        Context context = Context.builder()
                .request(request)
                .build();

        assertThat(context.getActivity()).isEqualTo(request.getActivity());
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
        given(sequences.isComplete()).willReturn(true);

        Context context = Context.builder()
                .sequences(sequences)
                .build();

        assertThat(context.isComplete()).isTrue();
    }

    @Test
    void shouldReturnSuccessfulFromSequences() {
        Sequences sequences = mock(Sequences.class);
        given(sequences.isSuccessful()).willReturn(true);

        Context context = Context.builder()
                .sequences(sequences)
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
    void shouldReturnNextMethodsMatchingName() {
        String methodName = "method-name";
        Methods expected = mock(Methods.class);
        Sequences sequences = givenSequencesWithNextMethods(methodName, expected);
        Context context = ContextMother.withSequences(sequences);

        Methods methods = context.getNextMethods(methodName);

        assertThat(methods).isEqualTo(expected);
    }

    @Test
    void shouldThrowExceptionIfMethodIsNotNextMethodInAnySequences() {
        String methodName = "method-name";
        Methods methods = mock(Methods.class);
        given(methods.isEmpty()).willReturn(true);
        Sequences sequences = givenSequencesWithNextMethods(methodName, methods);
        Context context = ContextMother.withSequences(sequences);

        Throwable error = catchThrowable(() -> context.getNextEligibleIncompleteMethods(methodName));

        assertThat(error)
                .isInstanceOf(NotNextMethodInSequenceException.class)
                .hasMessage(methodName);
    }

    @Test
    void shouldThrowExceptionIfAllNextMethodsAreIneligible() {
        String methodName = "method-name";
        Methods methods = mock(Methods.class);
        given(methods.isEmpty()).willReturn(false);
        given(methods.isEligible()).willReturn(false);
        Sequences sequences = givenSequencesWithNextMethods(methodName, methods);
        Context context = ContextMother.withSequences(sequences);

        Throwable error = catchThrowable(() -> context.getNextEligibleIncompleteMethods(methodName));

        assertThat(error)
                .isInstanceOf(MethodNotEligibleException.class)
                .hasMessage(methodName);
    }

    @Test
    void shouldThrowExceptionIfAllNextMethodsAreComplete() {
        String methodName = "method-name";
        Methods methods = mock(Methods.class);
        given(methods.isEmpty()).willReturn(false);
        given(methods.isEligible()).willReturn(true);
        given(methods.isComplete()).willReturn(true);
        Sequences sequences = givenSequencesWithNextMethods(methodName, methods);
        Context context = ContextMother.withSequences(sequences);

        Throwable error = catchThrowable(() -> context.getNextEligibleIncompleteMethods(methodName));

        assertThat(error)
                .isInstanceOf(MethodAlreadyCompleteException.class)
                .hasMessage(methodName);
    }

    @Test
    void shouldReturnNextMethodsMatchingNameThatAreEligibleAndIncomplete() {
        String methodName = "method-name";
        Methods methods = mock(Methods.class);
        given(methods.isEmpty()).willReturn(false);
        given(methods.isEligible()).willReturn(true);
        given(methods.isComplete()).willReturn(false);
        Methods expectedMethods = mock(Methods.class);
        given(methods.getEligibleIncomplete()).willReturn(expectedMethods);
        Sequences sequences = givenSequencesWithNextMethods(methodName, methods);
        Context context = ContextMother.withSequences(sequences);

        Methods nextEligibleIncomplete = context.getNextEligibleIncompleteMethods(methodName);

        assertThat(nextEligibleIncomplete).isEqualTo(expectedMethods);
    }

    private Sequences givenSequencesWithNextMethods(String name, Methods methods) {
        Sequences sequences = mock(Sequences.class);
        given(sequences.getMethodsIfNext(name)).willReturn(methods);
        return sequences;
    }

}
