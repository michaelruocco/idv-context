package uk.co.idv.identity.entities.alias;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class DefaultAliasFactoryTest {

    private final AliasFactory factory = new DefaultAliasFactory();

    @Test
    void shouldBuildIdvId() {
        String value = UUID.randomUUID().toString();

        Alias alias = factory.build(IdvId.TYPE, value);

        assertThat(alias).isInstanceOf(IdvId.class);
        assertThat(alias.getValue()).isEqualTo(value);
    }

    @Test
    void shouldBuildCreditCardNumber() {
        String value = "4929001111111111";

        Alias alias = factory.build(CreditCardNumber.TYPE, value);

        assertThat(alias).isInstanceOf(CreditCardNumber.class);
        assertThat(alias.getValue()).isEqualTo(value);
    }

    @Test
    void shouldBuildDebitCardNumber() {
        String value = "4929991111111111";

        Alias alias = factory.build(DebitCardNumber.TYPE, value);

        assertThat(alias).isInstanceOf(DebitCardNumber.class);
        assertThat(alias.getValue()).isEqualTo(value);
    }

    @Test
    void shouldThrowExceptionForUnsupportedAliasType() {
        String type = "not-supported";

        Throwable error = catchThrowable(() -> factory.build(type, "ABC123"));

        assertThat(error)
                .isInstanceOf(UnsupportedAliasTypeException.class)
                .hasMessage(type);
    }

}
