package uk.co.idv.identity.adapter.json.emailaddress;

import com.fasterxml.jackson.annotation.JsonIgnore;


public interface EmailAddressesMixin {

    @JsonIgnore
    boolean isEmpty();

}
