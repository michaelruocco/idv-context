package uk.co.idv.context.entities.alias;

public class CreditCardNumber extends CardNumber {

    static final String TYPE = "credit-card-number";

    protected CreditCardNumber(String value) {
        super(TYPE, value);
    }

}
