package uk.co.idv.context.usecases.identity.find;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.DefaultAliasMother;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityNotFoundExceptionTest {

    @Test
    void shouldReturnAlias() {
        Alias alias = DefaultAliasMother.build();

        IdentityNotFoundException error = new IdentityNotFoundException(alias);

        assertThat(error.getAliases()).containsExactly(alias);
    }

    @Test
    void shouldReturnAliases() {
        Aliases aliases = AliasesMother.idvIdAndDebitCardNumber();

        IdentityNotFoundException error = new IdentityNotFoundException(aliases);

        assertThat(error.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldReturnFormattedAliasMessage() {
        Aliases aliases = AliasesMother.idvIdAndDebitCardNumber();

        Throwable error = new IdentityNotFoundException(aliases);

        assertThat(error.getMessage()).isEqualTo(
                "idv-id|90b585c6-170f-42a6-ac7c-83d294bdab3f," +
                        "debit-card-number|4929222222222222"
        );
    }

}
