package uk.co.idv.identity.adapter.json.mobiledevice;

import com.fasterxml.jackson.annotation.JsonIgnore;


public interface MobileDevicesMixin {

    @JsonIgnore
    boolean isEmpty();

}
