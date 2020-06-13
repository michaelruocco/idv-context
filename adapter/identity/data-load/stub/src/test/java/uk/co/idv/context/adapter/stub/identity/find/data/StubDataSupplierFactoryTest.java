package uk.co.idv.context.adapter.stub.identity.find.data;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.stub.identity.find.data.alias.StubAliasSupplier;
import uk.co.idv.context.adapter.stub.identity.find.data.emailaddress.StubEmailAddressSupplier;
import uk.co.idv.context.adapter.stub.identity.find.data.phonenumber.StubPhoneNumberSupplier;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.FindIdentityRequestMother;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class StubDataSupplierFactoryTest {

    private final StubDataSupplierFactory factory = StubDataSupplierFactory.builder().build();

    @Test
    void shouldReturnStubbedAliasSupplier() {
        FindIdentityRequest request = FindIdentityRequestMother.build();

        Supplier<Aliases> supplier = factory.aliasesSupplier(request);

        assertThat(supplier).isInstanceOf(StubAliasSupplier.class);
    }

    @Test
    void shouldReturnStubbedPhoneNumberSupplier() {
        FindIdentityRequest request = FindIdentityRequestMother.build();

        Supplier<PhoneNumbers> supplier = factory.phoneNumberSupplier(request);

        assertThat(supplier).isInstanceOf(StubPhoneNumberSupplier.class);
    }

    @Test
    void shouldReturnStubbedEmailAddressesSupplier() {
        FindIdentityRequest request = FindIdentityRequestMother.build();

        Supplier<EmailAddresses> supplier = factory.emailAddressSupplier(request);

        assertThat(supplier).isInstanceOf(StubEmailAddressSupplier.class);
    }

}
