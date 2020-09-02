package uk.co.idv.identity.entities.alias;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

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

        assertThat(aliases.isEmpty()).isTrue();
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
    void shouldReturnTrueIfContainsIdvIdAlias() {
        Alias idvId = IdvIdMother.idvId();

        Aliases aliases = AliasesMother.with(idvId);

        assertThat(aliases.hasIdvId()).isTrue();
    }

    @Test
    void shouldReturnFalseIfDoesNotContainIdvIdAlias() {
        Alias alias = CreditCardNumberMother.creditCardNumber();

        Aliases aliases = AliasesMother.with(alias);

        assertThat(aliases.hasIdvId()).isFalse();
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
    void shouldIgnoreAddingDuplicateAlias() {
        Alias existingIdvId = IdvIdMother.withValue("16d333b4-e339-46ad-b554-7de646b29f03");
        Aliases aliases = AliasesMother.with(existingIdvId);
        Alias duplicatedIdvId = IdvIdMother.withValue("16d333b4-e339-46ad-b554-7de646b29f03");

        Aliases updatedAliases = aliases.add(duplicatedIdvId);

        assertThat(updatedAliases).containsExactlyElementsOf(aliases);
    }

    @Test
    void shouldThrowExceptionIfDifferentIdvIdAliasAddedWhenAlreadyPresent() {
        Alias existingIdvId = IdvIdMother.withValue("16d333b4-e339-46ad-b554-7de646b29f03");
        Aliases aliases = AliasesMother.with(existingIdvId);

        Alias idvId = IdvIdMother.withValue("0c0dedac-ed60-49e4-bca4-c01295113a09");
        IdvIdAlreadyPresentException error = catchThrowableOfType(
                () -> aliases.add(idvId),
                IdvIdAlreadyPresentException.class
        );
        assertThat(error.getExisting()).isEqualTo(existingIdvId);
        assertThat(error.getUpdated()).isEqualTo(idvId);
    }

    @Test
    void shouldReturnAliasesNotPresent() {
        Alias idvId = IdvIdMother.idvId();
        Alias creditCardNumber = CreditCardNumberMother.creditCardNumber();
        Aliases aliases = AliasesMother.with(idvId, creditCardNumber);
        DefaultAliases comparison = AliasesMother.with(idvId);

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
        Alias alias = CreditCardNumberMother.creditCardNumber();
        Aliases aliases = AliasesMother.with(alias);

        aliases.add(alias);

        assertThat(aliases).containsExactly(alias);
    }

    @Test
    void shouldReturnOnlyCreditCardNumberAliases() {
        Alias creditCardNumber = CreditCardNumberMother.creditCardNumber();
        Alias debitCardNumber = DebitCardNumberMother.debitCardNumber();
        Aliases aliases = AliasesMother.with(creditCardNumber, debitCardNumber);

        Aliases creditCardNumbers = aliases.getCreditCardNumbers();

        assertThat(creditCardNumbers).containsExactly(creditCardNumber);
    }

    @Test
    void shouldReturnValueOfFirstAlias() {
        Alias creditCardNumber = CreditCardNumberMother.creditCardNumber();
        Alias debitCardNumber = DebitCardNumberMother.debitCardNumber();
        Aliases aliases = AliasesMother.with(creditCardNumber, debitCardNumber);

        String firstValue = aliases.getFirstValue();

        assertThat(firstValue).isEqualTo(creditCardNumber.getValue());
    }

    @Test
    void shouldThrowExceptionIfAttemptToGetFirstValueFromEmptyAliases() {
        Aliases aliases = AliasesMother.empty();

        Throwable error = catchThrowable(aliases::getFirstValue);

        assertThat(error).isInstanceOf(EmptyAliasesException.class);
    }

    @Test
    void shouldReturnTrueIfContainsAliasWithValueEndingWithSpecifiedSuffix() {
        Aliases aliases = AliasesMother.with(DefaultAliasMother.withValue("123456789"));

        boolean hasAliasEndsWith = aliases.hasAnyValuesEndingWith("9");

        assertThat(hasAliasEndsWith).isTrue();
    }

    @Test
    void shouldReturnFalseIfDoesNotContainAliasWithValueEndingWithSpecifiedSuffix() {
        Aliases aliases = AliasesMother.with(DefaultAliasMother.withValue("123456789"));

        boolean hasAliasEndsWith = aliases.hasAnyValuesEndingWith("1");

        assertThat(hasAliasEndsWith).isFalse();
    }


    @Test
    void shouldRemoveAlias() {
        Alias alias = DefaultAliasMother.withValue("123456789");
        Aliases aliases = AliasesMother.with(alias);

        Aliases updated = aliases.remove(alias);

        assertThat(updated).isEmpty();
    }

}
