package uk.co.idv.context.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.find.FindIdentity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class IdentityFacadeTest {

    private final UpdateIdentity update = mock(UpdateIdentity.class);
    private final FindIdentity find = mock(FindIdentity.class);

    private final IdentityFacade facade = IdentityFacade.builder()
            .update(update)
            .find(find)
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
    void shouldFindIdentity() {
        Aliases aliases = AliasesMother.idvIdAndDebitCardNumber();
        Identity expected = IdentityMother.example1();
        given(find.find(aliases)).willReturn(expected);

        Identity identity = facade.find(aliases);

        assertThat(identity).isEqualTo(expected);
    }

}