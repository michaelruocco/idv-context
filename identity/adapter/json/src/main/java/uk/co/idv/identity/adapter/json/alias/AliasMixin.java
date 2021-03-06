package uk.co.idv.identity.adapter.json.alias;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "type",
        "value"
})
public interface AliasMixin {

    @JsonIgnore
    boolean isIdvId();

    @JsonIgnore
    boolean isCardNumber();

}
