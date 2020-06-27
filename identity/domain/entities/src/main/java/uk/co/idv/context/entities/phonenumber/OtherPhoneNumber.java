package uk.co.idv.context.entities.phonenumber;

public class OtherPhoneNumber extends AbstractPhoneNumber {

    static final String TYPE = "other";

    public OtherPhoneNumber(String value) {
        super(TYPE, value);
    }

    @Override
    public boolean isMobile() {
        return false;
    }

}
