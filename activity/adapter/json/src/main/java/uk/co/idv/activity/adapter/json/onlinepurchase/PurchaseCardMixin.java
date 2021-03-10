package uk.co.idv.activity.adapter.json.onlinepurchase;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface PurchaseCardMixin {

    @JsonIgnore
    String getLast4NumberDigits();

}
