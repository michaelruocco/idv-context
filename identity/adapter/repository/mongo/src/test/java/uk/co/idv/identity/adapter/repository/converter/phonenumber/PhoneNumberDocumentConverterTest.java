package uk.co.idv.identity.adapter.repository.converter.phonenumber;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.adapter.repository.document.PhoneNumberDocument;
import uk.co.idv.identity.adapter.repository.document.phonenumber.PhoneNumberDocumentMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class PhoneNumberDocumentConverterTest {

    private final PhoneNumberDocumentConverter converter = new PhoneNumberDocumentConverter();

    @Test
    void shouldConvertMultiplePhoneNumbersToDocuments() {
        PhoneNumbers numbers = PhoneNumbersMother.two();

        Collection<PhoneNumberDocument> documents = converter.toDocuments(numbers);

        assertThat(documents).containsExactlyElementsOf(PhoneNumberDocumentMother.two());
    }

    @Test
    void shouldConvertPhoneNumberToDocument() {
        PhoneNumber number = PhoneNumberMother.example();

        PhoneNumberDocument document = converter.toDocument(number);

        assertThat(document).isEqualTo(PhoneNumberDocumentMother.example());
    }

    @Test
    void shouldConvertMultipleDocumentsToPhoneNumbers() {
        Collection<PhoneNumberDocument> documents = PhoneNumberDocumentMother.two();

        PhoneNumbers numbers = converter.toPhoneNumbers(documents);

        assertThat(numbers).containsExactlyElementsOf(PhoneNumbersMother.two());
    }

    @Test
    void shouldConvertPhoneNumberWithoutLastUpdatedToDocument() {
        PhoneNumber number = PhoneNumberMother.withoutLastUpdated();

        PhoneNumberDocument document = converter.toDocument(number);

        assertThat(document).isEqualTo(PhoneNumberDocumentMother.withoutLastUpdated());
    }

    @Test
    void shouldConvertDocumentToPhoneNumber() {
        PhoneNumberDocument document = PhoneNumberDocumentMother.example();

        PhoneNumber number = converter.toPhoneNumber(document);

        assertThat(number).isEqualTo(PhoneNumberMother.example());
    }

    @Test
    void shouldConvertDocumentWithoutLastUpdatedToPhoneNumber() {
        PhoneNumberDocument document = PhoneNumberDocumentMother.withoutLastUpdated();

        PhoneNumber number = converter.toPhoneNumber(document);

        assertThat(number).isEqualTo(PhoneNumberMother.withoutLastUpdated());
    }

    @Test
    void shouldConvertNullDocumentsListToEmptyPhoneNumbers() {
        Collection<PhoneNumberDocument> documents = null;

        PhoneNumbers phoneNumbers = converter.toPhoneNumbers(documents);

        assertThat(phoneNumbers).isEmpty();
    }

}
