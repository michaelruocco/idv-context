package uk.co.idv.identity.adapter.repository.converter;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import uk.co.idv.identity.adapter.repository.converter.alias.AliasDocumentConverter;
import uk.co.idv.identity.adapter.repository.converter.emailaddress.EmailAddressDocumentConverter;
import uk.co.idv.identity.adapter.repository.converter.mobiledevice.MobileDeviceDocumentConverter;
import uk.co.idv.identity.adapter.repository.converter.phonenumber.PhoneNumberDocumentConverter;
import uk.co.idv.identity.adapter.repository.document.IdentityDocument;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.identity.entities.identity.DefaultIdentity;
import uk.co.idv.identity.entities.identity.Identity;

@Builder
public class IdentityDocumentConverter {

    private final AliasDocumentConverter aliasConverter;
    private final PhoneNumberDocumentConverter phoneNumberConverter;
    private final EmailAddressDocumentConverter emailAddressConverter;
    private final MobileDeviceDocumentConverter mobileDeviceDocumentConverter;

    public static IdentityDocumentConverter build(AliasFactory factory) {
        return IdentityDocumentConverter.builder()
                .aliasConverter(new AliasDocumentConverter(factory))
                .phoneNumberConverter(new PhoneNumberDocumentConverter())
                .emailAddressConverter(new EmailAddressDocumentConverter())
                .mobileDeviceDocumentConverter(new MobileDeviceDocumentConverter())
                .build();
    }

    public Identity toIdentity(IdentityDocument document) {
        return DefaultIdentity.builder()
                .aliases(aliasConverter.toAliases(document.getAliases()))
                .country(CountryCode.valueOf(document.getCountry()))
                .phoneNumbers(phoneNumberConverter.toPhoneNumbers(document.getPhoneNumbers()))
                .emailAddresses(emailAddressConverter.toEmailAddresses(document.getEmailAddresses()))
                .mobileDevices(mobileDeviceDocumentConverter.toMobileDevices(document.getMobileDevices()))
                .build();
    }

    public IdentityDocument toDocument(Identity identity) {
        return IdentityDocument.builder()
                .id(identity.getIdvIdValue().toString())
                .aliases(aliasConverter.toDocuments(identity.getAliases()))
                .country(identity.getCountry().getAlpha2())
                .phoneNumbers(phoneNumberConverter.toDocuments(identity.getPhoneNumbers()))
                .emailAddresses(emailAddressConverter.toDocuments(identity.getEmailAddresses()))
                .mobileDevices(mobileDeviceDocumentConverter.toDocuments(identity.getMobileDevices()))
                .build();
    }

}
