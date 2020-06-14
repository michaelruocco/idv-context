package uk.co.idv.context.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class MultipleIdentitiesFoundExceptionTest {

    private final Aliases aliases = AliasesMother.idvIdAndDebitCardNumber();
    private final Collection<Identity> existingIdentities = Arrays.asList(
            IdentityMother.example(),
            IdentityMother.example1()
    );

    private final MultipleIdentitiesFoundException error = new MultipleIdentitiesFoundException(aliases, existingIdentities);

    @Test
    void shouldReturnMessage() {
        assertThat(error.getMessage()).isEqualTo("multiple identities found");
    }

    @Test
    void shouldReturnAliases() {
        assertThat(error.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldReturnExistingIdentities() {
        assertThat(error.getExistingIdentities()).isEqualTo(existingIdentities);
    }

}
