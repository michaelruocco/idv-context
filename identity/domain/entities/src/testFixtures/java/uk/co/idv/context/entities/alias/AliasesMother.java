package uk.co.idv.context.entities.alias;

import java.util.Arrays;

public interface AliasesMother {

    static Aliases empty() {
        return with();
    }

    static Aliases idvIdAndCreditCardNumber() {
        return with(IdvIdMother.idvId(), CreditCardNumberMother.creditCardNumber());
    }

    static Aliases idvIdAndDebitCardNumber() {
        return with(IdvIdMother.idvId(), DebitCardNumberMother.debitCardNumber());
    }

    static Aliases idvIdAndDebitCardNumber1() {
        return with(IdvIdMother.idvId1(), DebitCardNumberMother.debitCardNumber1());
    }

    static Aliases creditCardNumberOnly() {
        return with(CreditCardNumberMother.creditCardNumber());
    }

    static Aliases idvIdOnly() {
        return with(IdvIdMother.idvId());
    }

    static Aliases defaultAliasOnly() {
        return with(DefaultAliasMother.build());
    }

    static Aliases with(Alias... aliases) {
        return new Aliases(Arrays.asList(aliases));
    }

}
