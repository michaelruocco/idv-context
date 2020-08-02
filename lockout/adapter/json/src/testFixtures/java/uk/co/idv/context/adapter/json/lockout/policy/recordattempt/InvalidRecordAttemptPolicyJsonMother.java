package uk.co.idv.context.adapter.json.lockout.policy.recordattempt;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface InvalidRecordAttemptPolicyJsonMother {

    static String invalid() {
        return loadContentFromClasspath("policy/record-attempt/invalid-record-attempt-policy.json");
    }

}
