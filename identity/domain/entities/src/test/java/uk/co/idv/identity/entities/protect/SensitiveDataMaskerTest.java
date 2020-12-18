package uk.co.idv.identity.entities.protect;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.emailaddress.EmailAddress;
import uk.co.idv.identity.entities.emailaddress.EmailAddressMother;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

class SensitiveDataMaskerTest {

    private final SensitiveDataMasker masker = new SensitiveDataMasker();

    @Test
    void shouldMaskPhoneNumbers() {
        PhoneNumber number = PhoneNumberMother.example();
        Identity identity = IdentityMother.withEmptyData()
                .withPhoneNumbers(PhoneNumbersMother.with(number));

        Identity protectedIdentity = masker.protect(identity);

        assertThat(protectedIdentity)
                .usingRecursiveComparison()
                .ignoringFields("phoneNumbers")
                .isEqualTo(identity);
        assertThat(protectedIdentity.getPhoneNumbers()).containsExactly(
                number.withValue("**********111")
        );
    }

    @Test
    void shouldMaskEmailAddresses() {
        EmailAddress address = EmailAddressMother.bugsBunny();
        Identity identity = IdentityMother.withEmptyData()
                .withEmailAddresses(EmailAddressesMother.with(address));

        Identity protectedIdentity = masker.protect(identity);

        assertThat(protectedIdentity)
                .usingRecursiveComparison()
                .ignoringFields("emailAddresses")
                .isEqualTo(identity);
        assertThat(protectedIdentity.getEmailAddresses()).containsExactly(
                address.withValue("bugs**********.co.uk")
        );
    }

    @Test
    void shouldThrowExceptionIfDataTypeIsNotSupported() {
        Updatable<String> data = mock(Updatable.class);

        Throwable error = catchThrowable(() -> masker.protect(data));

        assertThat(error)
                .isInstanceOf(MaskingNotSupportedException.class)
                .hasMessageStartingWith("uk.co.idv.identity.entities.protect.Updatable");
    }

}
