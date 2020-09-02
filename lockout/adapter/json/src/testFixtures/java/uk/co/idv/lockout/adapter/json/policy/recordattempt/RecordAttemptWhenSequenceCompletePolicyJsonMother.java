package uk.co.idv.lockout.adapter.json.policy.recordattempt;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface RecordAttemptWhenSequenceCompletePolicyJsonMother {

    static String json() {
        return loadContentFromClasspath("policy/record-attempt/record-attempt-when-sequence-complete-policy.json");
    }

}
