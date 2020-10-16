package uk.co.idv.policy.usecases.policy.load;


import java.util.UUID;

public class PolicyNotFoundException extends RuntimeException {

    public PolicyNotFoundException(UUID id) {
        super(id.toString());
    }

}
