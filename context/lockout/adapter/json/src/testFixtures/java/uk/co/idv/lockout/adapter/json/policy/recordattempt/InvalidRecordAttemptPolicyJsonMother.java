package uk.co.idv.lockout.adapter.json.policy.recordattempt;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface InvalidRecordAttemptPolicyJsonMother {

    static String invalid() {
        return loadContentFromClasspath("policy/record-attempt/invalid-record-attempt-policy.json");
    }

}
