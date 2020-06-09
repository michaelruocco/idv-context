package uk.co.idv.context.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.CreditCardNumberMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.data.AliasLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ExternalIdentityLoaderTest {

    private final AliasLoader aliasLoader = mock(AliasLoader.class);

    private final ExternalIdentityLoader identityLoader = ExternalIdentityLoader.builder()
            .aliasLoader(aliasLoader)
            .build();

    @Test
    void shouldReturnIdentityWithProvidedAliasAndLoadedAliases() {
        LoadIdentityRequest request = LoadIdentityRequestMother.build();
        Aliases loadedAliases = AliasesMother.with(CreditCardNumberMother.creditCardNumber());
        given(aliasLoader.load(request)).willReturn(loadedAliases);

        Identity identity = identityLoader.load(request);

        Aliases expectedAliases = loadedAliases.add(request.getProvidedAlias());
        assertThat(identity.getAliases()).containsExactlyElementsOf(expectedAliases);
    }

}
