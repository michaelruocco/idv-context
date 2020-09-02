package uk.co.idv.identity.adapter.json.channel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public interface RsaMixin {

    @JsonInclude(Include.NON_EMPTY)
    boolean getIssuerSessionId();

    @JsonInclude(Include.NON_EMPTY)
    boolean getDsSessionId();

}
