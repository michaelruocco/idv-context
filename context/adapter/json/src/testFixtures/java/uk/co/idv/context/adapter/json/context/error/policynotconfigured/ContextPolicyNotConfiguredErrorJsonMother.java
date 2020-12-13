package uk.co.idv.context.adapter.json.context.error.policynotconfigured;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ContextPolicyNotConfiguredErrorJsonMother {

    static String contextPolicyNotConfiguredErrorJson() {
        return loadContentFromClasspath("context/error/context-policy-not-configured-error.json");
    }

}
