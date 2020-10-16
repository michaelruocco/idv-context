package uk.co.idv.lockout.adapter.json.policy.recordattempt;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.lockout.adapter.json.policy.recordattempt.always.AlwaysRecordAttemptPolicyDeserializer;
import uk.co.idv.lockout.adapter.json.policy.recordattempt.methodcomplete.RecordAttemptWhenMethodCompletePolicyDeserializer;
import uk.co.idv.lockout.adapter.json.policy.recordattempt.never.NeverRecordAttemptPolicyDeserializer;
import uk.co.idv.lockout.adapter.json.policy.recordattempt.sequencecomplete.RecordAttemptWhenSequenceCompletePolicyDeserializer;
import uk.co.idv.lockout.entities.policy.RecordAttemptPolicy;
import uk.co.idv.lockout.entities.policy.recordattempt.AlwaysRecordAttemptPolicy;
import uk.co.idv.lockout.entities.policy.recordattempt.NeverRecordAttemptPolicy;
import uk.co.idv.lockout.entities.policy.recordattempt.RecordAttemptWhenMethodCompletePolicy;
import uk.co.idv.lockout.entities.policy.recordattempt.RecordAttemptWhenSequenceCompletePolicy;

public class RecordAttemptPolicyModule extends SimpleModule {

    public RecordAttemptPolicyModule() {
        super("record-attempt-policy-module", Version.unknownVersion());
        setUpDefaults();

        setUpAlwaysRecordAttemptPolicy();
        setUpNeverRecordAttemptPolicy();
        setUpRecordAttemptWhenMethodCompletePolicy();
        setUpRecordAttemptWhenSequenceCompletePolicy();
    }

    private void setUpDefaults() {
        addDeserializer(RecordAttemptPolicy.class, new RecordAttemptPolicyDeserializer());
    }

    private void setUpAlwaysRecordAttemptPolicy() {
        addDeserializer(
                AlwaysRecordAttemptPolicy.class,
                new AlwaysRecordAttemptPolicyDeserializer()
        );
    }

    private void setUpNeverRecordAttemptPolicy() {
        addDeserializer(
                NeverRecordAttemptPolicy.class,
                new NeverRecordAttemptPolicyDeserializer()
        );
    }

    private void setUpRecordAttemptWhenMethodCompletePolicy() {
        addDeserializer(
                RecordAttemptWhenMethodCompletePolicy.class,
                new RecordAttemptWhenMethodCompletePolicyDeserializer()
        );
    }

    private void setUpRecordAttemptWhenSequenceCompletePolicy() {
        addDeserializer(
                RecordAttemptWhenSequenceCompletePolicy.class,
                new RecordAttemptWhenSequenceCompletePolicyDeserializer()
        );
    }

}
