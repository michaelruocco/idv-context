package uk.co.idv.identity.adapter.repository.converter.emailaddress;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.adapter.repository.document.emailaddress.EmailAddressDocumentMother;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class EmailAddressDocumentConverterTest {

    private final EmailAddressDocumentConverter converter = new EmailAddressDocumentConverter();

    @Test
    void shouldConvertEmailAddressesToDocuments() {
        EmailAddresses emailAddresses = EmailAddressesMother.two();

        Collection<String> documents = converter.toDocuments(emailAddresses);

        assertThat(documents).containsExactlyElementsOf(EmailAddressDocumentMother.two());
    }

    @Test
    void shouldConvertDocumentsToEmailAddresses() {
        Collection<String> documents = EmailAddressDocumentMother.two();

        EmailAddresses emailAddresses = converter.toEmailAddresses(documents);

        assertThat(emailAddresses).containsExactlyElementsOf(EmailAddressesMother.two());
    }

    @Test
    void shouldConvertNullDocumentsListToEmptyEmailAddresses() {
        Collection<String> documents = null;

        EmailAddresses emailAddresses = converter.toEmailAddresses(documents);

        assertThat(emailAddresses).isEmpty();
    }

}
