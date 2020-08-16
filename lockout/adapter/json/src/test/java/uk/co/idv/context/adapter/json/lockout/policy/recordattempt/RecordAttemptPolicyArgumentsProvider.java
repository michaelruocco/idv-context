package uk.co.idv.context.adapter.json.lockout.policy.recordattempt;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.entities.lockout.policy.recordattempt.AlwaysRecordAttemptPolicy;
import uk.co.idv.context.entities.lockout.policy.recordattempt.NeverRecordAttemptPolicy;
import uk.co.idv.context.entities.lockout.policy.recordattempt.RecordAttemptWhenMethodCompletePolicy;
import uk.co.idv.context.entities.lockout.policy.recordattempt.RecordAttemptWhenSequenceCompletePolicy;

import java.util.stream.Stream;

public class RecordAttemptPolicyArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(NeverRecordAttemptPolicyJsonMother.json(), new NeverRecordAttemptPolicy()),
                Arguments.of(AlwaysRecordAttemptPolicyJsonMother.json(), new AlwaysRecordAttemptPolicy()),
                Arguments.of(RecordAttemptWhenMethodCompletePolicyJsonMother.json(), new RecordAttemptWhenMethodCompletePolicy()),
                Arguments.of(RecordAttemptWhenSequenceCompletePolicyJsonMother.json(), new RecordAttemptWhenSequenceCompletePolicy())
        );
    }

}
