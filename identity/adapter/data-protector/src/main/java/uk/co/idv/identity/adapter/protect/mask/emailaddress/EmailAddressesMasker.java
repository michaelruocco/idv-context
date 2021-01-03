package uk.co.idv.identity.adapter.protect.mask.emailaddress;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.emailaddress.EmailAddress;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;

import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EmailAddressesMasker implements UnaryOperator<EmailAddresses> {

    private final UnaryOperator<String> stringMasker;

    public EmailAddressesMasker() {
        this(new EmailAddressStringMasker());
    }

    @Override
    public EmailAddresses apply(EmailAddresses emailAddresses) {
        return new EmailAddresses(emailAddresses.stream()
                .map(this::protect)
                .collect(Collectors.toList()));
    }

    public EmailAddress protect(EmailAddress address) {
        return address.withValue(stringMasker.apply(address.getValue()));
    }

}
