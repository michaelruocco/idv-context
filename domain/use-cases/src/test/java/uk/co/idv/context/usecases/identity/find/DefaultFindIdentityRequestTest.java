package uk.co.idv.context.usecases.identity.find;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.usecases.identity.request.FindIdentityRequestMother;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultFindIdentityRequestTest {

    @Test
    void shouldReturnAliases() {
        Aliases aliases = AliasesMother.idvIdAndDebitCardNumber();

        FindIdentityRequest request = FindIdentityRequestMother.withAliases(aliases);

        assertThat(request.getAliases()).isEqualTo(aliases);
    }

}
