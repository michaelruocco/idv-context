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

    public static boolean isMobile(String type) {
        return TYPE.equals(type);
    }

}
