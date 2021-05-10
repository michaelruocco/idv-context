package uk.co.idv.identity.adapter.repository.converter;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import uk.co.idv.identity.adapter.repository.converter.alias.AliasDocumentConverter;
import uk.co.idv.identity.adapter.repository.converter.emailaddress.EmailAddressesDocumentConverter;
import uk.co.idv.identity.adapter.repository.converter.mobiledevice.MobileDevicesDocumentConverter;
import uk.co.idv.identity.adapter.repository.converter.phonenumber.PhoneNumberDocumentConverter;
import uk.co.idv.identity.adapter.repository.document.IdentityDocument;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.identity.entities.identity.DefaultIdentity;
import uk.co.idv.identity.entities.identity.Identity;

@Builder
public class IdentityDocumentConverter {

    private final AliasDocumentConverter aliasesConverter;
    private final PhoneNumberDocumentConverter phoneNumberConverter;
    private final EmailAddressesDocumentConverter emailAddressesConverter;
    private final MobileDevicesDocumentConverter mobileDevicesDocumentConverter;

    public static IdentityDocumentConverter build(AliasFactory factory) {
        return IdentityDocumentConverter.builder()
                .aliasesConverter(new AliasDocumentConverter(factory))
                .phoneNumberConverter(new PhoneNumberDocumentConverter())
                .emailAddressesConverter(new EmailAddressesDocumentConverter())
                .mobileDevicesDocumentConverter(new MobileDevicesDocumentConverter())
                .build();
    }

    public Identity toIdentity(IdentityDocument document) {
        return DefaultIdentity.builder()
                .aliases(aliasesConverter.toAliases(document.getAliases()))
                .country(CountryCode.valueOf(document.getCountry()))
                .phoneNumbers(phoneNumberConverter.toPhoneNumbers(document.getPhoneNumbers()))
                .emailAddresses(emailAddressesConverter.toEmailAddresses(document.getEmailAddresses()))
                .mobileDevices(mobileDevicesDocumentConverter.toMobileDevices(document.getMobileDevices()))
                .build();
    }

    public IdentityDocument toDocument(Identity identity) {
        return IdentityDocument.builder()
                .id(identity.getIdvIdValue().toString())
                .aliases(aliasesConverter.toDocuments(identity.getAliases()))
                .country(identity.getCountry().getAlpha2())
                .phoneNumbers(phoneNumberConverter.toDocuments(identity.getPhoneNumbers()))
                .emailAddresses(emailAddressesConverter.toDocuments(identity.getEmailAddresses()))
                .mobileDevices(mobileDevicesDocumentConverter.toDocuments(identity.getMobileDevices()))
                .build();
    }

}
