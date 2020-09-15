package uk.co.idv.context.adapter.repository;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.context.usecases.context.ContextRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryContextRepositoryTest {

    private final ContextRepository repository = new InMemoryContextRepository();

    @Test
    void shouldEmptyOptionalIfNoContextFoundById() {
        UUID id = UUID.randomUUID();

        Optional<Context> loaded = repository.load(id);

        assertThat(loaded).isEmpty();
    }

    @Test
    void shouldReturnSavedContextById() {
        Context expected = ContextMother.build();
        repository.save(expected);

        Optional<Context> loaded = repository.load(expected.getId());

        assertThat(loaded).contains(expected);
    }

}
