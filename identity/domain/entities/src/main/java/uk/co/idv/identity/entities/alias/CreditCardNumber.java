package uk.co.idv.identity.entities.alias;

public class CreditCardNumber extends CardNumber {

    public static final String TYPE = "credit-card-number";

    public CreditCardNumber(String value) {
        super(TYPE, value);
    }

}
