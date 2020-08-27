package uk.co.idv.context.usecases.identity.save;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.DefaultIdentity;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.save.external.ExternalSaveIdentityStrategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ExternalSaveIdentityStrategyTest {

    private final SaveIdentityStrategy strategy = new ExternalSaveIdentityStrategy();

    @Test
    void shouldAddDataFromExistingIdentity() {
        DefaultIdentity updated = mock(DefaultIdentity.class);
        DefaultIdentity existing = mock(DefaultIdentity.class);
        Identity added = mock(DefaultIdentity.class);
        given(updated.addData(existing)).willReturn(added);

        Identity saved = strategy.prepare(updated, existing);

        assertThat(saved).isEqualTo(added);
    }

}
