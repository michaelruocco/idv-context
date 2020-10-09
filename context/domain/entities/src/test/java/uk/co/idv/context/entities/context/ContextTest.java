package uk.co.idv.context.entities.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequestMother;
import uk.co.idv.context.entities.context.sequence.Sequence;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.context.entities.context.sequence.SequencesMother;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.sequence.MethodSequence;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
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
    void shouldReturnTrueIfAnySequencesMatchPredicateQuery() {
        Predicate<MethodSequence> query = mock(Predicate.class);
        Sequence sequence1 = mock(Sequence.class);
        Sequence sequence2 = mock(Sequence.class);
        given(query.test(sequence1)).willReturn(false);
        given(query.test(sequence2)).willReturn(true);
        Context context = ContextMother.withSequences(SequencesMother.with(sequence1, sequence2));

        boolean result = context.query(query);

        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseIfNoSequencesMatchPredicateQuery() {
        Predicate<MethodSequence> query = mock(Predicate.class);
        Sequence sequence1 = mock(Sequence.class);
        Sequence sequence2 = mock(Sequence.class);
        given(query.test(sequence1)).willReturn(false);
        given(query.test(sequence2)).willReturn(false);
        Context context = ContextMother.withSequences(SequencesMother.with(sequence1, sequence2));

        boolean result = context.query(query);

        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnResultsFromQueryFunction() {
        Function<MethodSequence, Optional<Method>> query = mock(Function.class);
        Sequence sequence1 = mock(Sequence.class);
        Sequence sequence2 = mock(Sequence.class);
        Method method1 = mock(Method.class);
        Method method2 = mock(Method.class);
        given(query.apply(sequence1)).willReturn(Optional.of(method1));
        given(query.apply(sequence2)).willReturn(Optional.of(method2));
        Context context = ContextMother.withSequences(SequencesMother.with(sequence1, sequence2));

        Stream<Method> result = context.query(query);

        assertThat(result).containsExactly(method1, method2);
    }

    private Sequences givenUpdatedSequences(UnaryOperator<Method> function, Sequences sequences) {
        Sequences updated = mock(Sequences.class);
        given(sequences.updateMethods(function)).willReturn(updated);
        return updated;
    }

}
