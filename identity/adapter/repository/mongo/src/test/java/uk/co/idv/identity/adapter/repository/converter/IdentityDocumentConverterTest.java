package uk.co.idv.identity.adapter.repository.converter;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.adapter.repository.converter.alias.AliasDocumentConverter;
import uk.co.idv.identity.adapter.repository.converter.emailaddress.EmailAddressesDocumentConverter;
import uk.co.idv.identity.adapter.repository.converter.phonenumber.PhoneNumberDocumentConverter;
import uk.co.idv.identity.adapter.repository.document.AliasDocument;
import uk.co.idv.identity.adapter.repository.document.IdentityDocument;
import uk.co.idv.identity.adapter.repository.document.IdentityDocumentMother;
import uk.co.idv.identity.adapter.repository.document.PhoneNumberDocument;
import uk.co.idv.identity.adapter.repository.document.alias.AliasDocumentMother;
import uk.co.idv.identity.adapter.repository.document.emailaddress.EmailAddressDocumentMother;
import uk.co.idv.identity.adapter.repository.document.phonenumber.PhoneNumberDocumentMother;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class IdentityDocumentConverterTest {

    private final AliasDocumentConverter aliasesConverter = mock(AliasDocumentConverter.class);
    private final PhoneNumberDocumentConverter phoneNumberConverter = mock(PhoneNumberDocumentConverter.class);
    private final EmailAddressesDocumentConverter emailAddressesConverter = mock(EmailAddressesDocumentConverter.class);

    private final IdentityDocumentConverter converter = IdentityDocumentConverter.builder()
            .aliasesConverter(aliasesConverter)
            .phoneNumberConverter(phoneNumberConverter)
            .emailAddressesConverter(emailAddressesConverter)
            .build();

    @Test
    void shouldConvertCountryFromDocument() {
        IdentityDocument document = IdentityDocumentMother.example();

        Identity converted = converter.toIdentity(document);

        assertThat(converted.getCountry()).isEqualTo(CountryCode.GB);
    }

    @Test
    void shouldConvertAliasDocumentsToAliases() {
        IdentityDocument document = IdentityDocumentMother.example();
        Aliases expectedAliases = AliasesMother.idvIdAndCreditCardNumber();
        given(aliasesConverter.toAliases(document.getAliases())).willReturn(expectedAliases);

        Identity converted = converter.toIdentity(document);

        assertThat(converted.getAliases()).isEqualTo(expectedAliases);
    }

    @Test
    void shouldConvertPhoneNumberDocumentsToPhoneNumbers() {
        IdentityDocument document = IdentityDocumentMother.example();
        PhoneNumbers expectedPhoneNumbers = PhoneNumbersMother.two();
        given(phoneNumberConverter.toPhoneNumbers(document.getPhoneNumbers())).willReturn(expectedPhoneNumbers);

        Identity converted = converter.toIdentity(document);

        assertThat(converted.getPhoneNumbers()).isEqualTo(expectedPhoneNumbers);
    }

    @Test
    void shouldConvertEmailAddressDocumentsToPhoneNumbers() {
        IdentityDocument document = IdentityDocumentMother.example();
        EmailAddresses expectedEmailAddresses = EmailAddressesMother.two();
        given(emailAddressesConverter.toEmailAddresses(document.getEmailAddresses())).willReturn(expectedEmailAddresses);

        Identity converted = converter.toIdentity(document);

        assertThat(converted.getEmailAddresses()).isEqualTo(expectedEmailAddresses);
    }

    @Test
    void shouldConvertCountryToDocument() {
        Identity identity = IdentityMother.example();

        IdentityDocument converted = converter.toDocument(identity);

        assertThat(converted.getCountry()).isEqualTo(identity.getCountry().getAlpha2());
    }

    @Test
    void shouldConvertAliasesToAliasDocuments() {
        Identity identity = IdentityMother.example();
        Collection<AliasDocument> expectedDocuments = AliasDocumentMother.idvIdAndCreditCardNumber();
        given(aliasesConverter.toDocuments(identity.getAliases())).willReturn(expectedDocuments);

        IdentityDocument converted = converter.toDocument(identity);

        assertThat(converted.getAliases()).isEqualTo(expectedDocuments);
    }

    @Test
    void shouldConvertPhoneNumbersToPhoneNumberDocuments() {
        Identity identity = IdentityMother.example();
        Collection<PhoneNumberDocument> expectedDocuments = PhoneNumberDocumentMother.two();
        given(phoneNumberConverter.toDocuments(identity.getPhoneNumbers())).willReturn(expectedDocuments);

        IdentityDocument converted = converter.toDocument(identity);

        assertThat(converted.getPhoneNumbers()).isEqualTo(expectedDocuments);
    }

    @Test
    void shouldConvertEmailAddressesToPhoneNumberDocuments() {
        Identity identity = IdentityMother.example();
        Collection<String> expectedDocuments = EmailAddressDocumentMother.two();
        given(emailAddressesConverter.toDocuments(identity.getEmailAddresses())).willReturn(expectedDocuments);

        IdentityDocument converted = converter.toDocument(identity);

        assertThat(converted.getEmailAddresses()).isEqualTo(expectedDocuments);
    }

}
