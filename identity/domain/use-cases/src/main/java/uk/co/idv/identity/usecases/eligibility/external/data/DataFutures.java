package uk.co.idv.identity.usecases.eligibility.external.data;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.entities.async.FutureHandler;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

import java.util.concurrent.CompletableFuture;

@Builder
@Slf4j
public class DataFutures {

    private final CompletableFuture<PhoneNumbers> phoneNumbers;
    private final CompletableFuture<EmailAddresses> emailAddresses;

    public PhoneNumbers getPhoneNumbersNow() {
        return FutureHandler.handle(phoneNumbers, new PhoneNumbers());
    }

    public EmailAddresses getEmailAddressesNow() {
        return FutureHandler.handle(emailAddresses, new EmailAddresses());
    }

    public CompletableFuture<Void> allCombined() {
        return CompletableFuture.allOf(phoneNumbers, emailAddresses);
    }

}
