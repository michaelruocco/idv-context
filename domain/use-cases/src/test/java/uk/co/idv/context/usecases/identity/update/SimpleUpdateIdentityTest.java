package uk.co.idv.context.usecases.identity.update;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.IdentityRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SimpleUpdateIdentityTest {

    private final IdentityRepository repository = mock(IdentityRepository.class);

    private final UpdateIdentity update = new SimpleUpdateIdentity(repository);

    @Test
    void shouldSaveIdentity() {
        Identity identity = IdentityMother.example();

        update.update(identity);

        verify(repository).save(identity);
    }

    @Test
    void shouldReturnIdentity() {
        Identity identity = IdentityMother.example();

        Identity updatedIdentity = update.update(identity);

        assertThat(identity).isEqualTo(updatedIdentity);
    }

}
