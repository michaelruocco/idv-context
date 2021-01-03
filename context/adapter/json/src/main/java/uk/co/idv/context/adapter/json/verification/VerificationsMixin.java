package uk.co.idv.context.adapter.json.verification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.context.entities.verification.Verification;

import java.util.Collection;

public interface VerificationsMixin {

    @JsonIgnore
    Collection<Verification> getValues();

}
