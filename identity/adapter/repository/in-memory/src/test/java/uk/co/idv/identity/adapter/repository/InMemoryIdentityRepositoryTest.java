package uk.co.idv.identity.adapter.repository;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.alias.CardNumberMother;
import uk.co.idv.identity.entities.alias.DefaultAliasMother;
import uk.co.idv.identity.entities.alias.DefaultAliases;
import uk.co.idv.identity.entities.alias.IdvIdMother;
import uk.co.idv.identity.entities.identity.Identities;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.usecases.identity.IdentityRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryIdentityRepositoryTest {

    private final IdentityRepository repository = new InMemoryIdentityRepository();

    @Test
    void shouldReturnEmptyOptionalIfIdentityNotFound() {
        Alias alias = CardNumberMother.credit();

        assertThat(repository.load(toAliases(alias))).isEmpty();
    }

    @Test
    void shouldLoadSavedIdentityByAliases() {
        Alias idvId = IdvIdMother.idvId();
        Alias creditCardNumber = CardNumberMother.credit();
        Identity identity = IdentityMother.withAliases(toAliases(idvId, creditCardNumber));

        repository.create(identity);

        assertThat(repository.load(toAliases(idvId))).contains(identity);
        assertThat(repository.load(toAliases(creditCardNumber))).contains(identity);
    }

    @Test
    void shouldNotBeAbleToLoadByAliasThatHasBeenDeleted() {
        Alias idvId = IdvIdMother.idvId();
        Alias creditCardNumber = CardNumberMother.credit();
        Identity original = IdentityMother.withAliases(toAliases(idvId, creditCardNumber));
        repository.create(original);

        Alias debitCardNumber = CardNumberMother.debit();
        Identity updated = IdentityMother.withAliases(toAliases(idvId, debitCardNumber));
        repository.update(updated, original);

        assertThat(repository.load(toAliases(idvId))).contains(updated);
        assertThat(repository.load(toAliases(debitCardNumber))).contains(updated);
        assertThat(repository.load(toAliases(creditCardNumber))).isEmpty();
    }

    @Test
    void shouldLoadSavedIdentitiesByAliases() {
        Alias idvId1 = IdvIdMother.withValue("c91acc5a-501a-4dc7-9c02-f784916a8ca7");
        Identity identity1 = IdentityMother.withAliases(toAliases(idvId1));
        repository.create(identity1);

        Alias idvId2 = IdvIdMother.withValue("dbb4d277-612b-4ec6-8c95-2e86a9c06a7f");
        Identity identity2 = IdentityMother.withAliases(toAliases(idvId2));
        repository.create(identity2);

        Identities identities = repository.load(toAliases(idvId1, idvId2));

        assertThat(identities).containsExactly(identity1, identity2);
    }

    @Test
    void shouldOnlyReturnUniqueIdentitiesWhenLoadSavedIdentitiesByAliases() {
        DefaultAliases aliases = AliasesMother.idvIdAndCreditCardNumber();
        Identity identity = IdentityMother.withAliases(aliases);
        repository.create(identity);

        Identities identities = repository.load(aliases);

        assertThat(identities).containsExactly(identity);
    }

    @Test
    void shouldDeleteSavedIdentitiesByAliases() {
        DefaultAliases aliases = AliasesMother.idvIdOnly();
        Identity identity = IdentityMother.withAliases(aliases);
        repository.create(identity);

        repository.delete(aliases);

        assertThat(repository.load(aliases)).isEmpty();
    }

    @Test
    void shouldLoadSavedIdentityByAlias() {
        Alias alias = DefaultAliasMother.build();
        Identity expectedIdentity = IdentityMother.withAliases(IdvIdMother.idvId(), alias);
        repository.create(expectedIdentity);

        Optional<Identity> identity = repository.load(alias);

        assertThat(identity).contains(expectedIdentity);
    }

    @Test
    void shouldReturnEmptyOptionalIfIdentityFound() {
        Alias alias = DefaultAliasMother.build();

        Optional<Identity> identity = repository.load(alias);

        assertThat(identity).isEmpty();
    }

    private static DefaultAliases toAliases(Alias... alias) {
        return AliasesMother.with(alias);
    }

}
