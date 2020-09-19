
package uk.co.idv.context.adapter.json.policy.method;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface MethodPoliciesJsonMother {

    static String build() {
        return loadContentFromClasspath("method/method-policies.json");
    }

}
