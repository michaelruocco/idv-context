package uk.co.idv.identity.usecases.identity.save;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.usecases.identity.IdentityRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class DefaultSaveIdentityTest {

    private final SaveIdentityStrategy strategy = mock(SaveIdentityStrategy.class);
    private final IdentityRepository repository = mock(IdentityRepository.class);

    private final SaveIdentity saveIdentity = new DefaultSaveIdentity(repository, strategy);

    @Test
    void shouldNotUpdateIdentityIfIdentityIsUnchanged() {
        Identity update = IdentityMother.example1();
        Identity existing = IdentityMother.example();
        Identity expectedSaved = IdentityMother.example();
        given(strategy.prepare(update, existing)).willReturn(expectedSaved);

        Identity saved = saveIdentity.save(update, existing);

        assertThat(saved).isEqualTo(expectedSaved);
        verify(repository, never()).update(any(Identity.class), any(Identity.class));
    }

    @Test
    void shouldUpdateIdentityIfIdentityIsChanged() {
        Identity update = IdentityMother.example();
        Identity existing = IdentityMother.example1();
        Identity expectedSaved = IdentityMother.example();
        given(strategy.prepare(update, existing)).willReturn(expectedSaved);

        Identity saved = saveIdentity.save(update, existing);

        assertThat(saved).isEqualTo(expectedSaved);
        verify(repository).update(expectedSaved, existing);
    }

}
