package uk.co.idv.context.entities.policy;

import java.util.UUID;

public interface Policy {

    UUID getId();

    boolean appliesTo(PolicyRequest request);

    int getPriority();

    PolicyKey getKey();

}
