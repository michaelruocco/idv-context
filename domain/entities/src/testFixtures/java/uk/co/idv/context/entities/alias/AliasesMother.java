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

    static Aliases creditCardNumberOnly() {
        return with(CreditCardNumberMother.creditCardNumber());
    }

    static Aliases debitCardNumberOnly() {
        return with(DebitCardNumberMother.debitCardNumber());
    }

    static Aliases idvIdOnly() {
        return with(IdvIdMother.idvId());
    }

    static Aliases with(Alias... aliases) {
        return new Aliases(Arrays.asList(aliases));
    }

}
