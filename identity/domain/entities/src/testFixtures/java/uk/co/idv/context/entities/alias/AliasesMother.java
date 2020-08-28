package uk.co.idv.context.entities.alias;

import java.util.Arrays;

public interface AliasesMother {

    static DefaultAliases empty() {
        return with();
    }

    static DefaultAliases idvIdAndCreditCardNumber() {
        return with(IdvIdMother.idvId(), CreditCardNumberMother.creditCardNumber());
    }

    static DefaultAliases idvIdAndDebitCardNumber() {
        return with(IdvIdMother.idvId(), DebitCardNumberMother.debitCardNumber());
    }

    static DefaultAliases idvIdAndDebitCardNumber1() {
        return with(IdvIdMother.idvId1(), DebitCardNumberMother.debitCardNumber1());
    }

    static DefaultAliases creditCardNumberOnly() {
        return with(CreditCardNumberMother.creditCardNumber());
    }

    static DefaultAliases idvIdOnly() {
        return with(IdvIdMother.idvId());
    }

    static DefaultAliases defaultAliasOnly() {
        return with(DefaultAliasMother.build());
    }

    static DefaultAliases with(Alias... aliases) {
        return new DefaultAliases(Arrays.asList(aliases));
    }

}
