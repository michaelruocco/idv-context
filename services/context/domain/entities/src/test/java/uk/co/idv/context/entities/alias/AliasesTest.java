package uk.co.idv.context.entities.alias;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AliasesTest {

    @Test
    void shouldBeIterable() {
        Alias idvId = IdvIdMother.build();
        Alias creditCardNumber = CreditCardNumberMother.build();

        Aliases aliases = Aliases.with(idvId, creditCardNumber);

        assertThat(aliases).containsExactly(
                idvId,
                creditCardNumber
        );
    }

    @Test
    void shouldReturnStream() {
        Alias idvId = IdvIdMother.build();
        Alias creditCardNumber = CreditCardNumberMother.build();

        Aliases aliases = Aliases.with(idvId, creditCardNumber);

        assertThat(aliases.stream()).containsExactly(
                idvId,
                creditCardNumber
        );
    }

    @Test
    void shouldReturnSize() {
        Alias idvId = IdvIdMother.build();
        Alias creditCardNumber = CreditCardNumberMother.build();

        Aliases aliases = Aliases.with(idvId, creditCardNumber);

        assertThat(aliases.size()).isEqualTo(2);
    }

    @Test
    void shouldCreateEmptyInstance() {
        Aliases aliases = Aliases.empty();

        assertThat(aliases).isEmpty();
    }

    @Test
    void shouldEmptyOptionalIfIdvIdAliasNotPresent() {
        Alias creditCardNumber = CreditCardNumberMother.build();
        Aliases aliases = Aliases.with(creditCardNumber);

        Optional<UUID> value = aliases.getIdvIdValue();

        assertThat(value).isEmpty();
    }

    @Test
    void shouldReturnIdvIdAliasValueIfPresent() {
        IdvId idvId = IdvIdMother.build();
        Aliases aliases = Aliases.with(idvId);

        Optional<UUID> value = aliases.getIdvIdValue();

        assertThat(value).contains(idvId.getValueAsUuid());
    }

    @Test
    void shouldReturnTrueIfContainsAlias() {
        Alias idvId = IdvIdMother.build();

        Aliases aliases = Aliases.with(idvId);

        assertThat(aliases.contains(idvId)).isTrue();
    }

    @Test
    void shouldReturnFalseIfDoesNotContainAlias() {
        Alias idvId = IdvIdMother.build();

        Aliases aliases = Aliases.with(idvId);

        Alias creditCardNumber = CreditCardNumberMother.build();
        assertThat(aliases.contains(creditCardNumber)).isFalse();
    }

    @Test
    void shouldAddAlias() {
        Alias idvId = IdvIdMother.build();
        Aliases aliases = Aliases.with(idvId);
        Alias creditCardNumber = CreditCardNumberMother.build();

        aliases.add(creditCardNumber);

        assertThat(aliases).containsExactly(idvId, creditCardNumber);
    }

    @Test
    void shouldAddAliases() {
        Alias idvId = IdvIdMother.build();
        Aliases aliases = Aliases.with(idvId);
        Alias creditCardNumber = CreditCardNumberMother.build();
        Aliases aliasesToAdd = Aliases.with(creditCardNumber);

        aliases.add(aliasesToAdd);

        assertThat(aliases).containsExactly(idvId, creditCardNumber);
    }

}
