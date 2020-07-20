package uk.co.idv.context.policy.key;

import uk.co.idv.context.policy.PolicyKey;
import uk.co.idv.context.policy.PolicyRequest;
import uk.co.idv.context.policy.PolicyRequest.PolicyRequestBuilder;

import java.util.ArrayList;
import java.util.Collection;

public class PolicyKeyConverter {

    public Collection<PolicyRequest> toPolicyRequests(PolicyKey key) {
        Collection<PolicyRequest> requests = new ArrayList<>();
        PolicyRequestBuilder builder = PolicyRequest.builder().channelId(key.getChannelId());
        for (String activityName : key.getActivityNames()) {
            builder.activityName(activityName);
            for (String aliasType : key.getAliasTypes()) {
                builder.aliasType(aliasType);
                requests.add(builder.build());
            }
        }
        return requests;
    }

}
