package uk.co.idv.context.adapter.json.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Map;

@JsonPropertyOrder({
        "status",
        "title",
        "message",
        "meta"
})
public interface ApiErrorMixin {

    @JsonInclude(Include.NON_EMPTY)
    Map<String, Object> getMeta();

}
