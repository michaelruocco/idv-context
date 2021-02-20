package uk.co.idv.identity.entities.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.AliasesMother;

import static org.assertj.core.api.Assertions.assertThat;

class MultipleIdentitiesFoundExceptionTest {

    private final Aliases aliases = AliasesMother.idvIdAndDebitCardNumber();
    private final Identities existing = IdentitiesMother.two();

    private final MultipleIdentitiesFoundException error = new MultipleIdentitiesFoundException(aliases, existing);

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
        assertThat(error.getIdentities()).isEqualTo(existing);
    }

}
