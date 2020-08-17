package uk.co.idv.context.entities.lockout;

import uk.co.idv.context.entities.lockout.DefaultRecordAttemptRequest.DefaultRecordAttemptRequestBuilder;
import uk.co.idv.context.entities.lockout.attempt.Attempt;
import uk.co.idv.context.entities.lockout.attempt.AttemptMother;

public interface DefaultRecordAttemptRequestMother {

    static DefaultRecordAttemptRequest withAttempt(Attempt attempt) {
        return builder().attempt(attempt).build();
    }

    static DefaultRecordAttemptRequest build() {
        return builder().build();
    }

    static DefaultRecordAttemptRequestBuilder builder() {
        return DefaultRecordAttemptRequest.builder()
                .sequenceComplete(true)
                .methodComplete(true)
                .attempt(AttemptMother.build());
    }

}
