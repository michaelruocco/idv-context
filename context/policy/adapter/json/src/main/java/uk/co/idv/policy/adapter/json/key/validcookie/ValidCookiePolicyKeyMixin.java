package uk.co.idv.policy.adapter.json.key.validcookie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.policy.entities.policy.key.PolicyKey;

import java.util.Collection;

public interface ValidCookiePolicyKeyMixin {

    @JsonIgnore
    PolicyKey getBaseKey();

    @JsonIgnore
    Collection<String> getAliasTypes();

}
