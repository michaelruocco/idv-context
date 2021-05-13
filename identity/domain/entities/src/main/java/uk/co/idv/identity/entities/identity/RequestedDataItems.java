package uk.co.idv.identity.entities.identity;

import java.util.Arrays;
import java.util.Collection;

public class RequestedDataItems {

    public static final String EMAIL_ADDRESSES = "email-addresses";
    public static final String PHONE_NUMBERS = "phone-numbers";
    public static final String MOBILE_DEVICES = "mobile-devices";

    private RequestedDataItems() {
        // utility class
    }

    public static Collection<String> all() {
        return Arrays.asList(
                EMAIL_ADDRESSES,
                PHONE_NUMBERS,
                MOBILE_DEVICES
        );
    }

}
