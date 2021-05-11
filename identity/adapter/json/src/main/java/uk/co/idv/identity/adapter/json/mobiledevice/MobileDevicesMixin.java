package uk.co.idv.identity.adapter.json.mobiledevice;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;


public interface MobileDevicesMixin {

    @JsonIgnore
    boolean isEmpty();

    @JsonIgnore
    Collection<String> getTokens();

}
