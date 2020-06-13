package uk.co.idv.context.usecases.identity.find.data;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DataFuturesTest {

    private final CompletableFuture<PhoneNumbers> phoneNumbersFuture = mock(CompletableFuture.class);
    private final CompletableFuture<EmailAddresses> emailAddressesFuture = mock(CompletableFuture.class);

    private final DataFutures futures = DataFutures.builder()
            .phoneNumbers(phoneNumbersFuture)
            .emailAddresses(emailAddressesFuture)
            .build();

    @Test
    void shouldReturnCompletableFuturesToArray() {
        assertThat(futures.toArray()).containsExactly(
                phoneNumbersFuture,
                emailAddressesFuture
        );
    }

    @Test
    void shouldPassEmptyDataAsDefaultOnGetPhoneNumbersNow() {
        PhoneNumbers expectedNumbers = PhoneNumbersMother.mobileAndOther();
        given(phoneNumbersFuture.getNow(PhoneNumbersMother.empty())).willReturn(expectedNumbers);

        PhoneNumbers numbers = futures.getPhoneNumbersNow();

        assertThat(numbers).isEqualTo(expectedNumbers);
    }

    @Test
    void shouldPassEmptyDataAsDefaultOnGetEmailAddressesNow() {
        EmailAddresses expectedAddresses = EmailAddressesMother.two();
        given(emailAddressesFuture.getNow(EmailAddressesMother.empty())).willReturn(expectedAddresses);

        EmailAddresses addresses = futures.getEmailAddressesNow();

        assertThat(addresses).isEqualTo(expectedAddresses);
    }

}
