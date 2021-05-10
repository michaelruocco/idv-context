package uk.co.idv.identity.adapter.json.identity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

import java.util.UUID;

@JsonPropertyOrder({
        "idvId",
        "country",
        "aliases",
        "phoneNumbers",
        "emailAddresses",
})
public interface IdentityMixin {

    @JsonProperty("idvId")
    UUID getIdvIdValue();

    @JsonIgnore
    Aliases getCreditCardNumbers();

    @JsonIgnore
    PhoneNumbers getMobilePhoneNumbers();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    PhoneNumbers getPhoneNumbers();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    EmailAddresses getEmailAddresses();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    MobileDevices getMobileDevices();

}
