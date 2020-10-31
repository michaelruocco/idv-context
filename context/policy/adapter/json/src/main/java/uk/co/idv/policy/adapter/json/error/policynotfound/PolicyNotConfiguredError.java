package uk.co.idv.policy.adapter.json.error.policynotfound;

import lombok.Getter;
import uk.co.idv.common.adapter.json.error.DefaultApiError;
import uk.co.idv.policy.entities.policy.PolicyRequest;

import java.util.Map;

@Getter
public class PolicyNotConfiguredError extends DefaultApiError {

    private static final int STATUS = 422;

    public PolicyNotConfiguredError(String title, String message, PolicyRequest request) {
        super(STATUS, title , message, toMap(request));
    }

    private static Map<String, Object> toMap(PolicyRequest request) {
        return Map.of(
                "channelId", request.getChannelId(),
                "activityName", request.getActivityName(),
                "aliasTypes", request.getAliasTypes()
        );
    }

}
