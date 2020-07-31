package uk.co.idv.context.usecases.policy.load;

import uk.co.idv.context.entities.policy.PolicyRequest;

import java.util.UUID;

public class PolicyNotFoundException extends RuntimeException {

    public PolicyNotFoundException(UUID id) {
        super(id.toString());
    }

    public PolicyNotFoundException(PolicyRequest request) {
        super(request.toString());
    }

}
