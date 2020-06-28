package uk.co.idv.context.adapter.json.eligibility;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neovisionaries.i18n.CountryCode;

public interface CreateEligibilityRequestMixin {

    @JsonIgnore
    String getChannelId();

    @JsonIgnore
    CountryCode getCountry();

}
