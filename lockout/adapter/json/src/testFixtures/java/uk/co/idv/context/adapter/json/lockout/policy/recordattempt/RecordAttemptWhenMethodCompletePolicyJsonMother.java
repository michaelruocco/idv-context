package uk.co.idv.context.adapter.json.lockout.policy.recordattempt;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface RecordAttemptWhenMethodCompletePolicyJsonMother {

    static String json() {
        return loadContentFromClasspath("policy/record-attempt/record-attempt-when-method-complete-policy.json");
    }

}
