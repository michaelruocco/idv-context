package uk.co.idv.identity.adapter.protect.mask.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.emailaddress.EmailAddress;
import uk.co.idv.identity.entities.emailaddress.EmailAddressMother;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;

import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityMaskerTest {

    private final UnaryOperator<Identity> masker = new IdentityMasker();

    @Test
    void shouldMaskPhoneNumbers() {
        PhoneNumber number1 = PhoneNumberMother.example();
        PhoneNumber number2 = PhoneNumberMother.example1();
        Identity identity = IdentityMother.withPhoneNumbers(number1, number2)
                .withEmailAddresses(EmailAddressesMother.empty());

        Identity maskedIdentity = masker.apply(identity);

        assertThat(maskedIdentity)
                .usingRecursiveComparison()
                .ignoringFields("phoneNumbers")
                .isEqualTo(identity);
        assertThat(maskedIdentity.getPhoneNumbers()).containsExactly(
                number1.withValue("**********111"),
                number2.withValue("**********212")
        );
    }

    @Test
    void shouldMaskEmailAddresses() {
        EmailAddress address1 = EmailAddressMother.joeBloggsHotmail();
        EmailAddress address2 = EmailAddressMother.bugsBunny();
        Identity identity = IdentityMother.withEmailAddresses(address1, address2)
                .withPhoneNumbers(PhoneNumbersMother.empty());

        Identity maskedIdentity = masker.apply(identity);

        assertThat(maskedIdentity)
                .usingRecursiveComparison()
                .ignoringFields("emailAddresses")
                .isEqualTo(identity);
        assertThat(maskedIdentity.getEmailAddresses()).containsExactly(
                address1.withValue("joe.**************.co.uk"),
                address2.withValue("bugs**********.co.uk")
        );
    }

}
