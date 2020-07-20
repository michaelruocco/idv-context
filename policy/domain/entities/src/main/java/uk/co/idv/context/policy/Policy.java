package uk.co.idv.context.policy;

import java.util.UUID;

public interface Policy {

    UUID getId();

    PolicyKey getKey();

    boolean appliesTo(PolicyRequest request);

    int getPriority();

}
