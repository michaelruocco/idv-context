package uk.co.idv.context.entities.alias;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AliasesTest {

    @Test
    void shouldBeIterable() {
        Alias idvId = IdvIdMother.idvId();
        Alias creditCardNumber = CreditCardNumberMother.creditCardNumber();

        Aliases aliases = AliasesMother.with(idvId, creditCardNumber);

        assertThat(aliases).containsExactly(
                idvId,
                creditCardNumber
        );
    }

    @Test
    void shouldReturnStream() {
        Alias idvId = IdvIdMother.idvId();
        Alias creditCardNumber = CreditCardNumberMother.creditCardNumber();

        Aliases aliases = AliasesMother.with(idvId, creditCardNumber);

        assertThat(aliases.stream()).containsExactly(
                idvId,
                creditCardNumber
        );
    }

    @Test
    void shouldReturnSize() {
        Alias idvId = IdvIdMother.idvId();
        Alias creditCardNumber = CreditCardNumberMother.creditCardNumber();

        Aliases aliases = AliasesMother.with(idvId, creditCardNumber);

        assertThat(aliases.size()).isEqualTo(2);
    }

    @Test
    void shouldCreateEmptyInstance() {
        Aliases aliases = AliasesMother.empty();

        assertThat(aliases).isEmpty();
    }

    @Test
    void shouldThrowIdvIdNotFoundExceptionIfIdvIdNotPresent() {
        Alias creditCardNumber = CreditCardNumberMother.creditCardNumber();
        Aliases aliases = AliasesMother.with(creditCardNumber);

        Throwable error = catchThrowable(aliases::getIdvIdValue);

        assertThat(error).isInstanceOf(IdvIdNotFoundException.class);
    }

    @Test
    void shouldReturnIdvIdAliasValueIfPresent() {
        IdvId idvId = IdvIdMother.idvId();
        Aliases aliases = AliasesMother.with(idvId);

        UUID value = aliases.getIdvIdValue();

        assertThat(value).isEqualTo(idvId.getValueAsUuid());
    }

    @Test
    void shouldReturnTrueIfContainsAlias() {
        Alias idvId = IdvIdMother.idvId();

        Aliases aliases = AliasesMother.with(idvId);

        assertThat(aliases.contains(idvId)).isTrue();
    }

    @Test
    void shouldReturnFalseIfDoesNotContainAlias() {
        Alias idvId = IdvIdMother.idvId();

        Aliases aliases = AliasesMother.with(idvId);

        Alias creditCardNumber = CreditCardNumberMother.creditCardNumber();
        assertThat(aliases.contains(creditCardNumber)).isFalse();
    }

    @Test
    void shouldAddAlias() {
        Alias idvId = IdvIdMother.idvId();
        Aliases aliases = AliasesMother.with(idvId);
        Alias creditCardNumber = CreditCardNumberMother.creditCardNumber();

        Aliases addedAliases = aliases.add(creditCardNumber);

        assertThat(addedAliases).containsExactly(idvId, creditCardNumber);
    }

    @Test
    void shouldAddAliases() {
        Alias idvId = IdvIdMother.idvId();
        Aliases aliases = AliasesMother.with(idvId);
        Alias creditCardNumber = CreditCardNumberMother.creditCardNumber();
        Aliases aliasesToAdd = AliasesMother.with(creditCardNumber);

        Aliases addedAliases = aliases.add(aliasesToAdd);

        assertThat(addedAliases).containsExactly(idvId, creditCardNumber);
    }

    @Test
    void shouldReturnAliasesNotPresent() {
        Alias idvId = IdvIdMother.idvId();
        Alias creditCardNumber = CreditCardNumberMother.creditCardNumber();
        Aliases aliases = AliasesMother.with(idvId, creditCardNumber);
        Aliases comparison = AliasesMother.with(idvId);

        Aliases notPresent = aliases.notPresent(comparison);

        assertThat(notPresent).containsExactly(creditCardNumber);
    }

    @Test
    void shouldNotDuplicateAliasesOnConstruction() {
        Alias idvId = IdvIdMother.idvId();
        Aliases aliases = AliasesMother.with(idvId, idvId);

        assertThat(aliases).containsExactly(idvId);
    }

    @Test
    void shouldNotAddDuplicateAliases() {
        Alias idvId = IdvIdMother.idvId();
        Aliases aliases = AliasesMother.with(idvId);

        aliases.add(idvId);

        assertThat(aliases).containsExactly(idvId);
    }

}
