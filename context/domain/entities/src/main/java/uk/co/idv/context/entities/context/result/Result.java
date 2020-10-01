package uk.co.idv.context.entities.context.result;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Builder
@Data
public class Result {

    private final String methodName;
    private final boolean successful;
    private final UUID verificationId;
    private final Instant timestamp;

    public boolean isFor(String otherMethodName) {
        return this.methodName.equals(otherMethodName);
    }

}
