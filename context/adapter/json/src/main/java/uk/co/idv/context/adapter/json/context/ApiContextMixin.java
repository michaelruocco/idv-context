package uk.co.idv.context.adapter.json.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.method.entities.verification.Verifications;

public interface ApiContextMixin {

    @JsonIgnore
    Verifications getVerifications();

}
