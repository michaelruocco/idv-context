package uk.co.idv.context.entities.alias;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultAliasTest {

    @Test
    void shouldReturnType() {
        String type = "my-type";

        Alias alias = DefaultAliasMother.withType(type);

        assertThat(alias.getType()).isEqualTo(type);
    }

    @Test
    void shouldReturnValueString() {
        String value = "my-value";

        Alias alias = DefaultAliasMother.withValue(value);

        assertThat(alias.getValue()).isEqualTo(value);
    }

    @Test
    void shouldReturnTrueIfTypeMatchesAliasType() {
        String type = "my-type";

        Alias alias = DefaultAliasMother.withType(type);

        assertThat(alias.isType(type)).isTrue();
    }

    @Test
    void shouldReturnFalseIfTypeDoesNotMatchAliasType() {
        String type = "my-type";

        Alias alias = DefaultAliasMother.withType(type);

        assertThat(alias.isType("other-type")).isFalse();
    }

    @Test
    void isNotCardNumber() {
        Alias alias = DefaultAliasMother.build();

        boolean cardNumber = alias.isCardNumber();

        assertThat(cardNumber).isFalse();
    }

    @Test
    void shouldReturnFormattedString() {
        Alias alias = DefaultAliasMother.build();

        String formatted = alias.format();

        assertThat(formatted).isEqualTo("default-alias|11111111");
    }

}
