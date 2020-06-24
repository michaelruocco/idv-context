package uk.co.idv.context.usecases.identity.find;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.identity.Identities;
import uk.co.idv.context.entities.identity.IdentitiesMother;

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
