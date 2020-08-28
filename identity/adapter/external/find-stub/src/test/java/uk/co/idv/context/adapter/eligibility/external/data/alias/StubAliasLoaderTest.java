package uk.co.idv.context.adapter.eligibility.external.data.alias;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.CreditCardNumberMother;
import uk.co.idv.context.entities.alias.DebitCardNumberMother;
import uk.co.idv.context.entities.alias.DefaultAliases;
import uk.co.idv.context.entities.eligibility.CreateEligibilityRequestMother;
import uk.co.idv.context.entities.identity.FindIdentityRequest;
import uk.co.idv.context.usecases.eligibility.external.data.AliasLoader;

import static org.assertj.core.api.Assertions.assertThat;

class StubAliasLoaderTest {

    private final AliasLoader loader = new StubAliasLoader();

    @Test
    void shouldReturnInputAliasesIfAllAliasValuesDoNotEndIn9AndDoesNotContainCreditCardNumber() {
        DefaultAliases inputAliases = AliasesMother.with(DebitCardNumberMother.debitCardNumber());
        FindIdentityRequest request = CreateEligibilityRequestMother.withAliases(inputAliases);

        Aliases loadedAliases = loader.load(request);

        assertThat(loadedAliases).isEqualTo(inputAliases);
    }

    @Test
    void shouldReturnAdditionalDebitCardAliasIfAllAliasValuesDoNotEndIn9AndContainsCreditCardNumber() {
        DefaultAliases inputAliases = AliasesMother.with(CreditCardNumberMother.withValue("4929111111111111"));
        FindIdentityRequest request = CreateEligibilityRequestMother.withAliases(inputAliases);

        Aliases loadedAliases = loader.load(request);

        assertThat(loadedAliases)
                .hasSize(inputAliases.size() + 1)
                .containsAll(inputAliases)
                .contains(DebitCardNumberMother.withValue("5929111111111111"));
    }

    @Test
    void shouldReturnEmptyAliasesIfAnyAliasValuesEndIn9() {
        FindIdentityRequest request = CreateEligibilityRequestMother.withAliases(
                AliasesMother.with(CreditCardNumberMother.withValueEndingIn9())
        );

        Aliases aliases = loader.load(request);

        assertThat(aliases).isEqualTo(AliasesMother.empty());
    }

}
