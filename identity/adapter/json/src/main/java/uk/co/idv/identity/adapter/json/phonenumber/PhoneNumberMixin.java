package uk.co.idv.identity.adapter.json.phonenumber;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface PhoneNumberMixin {

    @JsonIgnore
    boolean isMobile();

}
