package uk.co.idv.context.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.DefaultAliasMother;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityNotFoundExceptionTest {

    @Test
    void shouldReturnAlias() {
        Alias alias = DefaultAliasMother.build();

        IdentityNotFoundException error = new IdentityNotFoundException(alias);

        assertThat(error.getAlias()).isEqualTo(alias);
    }

    @Test
    void shouldReturnFormattedAliasMessage() {
        Alias alias = DefaultAliasMother.build();

        Throwable error = new IdentityNotFoundException(alias);

        assertThat(error.getMessage()).isEqualTo(alias.format());
    }

}
