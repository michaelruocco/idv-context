package uk.co.idv.identity.adapter.repository.converter;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.adapter.repository.converter.phonenumber.PhoneNumbersDocumentConverter;
import uk.co.idv.identity.adapter.repository.document.IdentityDocument;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.identity.entities.identity.DefaultIdentity;
import uk.co.idv.identity.entities.identity.Identities;
import uk.co.idv.identity.entities.identity.Identity;

import java.util.Collection;
import java.util.LinkedHashSet;

@Builder
@Slf4j
public class IdentityDocumentConverter {

    private final AliasDocumentConverter aliasesConverter;
    private final PhoneNumbersDocumentConverter phoneNumbersConverter;
    private final EmailAddressesDocumentConverter emailAddressesConverter;

    public static IdentityDocumentConverter build(AliasFactory factory) {
        return IdentityDocumentConverter.builder()
                .aliasesConverter(new AliasDocumentConverter(factory))
                .phoneNumbersConverter(new PhoneNumbersDocumentConverter())
                .emailAddressesConverter(new EmailAddressesDocumentConverter())
                .build();
    }

    public Identities toIdentities(FindIterable<IdentityDocument> documents) {
        Collection<Identity> identities = new LinkedHashSet<>();
        try (MongoCursor<IdentityDocument> cursor = documents.iterator()) {
            while (cursor.hasNext()) {
                identities.add(toIdentity(cursor.next()));
            }
        }
        log.debug("converted {} documents to identities", identities.size());
        return new Identities(identities);
    }

    public Identity toIdentity(IdentityDocument document) {
        return DefaultIdentity.builder()
                .aliases(aliasesConverter.toAliases(document.getAliases()))
                .country(CountryCode.valueOf(document.getCountry()))
                .phoneNumbers(phoneNumbersConverter.toPhoneNumbers(document.getPhoneNumbers()))
                .emailAddresses(emailAddressesConverter.toEmailAddresses(document.getEmailAddresses()))
                .build();
    }

    public IdentityDocument toDocument(Identity identity) {
        return IdentityDocument.builder()
                .id(identity.getIdvIdValue().toString())
                .aliases(aliasesConverter.toDocuments(identity.getAliases()))
                .country(identity.getCountry().getAlpha2())
                .phoneNumbers(phoneNumbersConverter.toDocuments(identity.getPhoneNumbers()))
                .emailAddresses(emailAddressesConverter.toDocuments(identity.getEmailAddresses()))
                .build();
    }

}
