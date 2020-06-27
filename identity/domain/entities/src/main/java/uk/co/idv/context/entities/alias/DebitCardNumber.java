package uk.co.idv.context.entities.alias;

public class DebitCardNumber extends CardNumber {

    public static final String TYPE = "debit-card-number";

    public DebitCardNumber(String value) {
        super(TYPE, value);
    }

}
