package uk.co.idv.context.usecases.identity.data.stubbed;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.LoadIdentityRequest;
import uk.co.idv.context.usecases.identity.LoadIdentityRequestMother;
import uk.co.idv.context.usecases.identity.data.stubbed.emailaddress.StubbedEmailAddressSupplier;
import uk.co.idv.context.usecases.identity.data.stubbed.phonenumber.StubbedPhoneNumberSupplier;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class StubbedDataSupplierFactoryTest {

    private final StubbedDataSupplierFactory factory = new StubbedDataSupplierFactory();

    @Test
    void shouldReturnStubbedPhoneNumberSupplier() {
        LoadIdentityRequest request = LoadIdentityRequestMother.build();

        Supplier<PhoneNumbers> supplier = factory.phoneNumberSupplier(request);

        assertThat(supplier).isInstanceOf(StubbedPhoneNumberSupplier.class);
    }

    @Test
    void shouldReturnStubbedEmailAddressesSupplier() {
        LoadIdentityRequest request = LoadIdentityRequestMother.build();

        Supplier<EmailAddresses> supplier = factory.emailAddressSupplier(request);

        assertThat(supplier).isInstanceOf(StubbedEmailAddressSupplier.class);
    }

}
