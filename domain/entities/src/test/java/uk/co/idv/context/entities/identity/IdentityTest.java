package uk.co.idv.context.entities.identity;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.CreditCardNumberMother;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.entities.phonenumber.MobilePhoneNumberMother;
import uk.co.idv.context.entities.phonenumber.OtherPhoneNumberMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumber;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class IdentityTest {

    @Test
    void shouldReturnCountry() {
        CountryCode country = CountryCode.GB;

        Identity identity = IdentityMother.withCountry(country);

        assertThat(identity.getCountry()).isEqualTo(country);
    }

    @Test
    void shouldReturnAliases() {
        Aliases aliases = AliasesMother.idvIdAndCreditCardNumber();

        Identity identity = IdentityMother.withAliases(aliases);

        assertThat(identity.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldReturnIdvIdAliasValue() {
        IdvId idvId = IdvIdMother.idvId();

        Identity identity = IdentityMother.withAliases(idvId);

        assertThat(identity.getIdvIdValue()).isEqualTo(idvId.getValueAsUuid());
    }

    @Test
    void shouldReturnIdvIdAlias() {
        IdvId idvId = IdvIdMother.idvId();

        Identity identity = IdentityMother.withAliases(idvId);

        assertThat(identity.getIdvId()).isEqualTo(idvId);
    }

    @Test
    void shouldReturnTrueIfHasAlias() {
        IdvId idvId = IdvIdMother.idvId();

        Identity identity = IdentityMother.withAliases(idvId);

        assertThat(identity.hasAlias(idvId)).isTrue();
    }

    @Test
    void shouldReturnFalseIfDoesNotHaveAlias() {
        IdvId idvId = IdvIdMother.idvId();

        Identity identity = IdentityMother.withAliases(idvId);

        Alias alias = CreditCardNumberMother.creditCardNumber();
        assertThat(identity.hasAlias(alias)).isFalse();
    }

    @Test
    void shouldReturnPhoneNumbers() {
        PhoneNumbers numbers = PhoneNumbersMother.mobileAndOther();

        Identity identity = IdentityMother.withPhoneNumbers(numbers);

        assertThat(identity.getPhoneNumbers()).isEqualTo(numbers);
    }

    @Test
    void shouldReturnMobilePhoneNumbers() {
        PhoneNumber mobile = MobilePhoneNumberMother.mobile();
        PhoneNumber other = OtherPhoneNumberMother.other();

        Identity identity = IdentityMother.withPhoneNumbers(mobile, other);

        assertThat(identity.getMobilePhoneNumbers()).containsExactly(mobile);
    }

    @Test
    void shouldReturnEmailAddresses() {
        EmailAddresses emailAddresses = EmailAddressesMother.two();

        Identity identity = IdentityMother.withEmailAddresses(emailAddresses);

        assertThat(identity.getEmailAddresses()).isEqualTo(emailAddresses);
    }

    @Test
    void shouldReturnAliasesNotPresentInOtherIdentity() {
        Aliases aliases = mock(Aliases.class);
        Aliases otherAliases = mock(Aliases.class);
        Aliases expectedAliases = mock(Aliases.class);
        given(aliases.notPresent(otherAliases)).willReturn(expectedAliases);
        Identity identity = IdentityMother.withAliases(aliases);
        Identity other = IdentityMother.withAliases(otherAliases);

        Aliases notPresent = identity.getAliasesNotPresent(other);

        assertThat(notPresent).isEqualTo(expectedAliases);
    }

    @Test
    void shouldReturnHasIdvAliasFromAliases() {
        Aliases aliases = mock(Aliases.class);
        given(aliases.hasIdvId()).willReturn(true);

        boolean hasIdvId = aliases.hasIdvId();

        assertThat(hasIdvId).isTrue();
    }

    @Test
    void shouldCopyAllOtherAttributesWhenSettingIdvIdAlias() {
        Aliases aliases = mock(Aliases.class);
        Identity identity = IdentityMother.withAliases(aliases);
        IdvId idvId = IdvIdMother.idvId();
        Aliases updatedAliases = mock(Aliases.class);
        given(aliases.add(idvId)).willReturn(updatedAliases);

        Identity updatedIdentity = identity.setIdvId(idvId);

        assertThat(updatedIdentity).isEqualToIgnoringGivenFields(updatedIdentity, "aliases");
        assertThat(updatedIdentity.getAliases()).isEqualTo(updatedAliases);
    }

}
