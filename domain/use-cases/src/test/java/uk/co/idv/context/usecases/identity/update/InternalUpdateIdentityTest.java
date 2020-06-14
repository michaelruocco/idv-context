package uk.co.idv.context.usecases.identity.update;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.IdentityRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class InternalUpdateIdentityTest {

    private final IdentityRepository repository = mock(IdentityRepository.class);

    private final UpdateIdentity update = InternalUpdateIdentity.builder()
            .repository(repository)
            .build();

    @Test
    void shouldSaveIdentity() {
        Identity identity = IdentityMother.example();

        Identity updated = update.update(identity);

        verify(repository).save(updated);
    }

    @Test
    void shouldReturnIdentity() {
        Identity identity = IdentityMother.example();

        Identity updated = update.update(identity);

        assertThat(updated).isEqualTo(identity);
    }

}
