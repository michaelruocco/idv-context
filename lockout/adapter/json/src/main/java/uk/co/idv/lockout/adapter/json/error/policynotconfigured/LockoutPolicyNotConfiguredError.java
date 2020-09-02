package uk.co.idv.lockout.adapter.json.error.policynotconfigured;

import lombok.Getter;
import uk.co.idv.common.adapter.json.error.DefaultApiError;
import uk.co.idv.policy.entities.policy.PolicyRequest;

import java.util.Map;

@Getter
public class LockoutPolicyNotConfiguredError extends DefaultApiError {

    private static final int STATUS = 422;
    private static final String TITLE = "Lockout policy not configured";
    private static final String MESSAGE = "Lockout policy not configured for channel, activity and alias combination";

    public LockoutPolicyNotConfiguredError(PolicyRequest request) {
        super(STATUS, TITLE, MESSAGE, toMap(request));
    }

    private static Map<String, Object> toMap(PolicyRequest request) {
        return Map.of(
                "channelId", request.getChannelId(),
                "activityName", request.getActivityName(),
                "aliasTypes", request.getAliasTypes()
        );
    }

}
