package uk.co.idv.context.entities.alias;

public class DebitCardNumber extends CardNumber {

    static final String TYPE = "debit-card-number";

    protected DebitCardNumber(final String value) {
        super(TYPE, value);
    }

}
