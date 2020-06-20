package uk.co.idv.context.adapter.eligibility.external.data;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.CreditCardNumberMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.adapter.eligibility.external.data.phonenumber.StubPhoneNumberFactory;
import uk.co.idv.context.usecases.eligibility.external.data.AsyncDataLoadRequest;
import uk.co.idv.context.usecases.eligibility.external.data.AsyncDataLoadRequestMother;
import uk.co.idv.context.usecases.eligibility.external.data.Delay;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class StubDataSupplierTest {

    private final Delay delay = mock(Delay.class);
    private final StubPhoneNumberFactory factory = new StubPhoneNumberFactory();

    @Test
    void shouldReturnEmptyDataIfAtLeastOneAliasValueEndsIn9() {
        AsyncDataLoadRequest request = toRequest(AliasesMother.with(CreditCardNumberMother.withValueEndingIn9()));
        StubDataSupplier<PhoneNumbers> supplier = buildSupplier(request);

        PhoneNumbers phoneNumbers = supplier.get();

        assertThat(phoneNumbers).isEqualTo(factory.getEmptyData());
    }

    @Test
    void shouldReturnPopulatedDataIfAllAliasValuesDoNotEndIn9() {
        AsyncDataLoadRequest request = toRequest(AliasesMother.with(CreditCardNumberMother.creditCardNumber()));
        StubDataSupplier<PhoneNumbers> supplier = buildSupplier(request);

        PhoneNumbers phoneNumbers = supplier.get();

        assertThat(phoneNumbers).isEqualTo(factory.getPopulatedData());
    }

    private AsyncDataLoadRequest toRequest(Aliases aliases) {
        return AsyncDataLoadRequestMother.withAliases(aliases);
    }

    private StubDataSupplier<PhoneNumbers> buildSupplier(AsyncDataLoadRequest request) {
        return new StubDataSupplier<>(request.getAliases(), delay, factory);
    }

}
