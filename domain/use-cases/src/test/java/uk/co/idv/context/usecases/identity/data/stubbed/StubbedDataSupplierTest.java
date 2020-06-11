package uk.co.idv.context.usecases.identity.data.stubbed;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.CreditCardNumberMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.LoadIdentityRequest;
import uk.co.idv.context.usecases.identity.LoadIdentityRequestMother;
import uk.co.idv.context.usecases.identity.data.stubbed.phonenumber.StubbedPhoneNumberFactory;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class StubbedDataSupplierTest {

    private final Duration delay = Duration.ZERO;
    private final Sleeper sleeper = mock(Sleeper.class);
    private final StubbedPhoneNumberFactory factory = new StubbedPhoneNumberFactory();

    @Test
    void shouldReturnEmptyDataIfProvidedAliasValueEndsIn9() {
        LoadIdentityRequest request = toRequest(CreditCardNumberMother.withValueEndingIn9());
        StubbedDataSupplier<PhoneNumbers> supplier = buildSupplier(request);

        PhoneNumbers phoneNumbers = supplier.get();

        assertThat(phoneNumbers).isEqualTo(factory.getEmptyData());
    }

    @Test
    void shouldReturnPopulatedDataIfProvidedAliasValueDoesNotEndIn9() {
        LoadIdentityRequest request = toRequest(CreditCardNumberMother.creditCardNumber());
        StubbedDataSupplier<PhoneNumbers> supplier = buildSupplier(request);

        PhoneNumbers phoneNumbers = supplier.get();

        assertThat(phoneNumbers).isEqualTo(factory.getPopulatedData());
    }

    private LoadIdentityRequest toRequest(Alias alias) {
        return LoadIdentityRequestMother.withProvidedAlias(alias);
    }

    private StubbedDataSupplier<PhoneNumbers> buildSupplier(LoadIdentityRequest request) {
        return new StubbedDataSupplier<>(request, delay, factory, sleeper);
    }

}
