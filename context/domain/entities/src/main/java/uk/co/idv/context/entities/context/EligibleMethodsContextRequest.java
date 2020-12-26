package uk.co.idv.context.entities.context;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class EligibleMethodsContextRequest {

    private final UUID contextId;
    private final String methodName;

}
