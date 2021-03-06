package uk.co.idv.policy.usecases.policy.load;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.policy.entities.policy.DefaultPolicyRequest;
import uk.co.idv.policy.entities.policy.DefaultPolicyRequest.DefaultPolicyRequestBuilder;
import uk.co.idv.policy.entities.policy.key.PolicyKey;
import uk.co.idv.policy.entities.policy.PolicyRequest;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class PolicyKeyConverter {

    public Collection<PolicyRequest> toPolicyRequests(PolicyKey key) {
        Collection<PolicyRequest> requests = new ArrayList<>();
        DefaultPolicyRequestBuilder builder = DefaultPolicyRequest.builder().channelId(key.getChannelId());
        for (String activityName : key.getActivityNames()) {
            builder.activityName(activityName);
            builder.aliasTypes(key.getAliasTypes());
            requests.add(builder.build());
        }
        log.info("converted key {} into policy requests {}", key, requests);
        return requests;
    }

}
