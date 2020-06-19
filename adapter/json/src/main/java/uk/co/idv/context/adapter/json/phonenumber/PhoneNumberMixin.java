package uk.co.idv.context.adapter.json.phonenumber;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface PhoneNumberMixin {

    @JsonIgnore
    boolean isMobile();

}
