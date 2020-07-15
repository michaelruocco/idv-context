package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import lombok.Getter;

@Getter
public class DynamoBatchGetException extends RuntimeException {

    private final BatchGetItemOutcome outcome;

    public DynamoBatchGetException(BatchGetItemOutcome outcome) {
        super(outcome.toString());
        this.outcome = outcome;
    }

}
