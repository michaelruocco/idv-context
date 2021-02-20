package uk.co.idv.method.adapter.json.verification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.method.entities.verification.Verification;

import java.util.Collection;

public interface VerificationsMixin {

    @JsonIgnore
    Collection<Verification> getValues();

}
