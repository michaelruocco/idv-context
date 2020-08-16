package uk.co.idv.context.adapter.json.lockout.policy.recordattempt;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface RecordAttemptWhenSequenceCompletePolicyJsonMother {

    static String json() {
        return loadContentFromClasspath("policy/record-attempt/record-attempt-when-sequence-complete-policy.json");
    }

}
