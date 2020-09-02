package uk.co.idv.lockout.adapter.json.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public interface LockoutPolicyMixin {

    @JsonIgnore
    UUID getId();

    @JsonIgnore
    int getPriority();

}
