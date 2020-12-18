package uk.co.idv.identity.entities.emailaddress;

import org.junit.jupiter.api.Test;

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
