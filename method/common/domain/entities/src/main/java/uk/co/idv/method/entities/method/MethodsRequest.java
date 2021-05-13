package uk.co.idv.method.entities.method;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.method.entities.policy.MethodPolicies;

import java.util.Collection;
import java.util.UUID;

@Builder
@Data
public class MethodsRequest {

    private UUID contextId;
    private Identity identity;
    private MethodPolicies policies;

    public Collection<String> getMobileDeviceTokens() {
        return identity.getMobileDevices().getTokens();
    }

}
