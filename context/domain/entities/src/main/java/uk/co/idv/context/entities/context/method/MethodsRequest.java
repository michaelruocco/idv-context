package uk.co.idv.context.entities.context.method;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.policy.method.MethodPolicies;
import uk.co.idv.identity.entities.identity.Identity;

import java.util.UUID;

@Builder
@Data
public class MethodsRequest {

    private UUID contextId;
    private Identity identity;
    private MethodPolicies policies;

}
