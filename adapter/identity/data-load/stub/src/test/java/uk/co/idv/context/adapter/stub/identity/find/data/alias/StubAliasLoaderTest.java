package uk.co.idv.context.adapter.stub.identity.find.data.alias;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.CreditCardNumberMother;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.FindIdentityRequestMother;
import uk.co.idv.context.usecases.identity.find.data.AliasLoader;

import static org.assertj.core.api.Assertions.assertThat;

class StubAliasLoaderTest {

    private final AliasLoader loader = new StubAliasLoader();

    @Test
    void shouldReturnPopulatedAliasesIfAllAliasValuesDoNotEndIn9() {
        FindIdentityRequest request = FindIdentityRequestMother.build();

        Aliases aliases = loader.load(request);

        assertThat(aliases).isEqualTo(AliasesMother.idvIdAndCreditCardNumber());
    }

    @Test
    void shouldReturnPopulatedAliasesIfAnyAliasValuesEndIn9() {
        FindIdentityRequest request = FindIdentityRequestMother.withAliases(
                AliasesMother.with(CreditCardNumberMother.withValueEndingIn9())
        );

        Aliases aliases = loader.load(request);

        assertThat(aliases).isEqualTo(AliasesMother.empty());
    }

}
