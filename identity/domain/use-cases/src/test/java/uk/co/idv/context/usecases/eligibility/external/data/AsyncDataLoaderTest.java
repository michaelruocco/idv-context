package uk.co.idv.context.usecases.eligibility.external.data;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;

import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AsyncDataLoaderTest {

    private static final Duration TIMEOUT = Duration.ofMillis(5);

    private final Executor executor = Executors.newSingleThreadExecutor();
    private final DataSupplierFactory supplierFactory = mock(DataSupplierFactory.class);

    private final AsyncDataLoader loader = new AsyncDataLoader(executor, supplierFactory);

    @Test
    void shouldReturnEmptyDataIfSuppliersDoNotCompleteSuccessfully() {
        AsyncDataLoadRequest request = AsyncDataLoadRequestMother.withTimeout(TIMEOUT);
        given(supplierFactory.phoneNumberSupplier(request)).willReturn(new FailingSupplier<>());
        given(supplierFactory.emailAddressSupplier(request)).willReturn(new FailingSupplier<>());

        DataFutures futures = loader.loadData(request);

        assertThat(futures.getPhoneNumbersNow()).isEmpty();
        assertThat(futures.getEmailAddressesNow()).isEmpty();
    }

    @Test
    void shouldReturnEmptyDataIfSuppliersDoNotCompleteWithinTimeout() {
        AsyncDataLoadRequest request = AsyncDataLoadRequestMother.withTimeout(TIMEOUT);
        Duration delayDuration = TIMEOUT.plusMillis(150);
        given(supplierFactory.phoneNumberSupplier(request)).willReturn(new DelayedSupplier<>(delayDuration, PhoneNumbersMother.mobileAndOther()));
        given(supplierFactory.emailAddressSupplier(request)).willReturn(new DelayedSupplier<>(delayDuration, EmailAddressesMother.one()));

        DataFutures futures = loader.loadData(request);

        assertThat(futures.getPhoneNumbersNow()).isEmpty();
        assertThat(futures.getEmailAddressesNow()).isEmpty();
    }

    @Test
    void shouldReturnDataIfSuppliersCompleteSuccessfully() {
        AsyncDataLoadRequest request = AsyncDataLoadRequestMother.withTimeout(TIMEOUT);
        PhoneNumbers expectedPhoneNumbers = PhoneNumbersMother.mobileAndOther();
        EmailAddresses expectedEmailAddresses = EmailAddressesMother.two();
        given(supplierFactory.phoneNumberSupplier(request)).willReturn(new SuccessfulSupplier<>(expectedPhoneNumbers));
        given(supplierFactory.emailAddressSupplier(request)).willReturn(new SuccessfulSupplier<>(expectedEmailAddresses));

        DataFutures futures = loader.loadData(request);

        assertThat(futures.getPhoneNumbersNow()).isEqualTo(expectedPhoneNumbers);
        assertThat(futures.getEmailAddressesNow()).isEqualTo(expectedEmailAddresses);
    }

}
