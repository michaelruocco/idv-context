package uk.co.idv.lockout.adapter.json.policy.recordattempt;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface NeverRecordAttemptPolicyJsonMother {

    static String json() {
        return loadContentFromClasspath("policy/record-attempt/never-record-attempt-policy.json");
    }

}
