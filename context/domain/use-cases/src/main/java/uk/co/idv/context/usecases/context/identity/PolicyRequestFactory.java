package uk.co.idv.context.usecases.context.identity;

import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.policy.entities.policy.DefaultPolicyRequest;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.entities.policy.key.validcookie.ValidCookiePolicyRequestDecorator;

public class PolicyRequestFactory {

    public PolicyRequest toPolicyRequest(CreateContextRequest request) {
        Channel channel = request.getChannel();
        return channel.hasValidCookie()
                .map(validCookie -> toPolicyRequest(request, validCookie))
                .orElse(toDefaultPolicyRequest(request));
    }

    private PolicyRequest toPolicyRequest(CreateContextRequest request, boolean validCookie) {
        return ValidCookiePolicyRequestDecorator.builder()
                .baseRequest(toDefaultPolicyRequest(request))
                .validCookie(validCookie)
                .build();
    }

    private PolicyRequest toDefaultPolicyRequest(CreateContextRequest request) {
        return DefaultPolicyRequest.builder()
                .channelId(request.getChannelId())
                .activityName(request.getActivityName())
                .aliasTypes(request.getAliasTypes())
                .build();
    }

}
