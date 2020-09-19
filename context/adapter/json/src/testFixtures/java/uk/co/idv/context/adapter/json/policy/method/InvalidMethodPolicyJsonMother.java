
package uk.co.idv.context.adapter.json.policy.method;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface InvalidMethodPolicyJsonMother {

    static String invalid() {
        return loadContentFromClasspath("method/invalid-method-policy.json");
    }

}
