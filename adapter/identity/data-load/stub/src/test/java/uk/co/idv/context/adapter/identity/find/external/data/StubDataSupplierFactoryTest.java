package uk.co.idv.context.adapter.identity.find.external.data;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.identity.find.external.data.emailaddress.StubEmailAddressSupplier;
import uk.co.idv.context.adapter.identity.find.external.data.phonenumber.StubPhoneNumberSupplier;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.find.external.data.AsyncDataLoadRequest;
import uk.co.idv.context.usecases.identity.find.external.data.AsyncDataLoadRequestMother;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class StubDataSupplierFactoryTest {

    private final StubDataSupplierFactory factory = StubDataSupplierFactory.builder().build();

    @Test
    void shouldReturnStubbedPhoneNumberSupplier() {
        AsyncDataLoadRequest request = AsyncDataLoadRequestMother.build();

        Supplier<PhoneNumbers> supplier = factory.phoneNumberSupplier(request);

        assertThat(supplier).isInstanceOf(StubPhoneNumberSupplier.class);
    }

    @Test
    void shouldReturnStubbedEmailAddressesSupplier() {
        AsyncDataLoadRequest request = AsyncDataLoadRequestMother.build();

        Supplier<EmailAddresses> supplier = factory.emailAddressSupplier(request);

        assertThat(supplier).isInstanceOf(StubEmailAddressSupplier.class);
    }

}
