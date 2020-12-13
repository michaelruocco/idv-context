package uk.co.idv.identity.adapter.repository.document;

import uk.co.idv.identity.adapter.repository.document.alias.AliasDocumentMother;
import uk.co.idv.identity.adapter.repository.document.emailaddress.EmailAddressDocumentMother;
import uk.co.idv.identity.adapter.repository.document.phonenumber.PhoneNumberDocumentMother;

public interface IdentityDocumentMother {

    static IdentityDocument example() {
        return IdentityDocument.builder()
                .id("90b585c6-170f-42a6-ac7c-83d294bdab3f")
                .country("GB")
                .aliases(AliasDocumentMother.idvIdAndCreditCardNumber())
                .phoneNumbers(PhoneNumberDocumentMother.two())
                .emailAddresses(EmailAddressDocumentMother.two())
                .build();
    }

}
