package uk.co.idv.context.adapter.stub.identity.data;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.CreditCardNumberMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.FindIdentityRequestMother;
import uk.co.idv.context.adapter.stub.identity.data.phonenumber.StubPhoneNumberFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class StubDataSupplierTest {

    private final Delay delay = mock(Delay.class);
    private final StubPhoneNumberFactory factory = new StubPhoneNumberFactory();

    @Test
    void shouldReturnEmptyDataIfProvidedAliasValueEndsIn9() {
        FindIdentityRequest request = toRequest(CreditCardNumberMother.withValueEndingIn9());
        StubDataSupplier<PhoneNumbers> supplier = buildSupplier(request);

        PhoneNumbers phoneNumbers = supplier.get();

        assertThat(phoneNumbers).isEqualTo(factory.getEmptyData());
    }

    @Test
    void shouldReturnPopulatedDataIfProvidedAliasValueDoesNotEndIn9() {
        FindIdentityRequest request = toRequest(CreditCardNumberMother.creditCardNumber());
        StubDataSupplier<PhoneNumbers> supplier = buildSupplier(request);

        PhoneNumbers phoneNumbers = supplier.get();

        assertThat(phoneNumbers).isEqualTo(factory.getPopulatedData());
    }

    private FindIdentityRequest toRequest(Alias alias) {
        return FindIdentityRequestMother.withProvidedAlias(alias);
    }

    private StubDataSupplier<PhoneNumbers> buildSupplier(FindIdentityRequest request) {
        return new StubDataSupplier<>(request, delay, factory);
    }

}
