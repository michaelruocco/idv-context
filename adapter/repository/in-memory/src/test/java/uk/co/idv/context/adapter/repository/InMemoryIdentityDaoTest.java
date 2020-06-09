package uk.co.idv.context.adapter.repository;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.CreditCardNumberMother;
import uk.co.idv.context.entities.alias.DebitCardNumberMother;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.IdentityRepository;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryIdentityDaoTest {

    private final IdentityRepository dao = new InMemoryIdentityRepository();

    @Test
    void shouldReturnEmptyOptionalIfIdentityNotFound() {
        Alias alias = CreditCardNumberMother.creditCardNumber();

        assertThat(dao.load(alias)).isEmpty();
    }

    @Test
    void shouldLoadSavedIdentityByAliases() {
        Alias idvId = IdvIdMother.idvId();
        Alias creditCardNumber = CreditCardNumberMother.creditCardNumber();
        Identity identity = IdentityMother.withAliases(AliasesMother.with(idvId, creditCardNumber));

        dao.save(identity);

        assertThat(dao.load(idvId)).contains(identity);
        assertThat(dao.load(creditCardNumber)).contains(identity);
    }

    @Test
    void shouldNotBeAbleToLoadByAliasThatHasBeenDeleted() {
        Alias idvId = IdvIdMother.idvId();
        Alias creditCardNumber = CreditCardNumberMother.creditCardNumber();
        Identity original = IdentityMother.withAliases(AliasesMother.with(idvId, creditCardNumber));
        dao.save(original);

        Alias debitCardNumber = DebitCardNumberMother.debitCardNumber();
        Identity updated = IdentityMother.withAliases(AliasesMother.with(idvId, debitCardNumber));
        dao.save(updated);

        assertThat(dao.load(idvId)).contains(updated);
        assertThat(dao.load(debitCardNumber)).contains(updated);
        assertThat(dao.load(creditCardNumber)).isEmpty();
    }

}
