package uk.co.idv.identity.entities.alias;

import java.util.Arrays;

public interface AliasesMother {

    static DefaultAliases empty() {
        return with();
    }

    static DefaultAliases idvIdAndCreditCardNumber() {
        return with(IdvIdMother.idvId(), CardNumberMother.credit());
    }

    static DefaultAliases idvIdAndDebitCardNumber() {
        return with(IdvIdMother.idvId(), CardNumberMother.debit());
    }

    static DefaultAliases idvIdAndDebitCardNumber1() {
        return with(IdvIdMother.idvId1(), CardNumberMother.debit1());
    }

    static DefaultAliases creditCardNumberOnly() {
        return with(CardNumberMother.credit());
    }

    static DefaultAliases debitCardNumberOnly() {
        return with(CardNumberMother.debit());
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
