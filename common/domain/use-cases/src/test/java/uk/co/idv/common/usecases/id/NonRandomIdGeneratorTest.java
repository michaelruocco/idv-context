package uk.co.idv.common.usecases.id;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class NonRandomIdGeneratorTest {

    private final IdGenerator generator = new NonRandomIdGenerator();

    @Test
    void shouldReturnIdsInPredictableWay() {
        IntStream.rangeClosed(0, 1).forEach(i -> {
            assertThat(generator.generate()).isEqualTo(UUID.fromString("76c9ec3b-b7aa-41ae-8066-796856e71e65"));
            assertThat(generator.generate()).isEqualTo(UUID.fromString("85bbb05a-3cf8-45e5-bae8-430503164c3b"));
            assertThat(generator.generate()).isEqualTo(UUID.fromString("0ec36d6b-145b-4c1e-9201-1f47a8eec9a5"));
            assertThat(generator.generate()).isEqualTo(UUID.fromString("446846e6-bf16-4da5-af5b-9ad4a240fe5d"));
            assertThat(generator.generate()).isEqualTo(UUID.fromString("507cc493-6998-49a4-9614-38ba4296eab6"));
        });
    }

}
