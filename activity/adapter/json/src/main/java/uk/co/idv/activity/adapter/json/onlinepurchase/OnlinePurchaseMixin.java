package uk.co.idv.activity.adapter.json.onlinepurchase;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface OnlinePurchaseMixin {

    @JsonIgnore
    String getLast4CardNumberDigits();

}
