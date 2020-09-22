package uk.co.idv.identity.adapter.json.alias.cardnumber;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface CardNumberMixin {

    @JsonIgnore
    boolean isIdvId();

    @JsonIgnore
    boolean isCardNumber();

}
