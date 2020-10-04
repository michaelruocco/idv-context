package uk.co.idv.method.entities.result;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResultsTest {

    @Test
    void shouldBeIterable() {
        Result result1 = ResultMother.build();
        Result result2 = ResultMother.build();

        Results results = new Results(result1, result2);

        assertThat(results).containsExactly(result1, result2);
    }

    @Test
    void shouldReturnValues() {
        Result result1 = ResultMother.build();
        Result result2 = ResultMother.build();

        Results results = new Results(result1, result2);

        assertThat(results.getValues()).containsExactly(result1, result2);
    }

    @Test
    void shouldAddResult() {
        Result result1 = ResultMother.build();
        Results results = new Results(result1);
        Result result2 = ResultMother.build();

        Results updated = results.add(result2);

        assertThat(updated).containsExactly(result1, result2);
    }

    @Test
    void shouldReturnTrueIfContainsSuccessful() {
        Result result = ResultMother.successful();
        Results results = new Results(result);

        boolean successful = results.containsSuccessful();

        assertThat(successful).isTrue();
    }

    @Test
    void shouldReturnFalseIfDoesNotContainSuccessful() {
        Result result = ResultMother.unsuccessful();
        Results results = new Results(result);

        boolean successful = results.containsSuccessful();

        assertThat(successful).isFalse();
    }

    @Test
    void shouldReturnSize() {
        Result result1 = ResultMother.build();
        Result result2 = ResultMother.build();
        Results results = new Results(result1, result2);

        int size = results.size();

        assertThat(size).isEqualTo(2);
    }

}
