package uk.co.idv.context.usecases.context;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ContextFinderTest {

    @Test
    void shouldReturnContextFromApply() {
        UUID id = UUID.randomUUID();
        Context expectedContext = ContextMother.build();
        ContextFinder finder = new FakeContextFinder(expectedContext);

        Context context = finder.apply(id);

        assertThat(context).isEqualTo(expectedContext);
    }

    @RequiredArgsConstructor
    private static class FakeContextFinder implements ContextFinder {

        private final Context context;

        @Override
        public Context find(UUID id) {
            return context;
        }

    }

}
