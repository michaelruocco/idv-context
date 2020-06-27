package uk.co.idv.context.usecases.identity.save;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ExternalSaveIdentityTest {

    private final IdentityRepository repository = mock(IdentityRepository.class);

    private final SaveIdentity save = new ExternalSaveIdentity(repository);

    @Test
    void shouldAddDataFromExistingIdentityBeforeSaving() {
        Identity identity = mock(Identity.class);
        Identity existing = mock(Identity.class);
        Identity added = mock(Identity.class);
        given(identity.addData(existing)).willReturn(added);

        Identity saved = save.save(identity, existing);

        assertThat(saved).isEqualTo(added);
        verify(repository).save(added);
    }

}
