package uk.co.idv.context.entities.alias;

public interface DebitCardNumberMother {

    static DebitCardNumber debitCardNumber() {
        return withValue("4929222222222222");
    }

    static DebitCardNumber debitCardNumber1() {
        return withValue("5929111111111111");
    }

    static DebitCardNumber withValue(String value) {
        return new DebitCardNumber(value);
    }

}
