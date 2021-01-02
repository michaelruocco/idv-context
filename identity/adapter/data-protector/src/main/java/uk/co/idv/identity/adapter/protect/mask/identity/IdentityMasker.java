package uk.co.idv.identity.adapter.protect.mask.identity;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.adapter.protect.mask.emailaddress.EmailAddressesMasker;
import uk.co.idv.identity.adapter.protect.mask.phonenumber.PhoneNumbersMasker;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class IdentityMasker implements UnaryOperator<Identity> {

    private final UnaryOperator<PhoneNumbers> phoneNumbersMasker;
    private final UnaryOperator<EmailAddresses> emailAddressesMasker;

    public IdentityMasker() {
        this(new PhoneNumbersMasker(), new EmailAddressesMasker());
    }

    @Override
    public Identity apply(Identity identity) {
        return identity
                .withPhoneNumbers(phoneNumbersMasker.apply(identity.getPhoneNumbers()))
                .withEmailAddresses(emailAddressesMasker.apply(identity.getEmailAddresses()));
    }

}
