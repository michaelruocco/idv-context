package uk.co.idv.policy.entities.policy;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Builder
@Data
public class DefaultPolicyRequest implements PolicyRequest {

    private final String channelId;
    private final String activityName;
    private final Collection<String> aliasTypes;

}
