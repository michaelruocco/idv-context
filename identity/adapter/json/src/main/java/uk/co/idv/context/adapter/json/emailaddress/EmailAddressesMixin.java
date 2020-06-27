package uk.co.idv.context.adapter.json.emailaddress;

import com.fasterxml.jackson.annotation.JsonIgnore;


public interface EmailAddressesMixin {

    @JsonIgnore
    boolean isEmpty();

}
