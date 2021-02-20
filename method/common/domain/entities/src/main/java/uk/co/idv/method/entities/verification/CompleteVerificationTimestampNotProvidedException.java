package uk.co.idv.method.entities.verification;

import java.util.UUID;

public class CompleteVerificationTimestampNotProvidedException extends RuntimeException {

    public CompleteVerificationTimestampNotProvidedException(UUID id) {
        super(id.toString());
    }

}
