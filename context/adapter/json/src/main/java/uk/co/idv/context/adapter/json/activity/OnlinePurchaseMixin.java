package uk.co.idv.context.adapter.json.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface OnlinePurchaseMixin {

    @JsonIgnore
    String getCardNumberValue();

    @JsonIgnore
    String getLast4DigitsOfCardNumber();

}
