package uk.co.idv.lockout.entities;

import uk.co.idv.lockout.entities.DefaultRecordAttemptRequest.DefaultRecordAttemptRequestBuilder;
import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.idv.lockout.entities.attempt.AttemptMother;

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
