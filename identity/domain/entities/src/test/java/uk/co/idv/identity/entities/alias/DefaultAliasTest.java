package uk.co.idv.identity.entities.alias;

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
    void shouldReturnTrueIfTypeIsIdvId() {
        String type = "idv-id";

        Alias alias = DefaultAliasMother.withType(type);

        assertThat(alias.isIdvId()).isTrue();
    }

    @Test
    void shouldReturnFalseIfTypeIsNotIdvId() {
        String type = "other-type";

        Alias alias = DefaultAliasMother.withType(type);

        assertThat(alias.isIdvId()).isFalse();
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

    @Test
    void shouldReturnTrueIfValueEndsWithSpecifiedSuffix() {
        Alias alias = DefaultAliasMother.withValue("my-value-123");

        boolean endsWith = alias.valueEndsWith("123");

        assertThat(endsWith).isTrue();
    }

    @Test
    void shouldReturnFalseIfValueDoesNotEndWithSpecifiedSuffix() {
        Alias alias = DefaultAliasMother.withValue("my-value-123");

        boolean endsWith = alias.valueEndsWith("789");

        assertThat(endsWith).isFalse();
    }

}
