package uk.co.idv.identity.usecases.protect.mask;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.emailaddress.EmailAddress;
import uk.co.idv.identity.entities.emailaddress.EmailAddressMother;

import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

class EmailAddressMaskerTest {

    private final UnaryOperator<String> masker = new EmailAddressMasker();

    @Test
    void shouldMaskEmailAddressValue() {
        EmailAddress address = EmailAddressMother.bugsBunny();

        String maskedValue = masker.apply(address.getValue());

        assertThat(maskedValue).isEqualTo("bugs**********.co.uk");
    }

}
