package uk.co.idv.policy.entities.policy.key.validcookie;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.entities.policy.key.PolicyKey;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Builder
@Data
public class ValidCookiePolicyKey implements PolicyKey {

    public static final String TYPE = "valid-cookie";

    @Getter(AccessLevel.NONE)
    private final PolicyKey baseKey;

    private final boolean validCookie;

    @Override
    public UUID getId() {
        return baseKey.getId();
    }

    @Override
    public String getChannelId() {
        return baseKey.getChannelId();
    }

    @Override
    public int getPriority() {
        return baseKey.getPriority();
    }

    @Override
    public Collection<String> getActivityNames() {
        return baseKey.getActivityNames();
    }

    @Override
    public boolean appliesTo(PolicyRequest request) {
        if (!baseKey.appliesTo(request)) {
            return false;
        }
        return toValidCookiePolicyRequest(request)
                .map(validCookieRequest -> validCookieRequest.hasValidCookie() == validCookie)
                .orElse(false);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    private Optional<ValidCookiePolicyRequest> toValidCookiePolicyRequest(PolicyRequest request) {
        if (request instanceof ValidCookiePolicyRequest) {
            return Optional.of((ValidCookiePolicyRequest) request);
        }
        return Optional.empty();
    }

}
