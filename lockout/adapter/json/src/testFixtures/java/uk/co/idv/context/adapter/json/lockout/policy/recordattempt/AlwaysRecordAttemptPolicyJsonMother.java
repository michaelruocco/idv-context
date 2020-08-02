package uk.co.idv.context.adapter.json.lockout.policy.recordattempt;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface AlwaysRecordAttemptPolicyJsonMother {

    static String json() {
        return loadContentFromClasspath("policy/record-attempt/always-record-attempt-policy.json");
    }

}
