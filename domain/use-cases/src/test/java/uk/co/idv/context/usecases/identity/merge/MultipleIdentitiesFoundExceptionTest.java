package uk.co.idv.context.usecases.identity.merge;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class MultipleIdentitiesFoundExceptionTest {

    private final Identity identity = IdentityMother.withoutIdvId();
    private final Collection<Identity> existingIdentities = Arrays.asList(
            IdentityMother.example(),
            IdentityMother.example1()
    );

    private final MultipleIdentitiesFoundException error = new MultipleIdentitiesFoundException(identity, existingIdentities);

    @Test
    void shouldReturnMessage() {
        assertThat(error.getMessage()).isEqualTo("multiple identities found");
    }

    @Test
    void shouldReturnIdentity() {
        assertThat(error.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldReturnExistingIdentities() {
        assertThat(error.getExistingIdentities()).isEqualTo(existingIdentities);
    }

}
