package uk.co.idv.context.adapter.json.phonenumber;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

public interface PhoneNumbersMixin {

    @JsonIgnore
    PhoneNumbers getMobileNumbers();

    @JsonIgnore
    boolean isEmpty();

}
