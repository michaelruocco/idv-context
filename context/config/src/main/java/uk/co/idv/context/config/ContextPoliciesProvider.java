package uk.co.idv.context.config;

import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.policy.usecases.policy.DefaultPoliciesProvider;
import uk.co.mruoc.json.JsonConverter;

import java.util.Arrays;
import java.util.Collection;

public class ContextPoliciesProvider extends DefaultPoliciesProvider<ContextPolicy> {

    public ContextPoliciesProvider(JsonConverter converter) {
        super(converter, ContextPolicy.class, buildPaths());
    }

    private static Collection<String> buildPaths() {
        return Arrays.asList(
                "policies/abc-context-policy.json",
                "policies/gb-rsa-context-policy.json"
        );
    }

}
