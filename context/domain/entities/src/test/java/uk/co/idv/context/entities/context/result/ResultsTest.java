package uk.co.idv.context.entities.context.result;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ResultsTest {

    @Test
    void shouldBeIterable() {
        Result result1 = mock(Result.class);
        Result result2 = mock(Result.class);

        Results results = new Results(result1, result2);

        assertThat(results).containsExactly(result1, result2);
    }

    @Test
    void shouldReturnValues() {
        Result result1 = mock(Result.class);
        Result result2 = mock(Result.class);

        Results results = new Results(result1, result2);

        assertThat(results.getValues()).containsExactly(result1, result2);
    }

    @Test
    void shouldAddResult() {
        Result result1 = mock(Result.class);
        Results results = new Results(result1);
        Result result2 = mock(Result.class);

        Results updated = results.add(result2);

        assertThat(updated).containsExactly(result1, result2);
    }

}
