package uk.co.idv.context.entities.verification;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class CreateVerificationRequest {

    private final UUID contextId;
    private final String methodName;

}
