package uk.co.idv.context.config;

import java.util.Arrays;
import java.util.Collection;

public interface ContextPolicyPaths {

    static Collection<String> buildPaths() {
        return Arrays.asList(
                "policies/abc-context-policy.json",
                "policies/gb-rsa-context-policy.json"
        );
    }

}
