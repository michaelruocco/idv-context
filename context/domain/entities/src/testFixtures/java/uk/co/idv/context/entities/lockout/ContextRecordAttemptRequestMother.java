package uk.co.idv.context.entities.lockout;

import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.context.entities.context.lockout.ContextRecordAttemptRequest;
import uk.co.idv.method.entities.result.ResultMother;

public interface ContextRecordAttemptRequestMother {

    static ContextRecordAttemptRequest build() {
        return builder().build();
    }

    static ContextRecordAttemptRequest.ContextRecordAttemptRequestBuilder builder() {
        return ContextRecordAttemptRequest.builder()
                .context(ContextMother.build())
                .result(ResultMother.build())
                .sequenceComplete(true)
                .methodComplete(true);
    }

}
