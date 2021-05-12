package uk.co.idv.identity.adapter.json.channel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

public interface ChannelMixin {

    @JsonInclude(Include.NON_EMPTY)
    PhoneNumbers getPhoneNumbers();

    @JsonInclude(Include.NON_EMPTY)
    EmailAddresses getEmailAddresses();

    @JsonInclude(Include.NON_EMPTY)
    MobileDevices getMobileDevices();

}
