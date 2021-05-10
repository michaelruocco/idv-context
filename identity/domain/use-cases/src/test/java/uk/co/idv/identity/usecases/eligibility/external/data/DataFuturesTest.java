package uk.co.idv.identity.usecases.eligibility.external.data;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;
import uk.co.idv.identity.entities.mobiledevice.MobileDevicesMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThat;

class DataFuturesTest {

    @Test
    void shouldReturnCombinedCompletableFutures() {
        CompletableFuture<PhoneNumbers> phoneNumbersFuture = completedFuture(PhoneNumbersMother.two());
        CompletableFuture<EmailAddresses> emailAddressesFuture = completedFuture(EmailAddressesMother.two());
        CompletableFuture<MobileDevices> mobileDevicesFuture = completedFuture(MobileDevicesMother.two());

        DataFutures futures = DataFutures.builder()
                .phoneNumbers(phoneNumbersFuture)
                .emailAddresses(emailAddressesFuture)
                .mobileDevices(mobileDevicesFuture)
                .build();

        assertThat(futures.allCombined().isDone()).isTrue();
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

    @Test
    void shouldReturnEmptyDataIfMobileDeviceFutureFailed() {
        CompletableFuture<MobileDevices> future = CompletableFuture.failedFuture(new Exception());

        DataFutures futures = DataFutures.builder()
                .mobileDevices(future)
                .build();

        assertThat(futures.getMobileDevicesNow()).isEqualTo(MobileDevicesMother.empty());
    }

}
