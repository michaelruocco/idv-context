package uk.co.idv.context.adapter.identity.find.external.data.alias;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.CreditCardNumberMother;
import uk.co.idv.context.usecases.identity.find.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.request.ExternalFindIdentityRequestMother;
import uk.co.idv.context.usecases.identity.find.data.AliasLoader;

import static org.assertj.core.api.Assertions.assertThat;

class StubAliasLoaderTest {

    private final AliasLoader loader = new StubAliasLoader();

    @Test
    void shouldReturnPopulatedAliasesIfAllAliasValuesDoNotEndIn9() {
        FindIdentityRequest request = ExternalFindIdentityRequestMother.build();

        Aliases aliases = loader.load(request);

        assertThat(aliases).isEqualTo(AliasesMother.idvIdAndCreditCardNumber());
    }

    @Test
    void shouldReturnPopulatedAliasesIfAnyAliasValuesEndIn9() {
        FindIdentityRequest request = ExternalFindIdentityRequestMother.withAliases(
                AliasesMother.with(CreditCardNumberMother.withValueEndingIn9())
        );

        Aliases aliases = loader.load(request);

        assertThat(aliases).isEqualTo(AliasesMother.empty());
    }

}
