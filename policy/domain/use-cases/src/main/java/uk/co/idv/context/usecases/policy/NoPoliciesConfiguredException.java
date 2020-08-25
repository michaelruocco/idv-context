package uk.co.idv.context.usecases.policy;

import lombok.Getter;
import uk.co.idv.context.entities.policy.PolicyRequest;

@Getter
public class NoPoliciesConfiguredException extends RuntimeException {

    private final transient PolicyRequest request;

    public NoPoliciesConfiguredException(PolicyRequest request) {
        super(toMessage(request));
        this.request = request;
    }

    private static String toMessage(PolicyRequest request) {
        return String.format("channel: %s, activity: %s, alias type: %s",
                request.getChannelId(),
                request.getActivityName(),
                request.getAliasType());
    }

}
