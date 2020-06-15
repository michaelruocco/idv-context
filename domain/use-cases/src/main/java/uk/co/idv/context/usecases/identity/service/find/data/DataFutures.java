package uk.co.idv.context.usecases.identity.service.find.data;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;

import java.util.concurrent.CompletableFuture;

@Builder
@Slf4j
public class DataFutures {

    private final CompletableFuture<PhoneNumbers> phoneNumbers;
    private final CompletableFuture<EmailAddresses> emailAddresses;

    public PhoneNumbers getPhoneNumbersNow() {
        return handleFuture(phoneNumbers, new PhoneNumbers());
    }

    public EmailAddresses getEmailAddressesNow() {
        return handleFuture(emailAddresses, new EmailAddresses());
    }

    public CompletableFuture<?>[] toArray() {
        return new CompletableFuture[]{
                phoneNumbers,
                emailAddresses
        };
    }

    private static <T> T handleFuture(CompletableFuture<T> future, T defaultValue) {
        return future
                .exceptionally(error -> handleError(error, defaultValue))
                .getNow(defaultValue);
    }

    private static <T> T handleError(Throwable error, T defaultValue) {
        log.debug(error.getMessage(), error);
        return defaultValue;
    }

}
