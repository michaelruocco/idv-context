package uk.co.idv.context.entities.verification;

import lombok.Getter;

import java.util.UUID;

@Getter
public class VerificationNotFoundException extends RuntimeException {

    public VerificationNotFoundException(UUID id) {
        super(id.toString());
    }

}
