package uk.co.idv.lockout.config;

import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.policy.usecases.policy.DefaultPoliciesProvider;
import uk.co.mruoc.json.JsonConverter;

import java.util.Arrays;
import java.util.Collection;

public class LockoutPoliciesProvider extends DefaultPoliciesProvider<LockoutPolicy> {

    public LockoutPoliciesProvider(JsonConverter converter) {
        super(converter, LockoutPolicy.class, buildPaths());
    }

    private static Collection<String> buildPaths() {
        return Arrays.asList(
                "policies/abc-lockout-policy.json",
                "policies/gb-rsa-lockout-policy.json"
        );
    }

}
