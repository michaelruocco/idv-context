package uk.co.idv.context.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.AliasFactory;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.DefaultAliasMother;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.find.FindIdentity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class IdentityFacadeTest {

    private final UpdateIdentity update = mock(UpdateIdentity.class);
    private final FindIdentity find = mock(FindIdentity.class);
    private final AliasFactory aliasFactory = mock(AliasFactory.class);

    private final IdentityFacade facade = IdentityFacade.builder()
            .update(update)
            .find(find)
            .aliasFactory(aliasFactory)
            .build();

    @Test
    void shouldUpdateIdentity() {
        Identity input = IdentityMother.example();
        Identity expected = IdentityMother.example1();
        given(update.update(input)).willReturn(expected);

        Identity identity = facade.update(input);

        assertThat(identity).isEqualTo(expected);
    }

    @Test
    void shouldFindIdentityByAliases() {
        Aliases aliases = AliasesMother.idvIdAndDebitCardNumber();
        Identity expected = IdentityMother.example1();
        given(find.find(aliases)).willReturn(expected);

        Identity identity = facade.find(aliases);

        assertThat(identity).isEqualTo(expected);
    }

    @Test
    void shouldFindIdentityByAliasTypeAndValue() {
        String type = "my-alias-type";
        String value = "my-alias-value";
        Alias alias = givenAliasCreatedForTypeAndValue(type, value);
        Identity expectedIdentity = givenIdentityFoundForAlias(alias);

        Identity identity = facade.find(type, value);

        assertThat(identity).isEqualTo(expectedIdentity);
    }

    @Test
    void shouldFindIdentityByIdvId() {
        UUID idvIdValue = UUID.fromString("99cd7cbf-10da-4547-bbc3-e88e8364f60c");
        Alias alias = givenAliasReturnedForIdvId(idvIdValue);
        Identity expectedIdentity = givenIdentityFoundForAlias(alias);

        Identity identity = facade.find(idvIdValue);

        assertThat(identity).isEqualTo(expectedIdentity);
    }

    private Alias givenAliasReturnedForIdvId(UUID value) {
        return givenAliasCreatedForTypeAndValue(IdvId.TYPE, value.toString());
    }

    private Alias givenAliasCreatedForTypeAndValue(String type, String value) {
        Alias alias = DefaultAliasMother.build();
        given(aliasFactory.build(type, value)).willReturn(alias);
        return alias;
    }

    private Identity givenIdentityFoundForAlias(Alias alias) {
        Identity identity = IdentityMother.example();
        given(find.find(AliasesMother.with(alias))).willReturn(identity);
        return identity;
    }

}
