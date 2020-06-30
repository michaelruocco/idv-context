package uk.co.idv.app.manual;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import uk.co.idv.config.identity.IdentityConfig;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.UnsupportedAliasTypeExeception;
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
import uk.co.idv.context.entities.identity.CountryMismatchException;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;
import uk.co.idv.context.usecases.identity.IdentityFacade;
import uk.co.idv.context.usecases.identity.create.IdentityMustBelongToCountryException;
import uk.co.idv.context.usecases.identity.find.IdentityNotFoundException;
import uk.co.idv.context.usecases.identity.save.CannotUpdateIdvIdException;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

public class IdentityIntegrationTest {

    private final IdentityConfig config = IdentityConfig.builder()
            .build();

    private final IdentityFacade facade = config.identityFacade();

    @Test
    void shouldThrowExceptionForUnsupportedAliasType() {
        String type = "ABC";

        Throwable error = catchThrowable(() -> facade.toAlias(type, "123"));

        assertThat(error)
                .isInstanceOf(UnsupportedAliasTypeExeception.class)
                .hasMessage(type);
    }

    @Test
    void shouldThrowExceptionIfIdentityNotFound() {
        Aliases aliases = AliasesMother.with(IdvIdMother.idvId());

        IdentityNotFoundException error = catchThrowableOfType(
                () -> facade.find(aliases),
                IdentityNotFoundException.class
        );

        assertThat(error.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldThrowExceptionIfIdentityDoesBelongToCountry() {
        Identity identity = IdentityMother.withoutCountry();

        Throwable error = catchThrowable(() -> facade.update(identity));

        assertThat(error).isInstanceOf(IdentityMustBelongToCountryException.class);
    }

    @Test
    void shouldAllocateIdvIdWhenIdentityCreated() {
        Identity identity = IdentityMother.exampleBuilder()
                .aliases(AliasesMother.creditCardNumberOnly())
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
    void cannotUpdateIdvIdOnIdentityAfterCreation() {
        Alias alias = CreditCardNumberMother.creditCardNumber();
        Identity initial = IdentityMother.withAliases(alias);
        Identity created = facade.update(initial);

        IdvId updated = IdvIdMother.idvId();
        CannotUpdateIdvIdException error = catchThrowableOfType(
                () -> facade.update(IdentityMother.withAliases(alias, updated)),
                CannotUpdateIdvIdException.class
        );

        assertThat(error.getExisting()).isEqualTo(created.getIdvId());
        assertThat(error.getUpdated()).isEqualTo(updated);
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
    void shouldThrowExceptionIfAttemptToMergeTwoIdentitiesWithDifferentCountries() {
        CreditCardNumber creditCardNumber = CreditCardNumberMother.creditCardNumber();
        Identity gbIdentity = facade.update(IdentityMother.emptyBuilder()
                .aliases(AliasesMother.with(creditCardNumber))
                .country(CountryCode.GB)
                .build());
        DebitCardNumber debitCardNumber = DebitCardNumberMother.debitCardNumber();
        Identity deIdentity = facade.update(IdentityMother.emptyBuilder()
                .aliases(AliasesMother.with(debitCardNumber))
                .country(CountryCode.DE)
                .build());
        Identity mergeInput = buildMergeInput(creditCardNumber, debitCardNumber);

        CountryMismatchException error = catchThrowableOfType(
                () -> facade.update(mergeInput),
                CountryMismatchException.class
        );

        assertThat(error.getCountryToAdd()).isEqualTo(deIdentity.getCountry());
        assertThat(error.getExistingCountry()).isEqualTo(gbIdentity.getCountry());
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
