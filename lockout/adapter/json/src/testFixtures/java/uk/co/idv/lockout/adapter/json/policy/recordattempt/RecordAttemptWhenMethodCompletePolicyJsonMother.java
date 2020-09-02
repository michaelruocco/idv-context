package uk.co.idv.lockout.adapter.json.policy.recordattempt;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface RecordAttemptWhenMethodCompletePolicyJsonMother {

    static String json() {
        return loadContentFromClasspath("policy/record-attempt/record-attempt-when-method-complete-policy.json");
    }

}
