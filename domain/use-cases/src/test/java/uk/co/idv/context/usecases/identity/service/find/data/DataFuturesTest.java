package uk.co.idv.context.usecases.identity.service.find.data;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThat;

class DataFuturesTest {

    @Test
    void shouldReturnCompletableFuturesToArray() {
        CompletableFuture<PhoneNumbers> phoneNumbersFuture = completedFuture(PhoneNumbersMother.mobileAndOther());
        CompletableFuture<EmailAddresses> emailAddressesFuture = completedFuture(EmailAddressesMother.two());

        DataFutures futures = DataFutures.builder()
                .phoneNumbers(phoneNumbersFuture)
                .emailAddresses(emailAddressesFuture)
                .build();

        assertThat(futures.toArray()).containsExactly(
                phoneNumbersFuture,
                emailAddressesFuture
        );
    }

    @Test
    void shouldReturnEmptyDataIfPhoneNumbersFutureFailed() {
        CompletableFuture<PhoneNumbers> future = CompletableFuture.failedFuture(new Exception());

        DataFutures futures = DataFutures.builder()
                .phoneNumbers(future)
                .build();

        assertThat(futures.getPhoneNumbersNow()).isEqualTo(PhoneNumbersMother.empty());
    }

    @Test
    void shouldReturnEmptyDataIfEmailAddressFutureFailed() {
        CompletableFuture<EmailAddresses> future = CompletableFuture.failedFuture(new Exception());

        DataFutures futures = DataFutures.builder()
                .emailAddresses(future)
                .build();

        assertThat(futures.getEmailAddressesNow()).isEqualTo(EmailAddressesMother.empty());
    }

}
