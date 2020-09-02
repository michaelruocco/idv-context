package uk.co.idv.identity.entities.alias;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class IdvIdTest {

    @Test
    void shouldThrowExceptionIfValueIsNotValidUuid() {
        String value = "not-valid-uuid";

        Throwable error = catchThrowable(() -> new IdvId(value));

        assertThat(error)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("Invalid UUID string: %s", value));
    }

    @Test
    void shouldTakeValidUuidStringAsConstructorArgument() {
        String value = UUID.randomUUID().toString();

        Alias alias = new IdvId(value);

        assertThat(alias.getValue()).isEqualTo(value);
    }

    @Test
    void shouldReturnType() {
        Alias alias = IdvIdMother.idvId();

        String type = alias.getType();

        assertThat(type).isEqualTo("idv-id");
    }

    @Test
    void isNotCardNumber() {
        Alias alias = IdvIdMother.idvId();

        boolean cardNumber = alias.isCardNumber();

        assertThat(cardNumber).isFalse();
    }

    @Test
    void shouldReturnValueAsString() {
        UUID value = UUID.randomUUID();

        Alias alias = IdvIdMother.withValue(value);

        assertThat(alias.getValue()).isEqualTo(value.toString());
    }

    @Test
    void shouldReturnValueAsUuid() {
        UUID value = UUID.randomUUID();

        IdvId alias = IdvIdMother.withValue(value);

        assertThat(alias.getValueAsUuid()).isEqualTo(value);
    }

    @Test
    void shouldReturnTrueIfAliasIsIdvId() {
        Alias alias = IdvIdMother.idvId();

        assertThat(IdvId.isIdvId(alias)).isTrue();
    }

    @Test
    void shouldGenerateRandomIdvId() {
        Alias idvId1 = IdvId.random();
        Alias idvId2 = IdvId.random();

        assertThat(idvId1).isNotEqualTo(idvId2);
    }

}
