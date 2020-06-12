package uk.co.idv.context.adapter.stub.identity.data.alias;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.AliasesMother;

import static org.assertj.core.api.Assertions.assertThat;

class StubAliasSupplierFactoryTest {

    private final StubAliasFactory factory = new StubAliasFactory();

    @Test
    void shouldReturnName() {
        assertThat(factory.getName()).isEqualTo("Aliases");
    }

    @Test
    void shouldReturnEmptyData() {
        assertThat(factory.getEmptyData()).isEmpty();
    }

    @Test
    void shouldReturnPopulatedData() {
        assertThat(factory.getPopulatedData()).isEqualTo(AliasesMother.idvIdAndCreditCardNumber());
    }

}
