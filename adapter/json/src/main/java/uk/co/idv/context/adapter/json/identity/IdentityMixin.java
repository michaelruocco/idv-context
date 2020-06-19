package uk.co.idv.context.adapter.json.identity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;

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

}