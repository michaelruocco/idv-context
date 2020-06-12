package uk.co.idv.context.adapter.identity.data.stub;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.identity.data.stub.emailaddress.StubEmailAddressSupplier;
import uk.co.idv.context.adapter.identity.data.stub.phonenumber.StubPhoneNumberSupplier;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.LoadIdentityRequestMother;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class StubDataSupplierFactoryTest {

    private final StubDataSupplierFactory factory = new StubDataSupplierFactory();

    @Test
    void shouldReturnStubbedPhoneNumberSupplier() {
        FindIdentityRequest request = LoadIdentityRequestMother.build();

        Supplier<PhoneNumbers> supplier = factory.phoneNumberSupplier(request);

        assertThat(supplier).isInstanceOf(StubPhoneNumberSupplier.class);
    }

    @Test
    void shouldReturnStubbedEmailAddressesSupplier() {
        FindIdentityRequest request = LoadIdentityRequestMother.build();

        Supplier<EmailAddresses> supplier = factory.emailAddressSupplier(request);

        assertThat(supplier).isInstanceOf(StubEmailAddressSupplier.class);
    }

}
