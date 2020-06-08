package uk.co.idv.context.entities.alias;

import java.util.Arrays;

public interface AliasesMother {

    static Aliases empty() {
        return with();
    }

    static Aliases idvIdAndCreditCardNumber() {
        return with(IdvIdMother.idvId(), CreditCardNumberMother.creditCardNumber());
    }

    static Aliases with(Alias... aliases) {
        return new Aliases(Arrays.asList(aliases));
    }

}
