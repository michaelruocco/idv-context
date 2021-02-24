package uk.co.idv.policy.entities.policy.key.validcookie;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import uk.co.idv.policy.entities.policy.PolicyRequest;

import java.util.Collection;

@Builder
@Data
public class ValidCookiePolicyRequestDecorator implements ValidCookiePolicyRequest {

    @Getter(AccessLevel.NONE)
    private final PolicyRequest baseRequest;

    @Getter(AccessLevel.NONE)
    private final boolean validCookie;

    @Override
    public String getChannelId() {
        return baseRequest.getChannelId();
    }

    @Override
    public String getActivityName() {
        return baseRequest.getActivityName();
    }

    @Override
    public Collection<String> getAliasTypes() {
        return baseRequest.getAliasTypes();
    }

    @Override
    public boolean hasValidCookie() {
        return validCookie;
    }

}
