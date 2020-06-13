package uk.co.idv.context.usecases.identity.find.data;

import lombok.Builder;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;

import java.util.concurrent.CompletableFuture;

@Builder
public class DataFutures {

    private final CompletableFuture<PhoneNumbers> phoneNumbers;
    private final CompletableFuture<EmailAddresses> emailAddresses;

    public PhoneNumbers getPhoneNumbersNow() {
        return phoneNumbers.getNow(new PhoneNumbers());
    }

    public EmailAddresses getEmailAddressesNow() {
        return emailAddresses.getNow(new EmailAddresses());
    }

    public CompletableFuture<?>[] toArray() {
        return new CompletableFuture[] {
                phoneNumbers,
                emailAddresses
        };
    }

}
