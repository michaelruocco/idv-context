package uk.co.idv.method.entities.verification;

import java.util.UUID;

public class VerificationNotCompletedException extends RuntimeException {

    public VerificationNotCompletedException(UUID id) {
        super(id.toString());
    }

}
