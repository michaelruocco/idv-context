package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import lombok.Getter;

@Getter
public class DynamoBatchWriteException extends RuntimeException {

    private final transient BatchWriteItemOutcome outcome;

    public DynamoBatchWriteException(BatchWriteItemOutcome outcome) {
        super(outcome.toString());
        this.outcome = outcome;
    }

}
