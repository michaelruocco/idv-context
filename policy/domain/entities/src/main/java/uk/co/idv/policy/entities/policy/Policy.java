package uk.co.idv.policy.entities.policy;

import uk.co.idv.policy.entities.policy.key.PolicyKey;

import java.util.UUID;

public interface Policy {

    UUID getId();

    boolean appliesTo(PolicyRequest request);

    int getPriority();

    PolicyKey getKey();

}
