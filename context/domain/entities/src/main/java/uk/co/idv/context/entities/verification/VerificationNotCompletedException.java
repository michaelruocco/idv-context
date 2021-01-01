package uk.co.idv.context.entities.verification;

import java.util.UUID;

//TODO test
public class VerificationNotCompletedException extends RuntimeException {

    public VerificationNotCompletedException(UUID id) {
        super(id.toString());
    }

}
