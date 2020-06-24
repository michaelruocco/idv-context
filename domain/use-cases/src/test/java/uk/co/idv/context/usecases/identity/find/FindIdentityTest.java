package uk.co.idv.context.usecases.identity.find;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.identity.Identities;
import uk.co.idv.context.entities.identity.IdentitiesMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.IdentityRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class FindIdentityTest {

    private final IdentityRepository repository = mock(IdentityRepository.class);

    private final FindIdentity loader = new FindIdentity(repository);

    @Test
    void shouldThrowExceptionIfNoIdentitiesExist() {
        Aliases aliases = AliasesMother.creditCardNumberOnly();
        givenNoExistingIdentities(aliases);

        IdentityNotFoundException error = catchThrowableOfType(
                () -> loader.find(aliases),
                IdentityNotFoundException.class
        );

        assertThat(error.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldReturnIdentityIfOneExistingIdentity() {
        Aliases aliases = AliasesMother.idvIdAndDebitCardNumber();
        Identity expected = IdentityMother.withAliases(aliases);
        givenOneExistingIdentity(aliases, expected);

        Identity identity = loader.find(aliases);

        assertThat(identity).isEqualTo(expected);
    }

    @Test
    void shouldThrowExceptionIfMoreThanOneIdentityExists() {
        Aliases aliases = AliasesMother.idvIdAndDebitCardNumber();
        Identities existing = givenMoreThanOneExistingIdentity(aliases);

        MultipleIdentitiesFoundException error = catchThrowableOfType(
                () -> loader.find(aliases),
                MultipleIdentitiesFoundException.class
        );

        assertThat(error.getAliases()).isEqualTo(aliases);
        assertThat(error.getIdentities()).isEqualTo(existing);
    }

    private void givenNoExistingIdentities(Aliases aliases) {
        given(repository.load(aliases)).willReturn(IdentitiesMother.empty());
    }

    private void givenOneExistingIdentity(Aliases aliases, Identity identity) {
        given(repository.load(aliases)).willReturn(IdentitiesMother.of(identity));
    }

    private Identities givenMoreThanOneExistingIdentity(Aliases aliases) {
        Identities existing = IdentitiesMother.two();
        given(repository.load(aliases)).willReturn(existing);
        return existing;
    }

}
