package uk.co.idv.identity.adapter.protect.mask.emailaddress;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.emailaddress.EmailAddress;
import uk.co.idv.identity.entities.emailaddress.EmailAddressMother;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;

import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

class EmailAddressesMaskerTest {

    private final UnaryOperator<EmailAddresses> masker = new EmailAddressesMasker();

    @Test
    void shouldMaskEmailAddresses() {
        EmailAddress address1 = EmailAddressMother.joeBloggsHotmail();
        EmailAddress address2 = EmailAddressMother.bugsBunny();
        EmailAddresses addresses = EmailAddressesMother.with(address1, address2);

        EmailAddresses maskedAddresses = masker.apply(addresses);

        assertThat(maskedAddresses).containsExactly(
                address1.withValue("joe.**************.co.uk"),
                address2.withValue("bugs**********.co.uk")
        );
    }

}
