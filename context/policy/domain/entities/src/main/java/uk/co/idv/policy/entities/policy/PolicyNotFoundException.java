package uk.co.idv.policy.entities.policy;


import java.util.UUID;

public class PolicyNotFoundException extends RuntimeException {

    public PolicyNotFoundException(UUID id) {
        super(id.toString());
    }

}
