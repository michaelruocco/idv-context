package uk.co.idv.app.plain.identity;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import uk.co.idv.app.plain.Application;
import uk.co.idv.app.plain.TestHarness;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.CardNumber;
import uk.co.idv.identity.entities.alias.CardNumberMother;
import uk.co.idv.identity.entities.alias.DefaultAliases;
import uk.co.idv.identity.entities.alias.UnsupportedAliasTypeException;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.alias.IdvIdMother;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.identity.CannotUpdateIdvIdException;
import uk.co.idv.identity.entities.identity.CountryMismatchException;
import uk.co.idv.identity.entities.identity.CountryNotProvidedException;
import uk.co.idv.identity.entities.identity.EmptyIdentityMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.entities.identity.IdentityNotFoundException;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class IdentityIntegrationTest {

    private final TestHarness harness = new TestHarness();
    private final Application application = harness.getApplication();

    @Test
    void shouldThrowExceptionForUnsupportedAliasType() {
        String type = "ABC";

        Throwable error = catchThrowable(() -> application.findIdentity(type, "123"));

        assertThat(error)
                .isInstanceOf(UnsupportedAliasTypeException.class)
                .hasMessage(type);
    }

    @Test
    void shouldThrowExceptionIfIdentityNotFound() {
        Aliases aliases = AliasesMother.with(IdvIdMother.idvId());

        IdentityNotFoundException error = catchThrowableOfType(
                () -> application.findIdentity(aliases),
                IdentityNotFoundException.class
        );

        assertThat(error.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldThrowExceptionIfIdentityDoesBelongToCountry() {
        Identity identity = IdentityMother.withoutCountry();

        Throwable error = catchThrowable(() -> application.update(identity));

        assertThat(error).isInstanceOf(CountryNotProvidedException.class);
    }

    @Test
    void shouldAllocateIdvIdWhenIdentityCreated() {
        Identity identity = IdentityMother.exampleBuilder()
                .aliases(AliasesMother.creditCardNumberOnly())
                .emailAddresses(EmailAddressesMother.empty())
                .phoneNumbers(PhoneNumbersMother.empty())
                .build();

        Identity created = application.update(identity);

        assertThat(created.hasIdvId()).isTrue();
    }

    @Test
    void shouldFindCreatedIdentity() {
        DefaultAliases aliases = AliasesMother.creditCardNumberOnly();
        Identity identity = IdentityMother.exampleBuilder()
                .aliases(aliases)
                .emailAddresses(EmailAddressesMother.empty())
                .phoneNumbers(PhoneNumbersMother.empty())
                .build();
        Identity created = application.update(identity);

        Identity found = application.findIdentity(aliases);

        assertThat(found).isEqualTo(created);
    }

    @Test
    void cannotUpdateIdvIdOnIdentityAfterCreation() {
        Alias alias = CardNumberMother.credit();
        Identity initial = IdentityMother.withAliases(alias);
        Identity created = application.update(initial);
        Identity updated = IdentityMother.withAliases(alias, IdvIdMother.idvId());

        CannotUpdateIdvIdException error = catchThrowableOfType(
                () -> application.update(updated),
                CannotUpdateIdvIdException.class
        );

        assertThat(error.getExisting()).isEqualTo(created.getIdvId());
        assertThat(error.getUpdated()).isEqualTo(updated.getIdvId());
    }

    @Test
    void canRemoveAliasOnUpdate() {
        Alias idvId = IdvIdMother.idvId();
        Alias creditCardNumber = CardNumberMother.credit();
        Alias debitCardNumber = CardNumberMother.debit();
        Identity initial = IdentityMother.withAliases(idvId, creditCardNumber, debitCardNumber);
        Identity created = application.update(initial);

        Identity change = IdentityMother.withAliases(idvId, creditCardNumber);
        Identity updated = application.update(change);

        assertThat(created.getAliases()).containsExactly(idvId, creditCardNumber, debitCardNumber);
        assertThat(updated.getAliases()).containsExactly(idvId, creditCardNumber);
    }

    @Test
    void shouldMergeIdentities() {
        CardNumber creditCardNumber = CardNumberMother.credit();
        Identity creditIdentity = application.update(buildCreditIdentity(creditCardNumber));
        CardNumber debitCardNumber = CardNumberMother.debit();
        Identity debitIdentity = application.update(buildDebitIdentity(debitCardNumber));
        Identity mergeInput = buildMergeInput(creditCardNumber, debitCardNumber);

        Identity merged = application.update(mergeInput);

        IdvId idvId = merged.getIdvId();
        assertThat(idvId)
                .isNotEqualTo(creditIdentity.getIdvId())
                .isNotEqualTo(debitIdentity.getIdvId());
        assertThat(merged)
                .isEqualTo(application.findIdentity(AliasesMother.with(creditCardNumber)))
                .isEqualTo(application.findIdentity(AliasesMother.with(debitCardNumber)));
    }

    @Test
    void shouldThrowExceptionIfAttemptToMergeTwoIdentitiesWithDifferentCountries() {
        CardNumber creditCardNumber = CardNumberMother.credit();
        Identity gbIdentity = application.update(EmptyIdentityMother.builder()
                .aliases(AliasesMother.with(creditCardNumber))
                .country(CountryCode.GB)
                .build());
        CardNumber debitCardNumber = CardNumberMother.debit();
        Identity deIdentity = application.update(EmptyIdentityMother.builder()
                .aliases(AliasesMother.with(debitCardNumber))
                .country(CountryCode.DE)
                .build());
        Identity mergeInput = buildMergeInput(creditCardNumber, debitCardNumber);

        CountryMismatchException error = catchThrowableOfType(
                () -> application.update(mergeInput),
                CountryMismatchException.class
        );

        assertThat(error.getUpdated()).isEqualTo(deIdentity.getCountry());
        assertThat(error.getExisting()).isEqualTo(gbIdentity.getCountry());
    }

    @Test
    void shouldMergeAliasesWhenMergingIdentity() {
        CardNumber creditCardNumber = CardNumberMother.credit();
        Identity creditIdentity = application.update(buildCreditIdentity(creditCardNumber));
        CardNumber debitCardNumber = CardNumberMother.debit();
        Identity debitIdentity = application.update(buildDebitIdentity(debitCardNumber));
        Identity mergeInput = buildMergeInput(creditCardNumber, debitCardNumber);

        Identity merged = application.update(mergeInput);

        Aliases aliases = merged.getAliases();
        assertThat(aliases).hasSize(3);
        assertThat(aliases.hasIdvId()).isTrue();
        assertThat(aliases)
                .containsAll(creditIdentity.removeIdvId().getAliases())
                .containsAll(debitIdentity.removeIdvId().getAliases());
    }

    @Test
    void shouldMergeEmailAddressesWhenMergingIdentity() {
        CardNumber creditCardNumber = CardNumberMother.credit();
        Identity creditIdentity = application.update(buildCreditIdentity(creditCardNumber));
        CardNumber debitCardNumber = CardNumberMother.debit();
        Identity debitIdentity = application.update(buildDebitIdentity(debitCardNumber));
        Identity mergeInput = buildMergeInput(creditCardNumber, debitCardNumber);

        Identity merged = application.update(mergeInput);

        EmailAddresses emailAddresses = merged.getEmailAddresses();
        assertThat(emailAddresses)
                .hasSize(2)
                .containsAll(creditIdentity.getEmailAddresses())
                .containsAll(debitIdentity.getEmailAddresses());
    }

    @Test
    void shouldMergePhoneNumbersWhenMergingIdentity() {
        CardNumber creditCardNumber = CardNumberMother.credit();
        Identity creditIdentity = application.update(buildCreditIdentity(creditCardNumber));
        CardNumber debitCardNumber = CardNumberMother.debit();
        Identity debitIdentity = application.update(buildDebitIdentity(debitCardNumber));
        Identity mergeInput = buildMergeInput(creditCardNumber, debitCardNumber);

        Identity merged = application.update(mergeInput);

        PhoneNumbers phoneNumbers = merged.getPhoneNumbers();
        assertThat(phoneNumbers)
                .hasSize(2)
                .containsAll(creditIdentity.getPhoneNumbers())
                .containsAll(debitIdentity.getPhoneNumbers());
    }

    private static Identity buildCreditIdentity(CardNumber alias) {
        return IdentityMother.exampleBuilder()
                .aliases(AliasesMother.with(alias))
                .emailAddresses(EmailAddressesMother.with("credit@email.com"))
                .phoneNumbers(PhoneNumbersMother.one())
                .build();
    }

    private static Identity buildDebitIdentity(CardNumber alias) {
        return IdentityMother.exampleBuilder()
                .aliases(AliasesMother.with(alias))
                .emailAddresses(EmailAddressesMother.with("debit@email.com"))
                .phoneNumbers(PhoneNumbersMother.two())
                .build();
    }

    private static Identity buildMergeInput(Alias... aliases) {
        return EmptyIdentityMother.builder()
                .aliases(AliasesMother.with(aliases))
                .build();
    }

}
