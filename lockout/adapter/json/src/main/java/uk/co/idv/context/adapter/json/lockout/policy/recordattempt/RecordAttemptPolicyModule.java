package uk.co.idv.context.adapter.json.lockout.policy.recordattempt;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.lockout.policy.recordattempt.always.AlwaysRecordAttemptPolicyDeserializer;
import uk.co.idv.context.adapter.json.lockout.policy.recordattempt.methodcomplete.RecordAttemptWhenMethodCompletePolicyDeserializer;
import uk.co.idv.context.adapter.json.lockout.policy.recordattempt.never.NeverRecordAttemptPolicyDeserializer;
import uk.co.idv.context.adapter.json.lockout.policy.recordattempt.sequencecomplete.RecordAttemptWhenSequenceCompletePolicyDeserializer;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptPolicy;
import uk.co.idv.context.entities.lockout.policy.recordattempt.AlwaysRecordAttemptPolicy;
import uk.co.idv.context.entities.lockout.policy.recordattempt.NeverRecordAttemptPolicy;
import uk.co.idv.context.entities.lockout.policy.recordattempt.RecordAttemptWhenMethodCompletePolicy;
import uk.co.idv.context.entities.lockout.policy.recordattempt.RecordAttemptWhenSequenceCompletePolicy;

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
