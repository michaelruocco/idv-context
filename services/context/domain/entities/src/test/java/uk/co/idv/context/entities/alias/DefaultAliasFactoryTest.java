package uk.co.idv.context.entities.alias;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class DefaultAliasFactoryTest {

    private final AliasFactory factory = new DefaultAliasFactory();

    @Test
    void shouldBuildIdvId() {
        final String value = UUID.randomUUID().toString();

        final Alias alias = factory.build(IdvId.TYPE, value);

        assertThat(alias).isInstanceOf(IdvId.class);
        assertThat(alias.getValue()).isEqualTo(value);
    }

    @Test
    void shouldBuildCreditCardNumber() {
        final String value = "4929001111111111";

        final Alias alias = factory.build(CreditCardNumber.TYPE, value);

        assertThat(alias).isInstanceOf(CreditCardNumber.class);
        assertThat(alias.getValue()).isEqualTo(value);
    }

    @Test
    void shouldBuildDebitCardNumber() {
        final String value = "4929991111111111";

        final Alias alias = factory.build(DebitCardNumber.TYPE, value);

        assertThat(alias).isInstanceOf(DebitCardNumber.class);
        assertThat(alias.getValue()).isEqualTo(value);
    }

    @Test
    void shouldThrowExceptionForUnsupportedAliasType() {
        final String type = "not-supported";

        final Throwable error = catchThrowable(() -> factory.build(type, "ABC123"));

        assertThat(error)
                .isInstanceOf(AliasTypeNotSupportedException.class)
                .hasMessage(type);
    }

}
