package uk.co.idv.identity.adapter.json.emailaddress;

import com.fasterxml.jackson.annotation.JsonValue;


public interface EmailAddressMixin {

    @JsonValue
    String getValue();

}
