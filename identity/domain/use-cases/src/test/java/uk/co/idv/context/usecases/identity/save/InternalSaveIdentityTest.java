package uk.co.idv.context.usecases.identity.save;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.IdentityRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class InternalSaveIdentityTest {

    private final IdentityRepository repository = mock(IdentityRepository.class);

    private final SaveIdentity save = new InternalSaveIdentity(repository);

    @Test
    void shouldSaveIdentityIgnoringExisting() {
        Identity identity = IdentityMother.example();
        Identity existing = IdentityMother.example1();

        Identity saved = save.save(identity, existing);

        assertThat(saved).isEqualTo(identity);
        verify(repository).save(identity);
    }

}
