package uk.co.idv.context.entities.phonenumber;

public class MobilePhoneNumber extends AbstractPhoneNumber {

    static final String TYPE = "mobile";

    public MobilePhoneNumber(String value) {
        super(TYPE, value);
    }

    @Override
    public boolean isMobile() {
        return true;
    }

}
