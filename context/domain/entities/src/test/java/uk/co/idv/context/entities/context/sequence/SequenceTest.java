package uk.co.idv.context.entities.context.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Methods;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class SequenceTest {

    @Test
    void shouldReturnName() {
        String name = "my-name";

        Sequence sequence = Sequence.builder()
                .name(name)
                .build();

        assertThat(sequence.getName()).isEqualTo(name);
    }

    @Test
    void shouldReturnMethods() {
        Methods methods = mock(Methods.class);

        Sequence sequence = Sequence.builder()
                .methods(methods)
                .build();

        assertThat(sequence.getMethods()).isEqualTo(methods);
    }

}
