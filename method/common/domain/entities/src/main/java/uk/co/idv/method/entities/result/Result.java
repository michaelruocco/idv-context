package uk.co.idv.method.entities.result;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Builder
@Data
public class Result {

    private final String methodName;
    private final boolean successful;
    private final Instant timestamp;
    private final UUID verificationId;

    public boolean isFor(String otherMethodName) {
        return this.methodName.equals(otherMethodName);
    }

}
