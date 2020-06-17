package uk.co.idv.context.adapter.json.alias;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface AliasMixin {

    @JsonIgnore
    boolean isIdvId();

    @JsonIgnore
    boolean isCardNumber();

}
