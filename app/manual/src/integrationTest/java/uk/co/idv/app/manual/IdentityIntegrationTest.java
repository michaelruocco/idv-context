package uk.co.idv.app.manual;

import org.junit.jupiter.api.Test;
import uk.co.idv.config.manual.ManualConfig;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.CreditCardNumber;
import uk.co.idv.context.entities.alias.CreditCardNumberMother;
import uk.co.idv.context.entities.alias.DebitCardNumber;
import uk.co.idv.context.entities.alias.DebitCardNumberMother;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;
import uk.co.idv.context.usecases.identity.IdentityFacade;
import uk.co.idv.context.usecases.identity.find.IdentityNotFoundException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class IdentityIntegrationTest {

    private final ManualConfig appConfig = ManualConfig.builder()
            .build();

    private final IdentityFacade facade = appConfig.identityFacade();

    @Test
    void shouldThrowExceptionIfIdentityNotFound() {
        Aliases aliases = AliasesMother.with(IdvIdMother.withValue(UUID.randomUUID()));

        IdentityNotFoundException error = catchThrowableOfType(
                () -> facade.find(aliases),
                IdentityNotFoundException.class
        );

        assertThat(error.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldAllocateIdvIdWhenIdentityCreated() {
        Aliases aliases = AliasesMother.creditCardNumberOnly();
        Identity identity = IdentityMother.exampleBuilder()
                .aliases(aliases)
                .emailAddresses(EmailAddressesMother.empty())
                .phoneNumbers(PhoneNumbersMother.empty())
                .build();

        Identity created = facade.update(identity);

        assertThat(created.hasIdvId()).isTrue();
    }

    @Test
    void shouldFindCreatedIdentity() {
        Aliases aliases = AliasesMother.creditCardNumberOnly();
        Identity identity = IdentityMother.exampleBuilder()
                .aliases(aliases)
                .emailAddresses(EmailAddressesMother.empty())
                .phoneNumbers(PhoneNumbersMother.empty())
                .build();
        Identity created = facade.update(identity);

        Identity found = facade.find(aliases);

        assertThat(found).isEqualTo(created);
    }

    @Test
    void shouldMergeIdentities() {
        CreditCardNumber creditCardNumber = CreditCardNumberMother.creditCardNumber();
        Identity creditIdentity = facade.update(buildCreditIdentity(creditCardNumber));
        DebitCardNumber debitCardNumber = DebitCardNumberMother.debitCardNumber();
        Identity debitIdentity = facade.update(buildDebitIdentity(debitCardNumber));
        Identity mergeInput = buildMergeInput(creditCardNumber, debitCardNumber);

        Identity merged = facade.update(mergeInput);

        IdvId idvId = merged.getIdvId();
        assertThat(idvId).isNotEqualTo(creditIdentity.getIdvId());
        assertThat(idvId).isNotEqualTo(debitIdentity.getIdvId());
        assertThat(merged).isEqualTo(facade.find(AliasesMother.with(creditCardNumber)));
        assertThat(merged).isEqualTo(facade.find(AliasesMother.with(debitCardNumber)));
    }

    @Test
    void shouldMergeAliasesWhenMergingIdentity() {
        CreditCardNumber creditCardNumber = CreditCardNumberMother.creditCardNumber();
        Identity creditIdentity = facade.update(buildCreditIdentity(creditCardNumber));
        DebitCardNumber debitCardNumber = DebitCardNumberMother.debitCardNumber();
        Identity debitIdentity = facade.update(buildDebitIdentity(debitCardNumber));
        Identity mergeInput = buildMergeInput(creditCardNumber, debitCardNumber);

        Identity merged = facade.update(mergeInput);

        Aliases aliases = merged.getAliases();
        assertThat(aliases).hasSize(3);
        assertThat(aliases.hasIdvId()).isTrue();
        assertThat(aliases).containsAll(creditIdentity.removeIdvId().getAliases());
        assertThat(aliases).containsAll(debitIdentity.removeIdvId().getAliases());
    }

    @Test
    void shouldMergeEmailAddressesWhenMergingIdentity() {
        CreditCardNumber creditCardNumber = CreditCardNumberMother.creditCardNumber();
        Identity creditIdentity = facade.update(buildCreditIdentity(creditCardNumber));
        DebitCardNumber debitCardNumber = DebitCardNumberMother.debitCardNumber();
        Identity debitIdentity = facade.update(buildDebitIdentity(debitCardNumber));
        Identity mergeInput = buildMergeInput(creditCardNumber, debitCardNumber);

        Identity merged = facade.update(mergeInput);

        EmailAddresses emailAddresses = merged.getEmailAddresses();
        assertThat(emailAddresses).hasSize(2);
        assertThat(emailAddresses).containsAll(creditIdentity.getEmailAddresses());
        assertThat(emailAddresses).containsAll(debitIdentity.getEmailAddresses());
    }

    @Test
    void shouldMergePhoneNumbersWhenMergingIdentity() {
        CreditCardNumber creditCardNumber = CreditCardNumberMother.creditCardNumber();
        Identity creditIdentity = facade.update(buildCreditIdentity(creditCardNumber));
        DebitCardNumber debitCardNumber = DebitCardNumberMother.debitCardNumber();
        Identity debitIdentity = facade.update(buildDebitIdentity(debitCardNumber));
        Identity mergeInput = buildMergeInput(creditCardNumber, debitCardNumber);

        Identity merged = facade.update(mergeInput);

        PhoneNumbers phoneNumbers = merged.getPhoneNumbers();
        assertThat(phoneNumbers).hasSize(2);
        assertThat(phoneNumbers).containsAll(creditIdentity.getPhoneNumbers());
        assertThat(phoneNumbers).containsAll(debitIdentity.getPhoneNumbers());
    }

    private static Identity buildCreditIdentity(CreditCardNumber alias) {
        return IdentityMother.exampleBuilder()
                .aliases(AliasesMother.with(alias))
                .emailAddresses(EmailAddressesMother.with("credit@email.com"))
                .phoneNumbers(PhoneNumbersMother.mobile())
                .build();
    }

    private static Identity buildDebitIdentity(DebitCardNumber alias) {
        return IdentityMother.exampleBuilder()
                .aliases(AliasesMother.with(alias))
                .emailAddresses(EmailAddressesMother.with("debit@email.com"))
                .phoneNumbers(PhoneNumbersMother.other())
                .build();
    }

    private static Identity buildMergeInput(Alias... aliases) {
        return IdentityMother.emptyBuilder()
                .aliases(AliasesMother.with(aliases))
                .build();
    }

}
